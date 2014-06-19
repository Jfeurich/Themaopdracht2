package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ParkeerplaatsReserverenTP1 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testParkeerplaatsReserverenTP1() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("sandri");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("sww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.linkText("Parkeerplaats reserveren")).click();
    driver.findElement(By.name("begindat")).click();
    driver.findElement(By.name("begindat")).clear();
    driver.findElement(By.name("begindat")).sendKeys("01-07-2014");
    driver.findElement(By.name("einddat")).click();
    driver.findElement(By.name("einddat")).clear();
    driver.findElement(By.name("einddat")).sendKeys("14-07-2014");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    driver.findElement(By.cssSelector("#parkeerplaatsoverzicht > table > tbody > tr > td > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Auto met kenteken ABC is een Fiesta van Ford", driver.findElement(By.cssSelector("#content > form > p")).getText());
    assertEquals("Parkeerplek: 1", driver.findElement(By.xpath("//div[@id='content']/form/p[2]")).getText());
    assertEquals("Van: 01-07-2014", driver.findElement(By.xpath("//div[@id='content']/form/p[4]")).getText());
    assertEquals("Tot: 14-07-2014", driver.findElement(By.xpath("//div[@id='content']/form/p[5]")).getText());
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("Reservering met succes aangemaakt voor parkeerplaats: 1", driver.findElement(By.cssSelector("h3.msg > span")).getText());
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

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
