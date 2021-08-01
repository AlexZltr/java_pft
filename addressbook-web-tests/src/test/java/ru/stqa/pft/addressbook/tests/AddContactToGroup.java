package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class AddContactToGroup extends TestBase {

  @BeforeMethod
  private void testPrecondition() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    if (groups.size() ==0 ){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("testGroup"));
    }
    if (contacts.size() == 0){
      app.goTo().gotoHome();
      app.goTo().addContactPage();
      app.contact().create(new ContactData().withLastName("LastName")
              .withFirstName("FirstName"));
    }
  }

  @Test
  public void testAddContactToGroup() {
    Contacts dbContacts = app.db().contacts();
    Groups dbGroups = app.db().groups();

    app.goTo().gotoHome();

    ContactData selectedContact = dbContacts.iterator().next();
    int idSelectedContact = selectedContact.getId();
    Groups groupsContactBefore = selectedContact.getGroups();
    System.out.println("Список групп, в которых уже состоит контакт" + selectedContact.getLastName() + "'(id = "+idSelectedContact+ ") :" + groupsContactBefore);
    GroupData selectedGroup = dbGroups.iterator().next();
    int idSelectedGroup = selectedGroup.getId();
    System.out.println("Контакт '" + selectedContact.getLastName() + "'(id = "+idSelectedContact+ ") добавляем в группу '" + selectedGroup.getName() + "' (id = "+idSelectedGroup+ ")");

    app.contact().selectContactById(idSelectedContact);
    app.contact().addToGroupByName(selectedGroup.getId());

    Groups groupsContactAfter = groupsContactBefore.withAdded(selectedGroup);
    Contacts dbContactsAfter = app.db().contacts();
    for (ContactData c : dbContactsAfter) {
      if (c.getId() == idSelectedContact) {
        Assert.assertEquals(c.getGroups(),groupsContactAfter);
        System.out.println("Контакт '" + selectedContact.getLastName() + "' (id = "+idSelectedContact+ ") успешно добавлен в группу '" + selectedGroup.getName() + "' (id = "+idSelectedGroup+ ")");
      }
    }
  }
}
