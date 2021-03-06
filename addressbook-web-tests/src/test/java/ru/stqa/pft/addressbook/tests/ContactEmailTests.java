package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase{

  @BeforeMethod
  private void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().gotoHome();
      app.goTo().addContactPage();
      app.contact().create(new ContactData().withLastName("Петров").withFirstName("Василий").withNickname("Вася").withAddress("СПб, ул.Петрова, д.2").withHomePhone("222-33-44").withEmail("mail"));
    }
  }

  @Test
  public void ContactEmailTests() {
    ContactData contact = app.db().contacts().iterator().next();
    app.goTo().gotoHome();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getEmail(), equalTo(contactInfoFromEditForm.getEmail()));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(),contact.getEmail2(),contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));
  }
}
