package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class NieuweBriefTest {
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
  public void testNieuweBrief() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.html");
    driver.findElement(By.linkText("Nieuwe herinneringsbrief(23)")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.xpath("(//input[@name='gekozenklant'])[3]")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("knop")).click();
    assertEquals("Geef een reden aan!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("reden")).clear();
    driver.findElement(By.name("reden")).sendKeys("Voor de selenium test =)");
    driver.findElement(By.name("knop")).click();
    assertEquals("Brief met succes aangemaakt!", driver.findElement(By.cssSelector("h3[name=\"msg\"]")).getText());
    driver.findElement(By.name("knop")).click();
    assertEquals("datum van vandaag", driver.findElement(By.xpath("//tr[4]/td[5]")).getText());
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
