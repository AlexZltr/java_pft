package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;

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
  }

  @Test
  public void testRemoveContactFromGroup() {
    Contacts dbContacts = app.db().contacts();
    Groups dbGroups = app.db().groups();
    ContactData selectedContact;
    GroupData selectedGroup;
    Groups groupsContactForRemove;

    ///создаем список контактов, у которых кол-во добавленных групп больше 1
    List<ContactData> listContactForRemove = new ArrayList<ContactData>() ;
    for (ContactData contact : dbContacts) {
      if (contact.getGroups().size() >=1){
        listContactForRemove.add(contact);
      }
    }
    ///если список пустой, выбираем любой контакт и добавляем в группу
    if (listContactForRemove.size() == 0) {
      selectedContact = dbContacts.iterator().next();

      selectedGroup = dbGroups.iterator().next();
      app.goTo().gotoHome();
      app.contact().selectContactById(selectedContact.getId());
      app.contact().addToGroupById(selectedGroup.getId());
      groupsContactForRemove = selectedContact.getGroups().withAdded(selectedGroup);
      selectedGroup = groupsContactForRemove.iterator().next();
    } //если не пустой, выбираем любой контакт из списка контактов, у которых кол-во добавленных групп больше 1
    else{
      selectedContact = listContactForRemove.iterator().next();
      groupsContactForRemove = selectedContact.getGroups();
      selectedGroup = selectedContact.getGroups().iterator().next();
    }

      app.goTo().gotoHome();
      app.contact().selectContactById(selectedContact.getId());
      app.contact().removeFromGroup(selectedContact, selectedGroup);

    Groups groupsContactAfter = groupsContactForRemove.without(selectedGroup);
    Contacts dbContactsAfter = app.db().contacts();
    for (ContactData c : dbContactsAfter) {
      if (c.getId() == selectedContact.getId()) {
        Assert.assertEquals(c.getGroups(),groupsContactAfter);
        System.out.println("Контакт '" + selectedContact.getLastName() + "' (id = "+selectedContact.getId()+ ") успешно удален из группы '" + selectedGroup.getName() + "' (id = "+selectedGroup.getId()+ ")");
      }
    }
  }
}
