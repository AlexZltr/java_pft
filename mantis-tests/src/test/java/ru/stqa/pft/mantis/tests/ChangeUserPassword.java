package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.testng.AssertJUnit.assertTrue;

public class ChangeUserPassword extends TestBase{

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testChangeUserPassword() throws IOException, MessagingException, InterruptedException {

    app.registration().loginAs("administrator", "root");
    app.registration().goToManageUser();
    Users dbUser = app.db().users();
////    Выбрать первого user из списка пользователей без Админа:
//    Users dbUsersNotAdmin = dbUser.without(dbUser.stream().filter((u) -> u.getUsername().equals("administrator")).findFirst().get());
//    UserData selectedUser = dbUsersNotAdmin.iterator().next();
////    ИЛИ указать user:
    String userForChange = "user1627978394979";
    UserData selectedUser = dbUser.stream().filter((u) -> u.getUsername().equals(userForChange)).findFirst().get();
    app.registration().changeUserById(selectedUser.getId());
    app.registration().resetPassword();

    List<MailMessage> mailMessages = app.mail().waitForMail(2, 40000);
    String confirmationLink = findConfirmationLink(mailMessages, selectedUser.getEmail());
    System.out.println("confirmationLink " + confirmationLink);
    app.registration().confirmNewPassword(confirmationLink, "Vasiliy", "pass");
    assertTrue(app.newSession().login(selectedUser.getUsername(),"pass"));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
