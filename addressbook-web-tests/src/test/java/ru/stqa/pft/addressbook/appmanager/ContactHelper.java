package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void fillNewContact(ContactData contactData, boolean creation) {

    type(By.name("lastname"), contactData.getLastName());
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    type(By.name("nickname"), contactData.getNickname());
//    attach(By.name("photo"),contactData.getPhoto());

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        //  Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
      } else {
        Assert.assertFalse(isElementPresent(By.name("new_group")));
      }
    }


  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModificationById(contact.getId());
    fillNewContact(contact, false);
    submitContactModification();
    contactCache = null;
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContact();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContact();
    contactCache = null;
  }

  public void submitAddContact() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initContactModification(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void initContactModificationById(int id) {
//    wd.findElement(By.xpath("//input[@id='" + id + "']/../following-sibling::td[7]")).click();
    wd.findElement(By.xpath(String.format(".//input[@id='" + id + "']/../following-sibling::td[7]/a/img", id))).click();
  }

  public void submitContactModification() {
    click(By.xpath("//input[@name='update']"));
  }

  public void deleteSelectedContact() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void create(ContactData contact) {
    fillNewContact(contact,true);
    submitAddContact();
    contactCache = null;
    returnToHomePage();
  }


  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name=entry]"));
    for (WebElement element : elements){
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      ContactData contact = new ContactData().withId(id).withLastName(lastName).withFirstName(firstName);
      contacts.add(contact);
    }
    return contacts;
  }
  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    Contacts contacts = new Contacts();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name=entry]"));
    for (WebElement element : elements){
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      String address = cells.get(3).getText();
//      String[] phones = cells.get(5).getText().split("\n");
      String allPhones = cells.get(5).getText();
//      String[] emails = cells.get(4).getText().split("\n");
      String allEmails = cells.get(4).getText();

      ContactData contact = new ContactData().withId(id).withLastName(lastName).withFirstName(firstName)
//              .withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2])
              .withAllPhones(allPhones)
              .withAddress(address)
//              .withEmail(emails[0]).withEmail2(emails[1]).withEmail3(emails[2]);
              .withAllEmails(allEmails);
      contactCache.add(contact);
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstname)
            .withLastName(lastname).withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
            .withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3);

  }

  public void addToGroupByName(int id) {
    click(By.xpath("//select[@name='to_group']"));
    click(By.xpath("//select[@name='to_group']/option[@value='" + id + "']"));
    click(By.xpath("//input[@value='Add to']"));
  }

  public void removeFromGroup(ContactData contact, GroupData group) {
    click(By.xpath("//select[@name='group']"));
    click(By.xpath("//select[@name='group']//option[text()='"+ group.getName() + "']"));
    selectContactById(contact.getId());
    click(By.xpath("//input[@name='remove']"));
  }
}