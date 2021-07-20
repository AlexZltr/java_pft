package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoAddContactPage();
    app.getContactHelper().createContact(new ContactData("Василий", "Петров", "СПб, ул.Петрова, д.2", "222-33-44", "mail1@mail.ru", "test1"));
    app.getSessionHelper().logout();
  }
}
