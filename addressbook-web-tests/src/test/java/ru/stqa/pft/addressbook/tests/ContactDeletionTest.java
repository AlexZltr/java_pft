package ru.stqa.pft.addressbook.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTest extends TestBase{

  @BeforeMethod
  private void ensurePreconditions() {
    app.goTo().gotoHome();
    if (!app.contact().isThereAContact()) {
      app.goTo().addContactPage();
      app.contact().create(new ContactData().withLastName("Петров").withFirstName("Василий").withAddress("СПб, ул.Петрова, д.2").withHomeTelephone("222-33-44").withEmail("mail1@mail.ru").withGroup("test1"));
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    int index = before.size() - 1 ;
    app.contact().delete(deletedContact);
    app.goTo().gotoHome();
    Contacts after = app.contact().all();
    assertEquals(after.size(), before.size() - 1);
    assertThat(after, Matchers.equalTo(before.without(deletedContact)));
  }

}
