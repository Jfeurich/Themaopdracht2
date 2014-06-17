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

public class AccountWijzigenTest {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAccountWijzigen() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("sandri");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("sww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.linkText("Mijn Account")).click();
    assertEquals("Gebruikersnaam: sandri", driver.findElement(By.cssSelector("th")).getText());
    assertEquals("Woonplaats: Utrecht", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[6]/th")).getText());
    driver.findElement(By.name("wp")).clear();
    driver.findElement(By.name("wp")).sendKeys("Woerden");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    driver.findElement(By.name("bevestigwachtwoord")).clear();
    driver.findElement(By.name("bevestigwachtwoord")).sendKeys("sww");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("Woonplaats: Woerden", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[6]/th")).getText());
    driver.findElement(By.name("wp")).clear();
    driver.findElement(By.name("wp")).sendKeys("Utrecht");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    driver.findElement(By.name("bevestigwachtwoord")).clear();
    driver.findElement(By.name("bevestigwachtwoord")).sendKeys("s");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
    driver.findElement(By.name("bevestigwachtwoord")).clear();
    driver.findElement(By.name("bevestigwachtwoord")).sendKeys("sww");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("Woonplaats: Utrecht", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[6]/th")).getText());
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
