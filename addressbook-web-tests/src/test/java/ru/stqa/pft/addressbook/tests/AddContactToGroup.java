package ru.stqa.pft.addressbook.tests;

import org.checkerframework.checker.units.qual.C;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
    Groups groupsContactBefore = selectedContact.getGroups();
    System.out.println("groupsContactBefore" + groupsContactBefore);
    System.out.println("selectedContact.getId()" + selectedContact.getId());
    GroupData selectedGroup = dbGroups.iterator().next();

    app.contact().selectContactById(selectedContact.getId());
    app.contact().addToGroupByName(selectedGroup.getId());

    Contacts dbContactsAfter = app.db().contacts();
    Groups dbGroupsAfter = app.db().groups();


//    System.out.println( "groupsContactsAfter" + dbContactsAfter.stream().map((g) -> new ContactData().getGroups()));
    System.out.println( "groupsContactsAfter" + dbContactsAfter.stream()
            .map((g) -> new ContactData().withId(g.getId()).getGroups())
            .collect(Collectors.toSet()));
    Set<ContactData> setContactAfter = dbContactsAfter.stream()
            .map((g) -> new ContactData().withId(g.getId()))
            .collect(Collectors.toSet());


//    assertThat(groupsContactBefore, equalTo(dbContactsAfter.stream()
//            .map((g) -> new ContactData().withId(g.getId()).getGroups())
//            .collect(Collectors.toSet())));
//    System.out.println(dbGroupsAfter.stream().map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
//            .collect(Collectors.toSet())) ;
    //assertThat(dbContactsAfter.size(), equalTo(dbGroupsAfter.size()+1));



  }




}
