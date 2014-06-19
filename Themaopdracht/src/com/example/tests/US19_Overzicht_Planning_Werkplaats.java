package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US19_Overzicht_Planning_Werkplaats {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl + "/Themaopdracht/LogoutServlet.do");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  }

  @Test
  public void testUS19OverzichtPlanningWerkplaats() throws Exception {
    driver.get("http:/localhost:8080/Themaopdracht/overzichtwerkplaatsplanning.jsp");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("table")));
    assertEquals("Geplande klussen", driver.findElement(By.cssSelector("h2 > span")).getText());
    driver.get("http:/localhost:8080/Themaopdracht/overzichtwerkplaatsplanning.jsp");
    driver.findElement(By.id("bdat")).click();
    driver.findElement(By.linkText("01")).click();
    driver.findElement(By.id("edat")).click();
    driver.findElement(By.linkText("30")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    assertEquals("Tussen data", driver.findElement(By.cssSelector("h3 > span")).getText());
    driver.get("http:/localhost:8080/Themaopdracht/overzichtwerkplaatsplanning.jsp");
    driver.findElement(By.id("bdat")).click();
    driver.findElement(By.linkText("01")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
}
