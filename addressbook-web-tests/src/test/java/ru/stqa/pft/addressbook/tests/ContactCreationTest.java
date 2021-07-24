package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getNavigationHelper().gotoAddContactPage();
    ContactData contact = new ContactData("Петров", "Василий", "СПб, ул.Петрова, д.2", "222-33-44", "mail1@mail.ru", "test1");
    app.getContactHelper().createContact(contact);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

//    int max = 0;
//    for (ContactData c : after ) {
//      if (c.getId() > max) {
//        max = c.getId();
//      }
//    }
    contact.setId(after.stream().max((Comparator<ContactData>) (o1, o2) -> Integer.compare(o1.getId(),o2.getId())).get().getId());
    before.add(contact);
    Assert.assertEquals(new HashSet<Object>(before),new HashSet<Object>(after));
    app.getSessionHelper().logout();
  }
}
