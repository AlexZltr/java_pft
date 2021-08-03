package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    for (ContactData c : dbContacts) {
         Groups groupsContact = c.getGroups();
      for (GroupData g : groupsContact) {
        if (selectedGroup.getId() != g.getId()) {
          List<ContactData> listContactAddedToGroup = new ArrayList<>();
          listContactAddedToGroup.add(c);
        }
      }
    }

//    for (GroupData g : groupsContactBefore) {
//      Map<GroupData, ContactData> mapContactForGroup = new HashMap<>();
//      if (idSelectedGroup == g.getId()) {
//        for (ContactData c : dbContacts) {
//          c.getGroups();
//        }
//      }
//    }
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
