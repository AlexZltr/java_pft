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
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

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
    Contacts dbContactsBefore = app.db().contacts();
    Groups dbGroupsBefore = app.db().groups();

    app.goTo().gotoHome();

    int idSelectedGroup = 0;
    int idSelectedContact = 0;
    ContactData  selectedContact;
    GroupData selectedGroup;
    Groups groupContactBefore = new Groups();

    ///создаем список контактов, у которых кол-во добавленных групп не равно общему кол-ву групп
    List<ContactData> listContactForAdded = new ArrayList<ContactData>() ;
    for (ContactData contact : dbContactsBefore) {
      if (contact.getGroups().size() != dbGroupsBefore.size()){
        listContactForAdded.add(contact);
      }
    }

    ///если список пустой, создаем новый контакт
    if (listContactForAdded.size() == 0) {
    app.goTo().gotoHome();
    app.goTo().addContactPage();
      long now = System.currentTimeMillis();
      String LastName = String.format("LastName%s", now);
    selectedContact = new ContactData().withLastName(LastName).withFirstName("FirstName");
    app.contact().create(selectedContact);
      Contacts dbContactWithAdded = app.db().contacts();
      idSelectedContact = dbContactWithAdded.stream().mapToInt((g) -> g.getId()).max().getAsInt();
    }
    ///если не пустой, берем любой контакт из этого списка
    else {
      selectedContact = listContactForAdded.iterator().next();
      idSelectedContact = selectedContact.getId();
    }
    System.out.println("idSelectedContact " + idSelectedContact);

    ///для выбранного контакта находим группы, в которые еще не добавлен контакт
    Groups contactGroupsBefore = selectedContact.getGroups();
    List<GroupData> listGroupForAdded = new ArrayList<GroupData>() ;
    for (GroupData group : dbGroupsBefore){
      if (!contactGroupsBefore.contains(group) ){
        listGroupForAdded.add(group);
      }
    }
    selectedGroup = listGroupForAdded.iterator().next();
    idSelectedGroup = selectedGroup.getId();
    System.out.println("idSelectedGroup " + idSelectedGroup);

    app.goTo().gotoHome();
    app.contact().selectContactById(idSelectedContact);
    app.contact().addToGroupById(idSelectedGroup);

    Groups groupsContactAfter = contactGroupsBefore.withAdded(selectedGroup);
    Contacts dbContactsAfter = app.db().contacts();
    for (ContactData c : dbContactsAfter) {
      if (c.getId() == idSelectedContact) {
        Assert.assertEquals(c.getGroups(),groupsContactAfter);
      }
    }
  }
}
