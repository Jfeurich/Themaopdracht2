package com.example.tests;

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

public class ReserveringAnnulerenTest {
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
  public void testReserveringAnnuleren() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.jsp");
    driver.findElement(By.linkText("Een parkeer reservering annuleren(25)")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
    driver.findElement(By.name("zoekviaID")).clear();
    driver.findElement(By.name("zoekviaID")).sendKeys("as!@#$%");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
    driver.findElement(By.name("zoekviaID")).clear();
    driver.findElement(By.name("zoekviaID")).sendKeys("6767");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
    driver.findElement(By.name("zoekviaID")).clear();
    driver.findElement(By.name("zoekviaID")).sendKeys("1");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"msg\"] > span")));
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
