package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class BTWOverzichtTest {
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
  public void testBTWOverzicht() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.html");
    driver.findElement(By.linkText("Overzicht BTW(22)")).click();
    driver.findElement(By.name("knop")).click();
    assertEquals("Vul een geldig begin- en eindjaar in!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("iets");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("iets");
    driver.findElement(By.name("knop")).click();
    assertEquals("Vul een geldig begin- en eindjaar in!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("-300");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("-400");
    driver.findElement(By.name("knop")).click();
    assertEquals("Vul een geldig begin- en eindjaar in!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("1900");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("1900");
    driver.findElement(By.name("knop")).click();
    assertEquals("Dit programma gaat niet verder terug dan 1950!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("2000");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("2014");
    driver.findElement(By.xpath("(//input[@name='eindkwartaal'])[3]")).click();
    driver.findElement(By.name("knop")).click();
    assertEquals("Geen facturen beschikbaar tussen deze data!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("2014");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("2015");
    driver.findElement(By.xpath("(//input[@name='eindkwartaal'])[4]")).click();
    driver.findElement(By.name("knop")).click();
    assertEquals("Alle betaalde facturen", driver.findElement(By.xpath("//div[2]/h2")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("2015");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("2014");
    driver.findElement(By.name("knop")).click();
    assertEquals("Eindjaar komt NA het beginjaar!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
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
