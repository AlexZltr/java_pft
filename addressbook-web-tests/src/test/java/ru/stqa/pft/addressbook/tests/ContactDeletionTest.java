package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

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
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1 ;
    app.contact().delete(index);
    app.goTo().gotoHome();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    Assert.assertEquals(before, after);
  }



}
