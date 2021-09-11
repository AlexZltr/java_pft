package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Set;

public class TestBase {

  @BeforeClass
  public void init(){
    RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490","");
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      System.out.println("Ignored because of issue " + issueId);
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public Set<Issue> getIssues()  {
    String json = RestAssured.get("https://bugify.stqa.ru/api/issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  public String getStateIssueByID (int issueId)  {
    String json = RestAssured.get("https://bugify.stqa.ru/api/issues/"+issueId+".json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    Set<Issue> setIssue = new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    Issue issueById = setIssue.iterator().next();
    return issueById.getState_name();
  }

  public boolean isIssueOpen(int issueId) throws IOException {
    String status_issue = getStateIssueByID(issueId);
    System.out.println("state_issue " + status_issue);
    if (!status_issue.equals("Closed")) {
      return true; } else {return false;}
  }
}
