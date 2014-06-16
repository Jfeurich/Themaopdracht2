package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ReserveringBevestigenTest {
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
  public void testReserveringBevestigen() throws Exception {
    driver.findElement(By.linkText("Een parkeer reservering bevestigen(4)")).click();
    driver.findElement(By.name("zoekviaID")).clear();
    driver.findElement(By.name("zoekviaID")).sendKeys("2");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("De reservering is succesvol bevestigd", driver.findElement(By.cssSelector("h3[name=\"msg\"] > span")).getText());
    driver.findElement(By.name("zoekviaID")).clear();
    driver.findElement(By.name("zoekviaID")).sendKeys("100");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
    driver.findElement(By.name("zoekviaID")).clear();
    driver.findElement(By.name("zoekviaID")).sendKeys("a$#@");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
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
