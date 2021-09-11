package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTests extends TestBase{



  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {

    skipIfNotFixed(1);

    Set<Project> projects = app.soap().getProjects();
    System.out.println(projects.size());
    for (Project project : projects){
      System.out.println(project.getName());
    }
    System.out.println("Проверка isIssueOpen idIssue=1 " + isIssueOpen(1));
  }

  @Test
  public void testCreateIssue()throws MalformedURLException, ServiceException, RemoteException {

    Set<Project> projects = app.soap().getProjects();
    for (Project project : projects){
      System.out.println(project.getName());
    }
    Issue issue = new Issue().withSummary("Test issue").withDescription("Test")
            .withProject(projects.iterator().next());

    Issue created = app.soap().addIssue(issue);
    Assert.assertEquals(issue.getSummary(),created.getSummary());
  }
}
