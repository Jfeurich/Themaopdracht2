package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class NieuweReserveringTest {
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
  public void testNieuweReservering() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.jsp");
    driver.findElement(By.linkText("Nieuwe reservering(3)")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.xpath("(//input[@name='gekozenklant'])[2]")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("knop")).click();
    assertEquals("Vul een begin- en einddatum in!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("iets");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("iets");
    driver.findElement(By.name("knop")).click();
    assertEquals("Kon de reservering niet toevoegen!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("-1");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("-1");
    driver.findElement(By.name("knop")).click();
    assertEquals("Kon de reservering niet toevoegen!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("01-01-2005");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("01-01-2004");
    driver.findElement(By.name("knop")).click();
    assertEquals("De einddatum komt NA de begindatum!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("01-01-2002");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("01-01-2003");
    driver.findElement(By.name("knop")).click();
    assertEquals("Reservering met succes aangemaakt voor parkeerplaats: 1", driver.findElement(By.cssSelector("h3[name=\"msg\"] > span")).getText());
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.xpath("(//input[@name='gekozenklant'])[2]")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("01-02-2002");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("01-04-2002");
    driver.findElement(By.name("knop")).click();
    assertEquals("Er bestaat al een reservering voor deze auto in deze periode!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
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
