package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import sun.net.ftp.FtpProtocolException;

import java.io.File;
import java.io.IOException;

public class TestBase {

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
  }

  @AfterSuite (alwaysRun = true)
  public void tearDown() throws FtpProtocolException, IOException {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }
}
