package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTest extends TestBase{

  @BeforeMethod
  private void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().gotoHome();
      app.goTo().addContactPage();
      app.contact().create(new ContactData().withLastName("Петров").withFirstName("Василий").withNickname("Вася").withAddress("СПб, ул.Петрова, д.2").withHomePhone("222-33-44").withMobilePhone("111").withWorkPhone("222").withEmail("mail1@mail.ru").withGroup("test1"));
    }
  }

  @Test (enabled = true)
  public void testContactModification() {
    app.goTo().gotoHome();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withLastName("Петров2").withFirstName("Василий").withNickname("Вася").withAddress("СПб, ул.Петрова, д.2").withHomePhone("222-33-44").withMobilePhone("1221").withWorkPhone("2112").withEmail("mail1@mail.ru");
    app.contact().modify(contact);
    app.goTo().gotoHome();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactsListInUI();//vm options: -DverifyUI=true
  }
}
