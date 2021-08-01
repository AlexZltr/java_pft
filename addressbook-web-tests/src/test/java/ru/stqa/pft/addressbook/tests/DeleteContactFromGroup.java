package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class DeleteContactFromGroup extends TestBase{

  @BeforeMethod
  private void testPrecondition() {
    Contacts contactsBefore = app.db().contacts();
    Groups groupsBefore = app.db().groups();

    if (groupsBefore.size() ==0 ){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("testGroup"));
    }
    if (contactsBefore.size() == 0){
      app.goTo().gotoHome();
      app.goTo().addContactPage();
      app.contact().create(new ContactData().withLastName("LastName")
              .withFirstName("FirstName"));
    }
    //проверяем наличие групп у выбираемого контакта
    Contacts dbExistContacts = app.db().contacts();
    ContactData selectedContact = dbExistContacts.iterator().next();
    Groups groupsContactBefore = selectedContact.getGroups();
    Groups dbExistGroups = app.db().groups();
    GroupData selectedGroup = dbExistGroups.iterator().next();
    if(groupsContactBefore.size() == 0) {
      app.goTo().gotoHome();
      app.contact().selectContactById(selectedContact.getId());
      app.contact().addToGroupByName(selectedGroup.getId());
    }
  }

  @Test
  public void testRemoveContactFromGroup() {
    Contacts dbContacts = app.db().contacts();
    Groups dbGroups = app.db().groups();

    app.goTo().gotoHome();
    ContactData selectedContact = dbContacts.iterator().next();
    int idSelectedContact = selectedContact.getId();
    Groups groupsContactBefore = selectedContact.getGroups();
    System.out.println("Список групп, в которых уже состоит контакт" + selectedContact.getLastName() + "'(id = "+idSelectedContact+ ") :" + groupsContactBefore);
    GroupData selectedGroup = dbGroups.iterator().next();
    int idSelectedGroup = selectedGroup.getId();
    System.out.println("Контакт '" + selectedContact.getLastName() + "'(id = "+idSelectedContact+ ") будет удален из группы '" + selectedGroup.getName() + "' (id = "+idSelectedGroup+ ")");

    app.contact().removeFromGroup(selectedContact, selectedGroup);

    Groups groupsContactAfter = groupsContactBefore.without(selectedGroup);
    Contacts dbContactsAfter = app.db().contacts();
    for (ContactData c : dbContactsAfter) {
      if (c.getId() == idSelectedContact) {
        Assert.assertEquals(c.getGroups(),groupsContactAfter);
        System.out.println("Контакт '" + selectedContact.getLastName() + "' (id = "+idSelectedContact+ ") успешно удален из группы '" + selectedGroup.getName() + "' (id = "+idSelectedGroup+ ")");
      }
    }
  }
}
