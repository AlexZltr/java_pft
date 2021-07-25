package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

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
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData contact = new ContactData().withId(before.get(index).getId()).withLastName("Петров2").withFirstName("Василий").withAddress("СПб, ул.Петрова, д.2").withHomeTelephone("222-33-44").withEmail("mail1@mail.ru");
    app.contact().modify(index, contact);
    app.goTo().gotoHome();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);

    Comparator<? super ContactData> byId = (Comparator<ContactData>) (o1, o2) -> Integer.compare(o1.getId(),o2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);
  }


}
