package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTest extends TestBase{

  @BeforeMethod
  private void ensurePreconditions() {
    app.goTo().gotoHome();
    if (app.contact().list().size() ==0) {
      app.goTo().addContactPage();
      app.contact().create(new ContactData().withLastName("Петров").withFirstName("Василий").withAddress("СПб, ул.Петрова, д.2").withHomeTelephone("222-33-44").withEmail("mail1@mail.ru").withGroup("test1"));
    }
  }

  @Test (enabled = true)
  public void testContactModification() {
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withLastName("Петров2").withFirstName("Василий").withAddress("СПб, ул.Петрова, д.2").withHomeTelephone("222-33-44").withEmail("mail1@mail.ru");
    app.contact().modify(contact);
    app.goTo().gotoHome();
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before,after);
  }


}
