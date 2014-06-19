package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class US17_UserAanmakenTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://127.0.0.1:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUserAanmaken() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("Henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.msg > span")));
    driver.get("http://127.0.0.1:8080/Themaopdracht/nieuwegebruikersaccount.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("test");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("test");
    driver.findElement(By.name("password2")).clear();
    driver.findElement(By.name("password2")).sendKeys("test");
    driver.findElement(By.name("type")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("test@test.test");
    driver.findElement(By.name("email2")).clear();
    driver.findElement(By.name("email2")).sendKeys("test@test.test");
    driver.findElement(By.name("naam")).clear();
    driver.findElement(By.name("naam")).sendKeys("test");
    driver.findElement(By.name("adres")).clear();
    driver.findElement(By.name("adres")).sendKeys("test");
    driver.findElement(By.name("plaats")).clear();
    driver.findElement(By.name("plaats")).sendKeys("test");
    driver.findElement(By.name("rekeningnummer")).clear();
    driver.findElement(By.name("rekeningnummer")).sendKeys("test");
    driver.findElement(By.name("telefoonnummer")).clear();
    driver.findElement(By.name("telefoonnummer")).sendKeys("123");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    try {
    	assertEquals("Gebruiker test Van type 0 succesvol geregistreerd!", driver.findElement(By.cssSelector("h3.msg > span")).getText());
      
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.linkText("Administratie")).click();
    driver.findElement(By.linkText("Registreer nieuwe gebruikersaccount")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("test");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("test");
    driver.findElement(By.name("password2")).clear();
    driver.findElement(By.name("password2")).sendKeys("test");
    driver.findElement(By.name("type")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("test@test.test");
    driver.findElement(By.name("email2")).clear();
    driver.findElement(By.name("email2")).sendKeys("test@test.test");
    driver.findElement(By.name("naam")).clear();
    driver.findElement(By.name("naam")).sendKeys("test");
    driver.findElement(By.name("adres")).clear();
    driver.findElement(By.name("adres")).sendKeys("test");
    driver.findElement(By.name("plaats")).clear();
    driver.findElement(By.name("plaats")).sendKeys("test");
    driver.findElement(By.name("rekeningnummer")).clear();
    driver.findElement(By.name("rekeningnummer")).sendKeys("test");
    driver.findElement(By.name("telefoonnummer")).clear();
    driver.findElement(By.name("telefoonnummer")).sendKeys("123");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    try {
    	assertEquals("Error! \n Er bestaat al een gebruiker met deze gebruikersnaam en/of dit emailadres!", driver.findElement(By.cssSelector("h3.error")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
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
