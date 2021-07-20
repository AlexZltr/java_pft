package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTest extends TestBase{

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().gotoHome();
    if (!app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().gotoAddContactPage();
      app.getContactHelper().createContact(new ContactData("Василий", "Петров", "СПб, ул.Петрова, д.2", "222-33-44", "mail1@mail.ru", "test1"));
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContact();
  }
}
