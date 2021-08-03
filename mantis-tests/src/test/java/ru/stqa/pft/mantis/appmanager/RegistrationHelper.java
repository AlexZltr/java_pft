package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationHelper extends HelperBase{

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[type='submit']"));
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[type='submit']"));
  }

  public void loginAs(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    type(By.name("password"), password);
    click(By.cssSelector("input[type='submit']"));
  }

  public void goToManageUser() {
//    click(By.cssSelector("a[href='mantisbt-1.3.20/manage_overview_page.php']"));
    click(By.xpath("//a[contains(text(),'управление')]"));
    click(By.xpath("//a[contains(text(),'Управление пользователями')]"));
  }

  public void changeUserById(int id){
    click(By.cssSelector("a[href='manage_user_edit_page.php?user_id=" + id + "']"));
  }

  public void resetPassword(){
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }

  public void confirmNewPassword(String confirmationLink, String realname, String password) {
//  public void confirmNewPassword(String confirmationLink,  String password) {
    wd.get(confirmationLink);
    type(By.name("realname"), realname);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.cssSelector("input[type='submit']"));
  }
}
