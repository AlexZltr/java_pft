package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase{

  public SessionHelper(WebDriver wd) {
    super(wd);
  }

  public void login(String username, String password) {
//    wd.findElement(By.xpath("//html")).click();
    type(By.name("user"), username);
    type(By.name("pass"), password);
//    wd.findElement(By.id("LoginForm")).click();
    click(By.xpath("//input[@value='Login']"));
  }

  public void logout () {
    click(By.linkText("Logout"));
  }

}
