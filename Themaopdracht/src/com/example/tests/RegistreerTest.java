package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class RegistreerTest {
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
  public void testRegistreer() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.jsp");
    driver.findElement(By.linkText("Registreer nieuwe klant(7)")).click();
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
    driver.findElement(By.name("gebrnaam")).clear();
    driver.findElement(By.name("gebrnaam")).sendKeys("Testje");
    driver.findElement(By.name("nm")).clear();
    driver.findElement(By.name("nm")).sendKeys("Test");
    driver.findElement(By.name("ww1")).clear();
    driver.findElement(By.name("ww1")).sendKeys("testfout");
    driver.findElement(By.name("ww2")).clear();
    driver.findElement(By.name("ww2")).sendKeys("test");
    driver.findElement(By.name("mail1")).clear();
    driver.findElement(By.name("mail1")).sendKeys("test@hotmail.com");
    driver.findElement(By.name("mail2")).clear();
    driver.findElement(By.name("mail2")).sendKeys("testfout@hotmail.com");
    driver.findElement(By.name("adr")).clear();
    driver.findElement(By.name("adr")).sendKeys("teststraat");
    driver.findElement(By.name("wp")).clear();
    driver.findElement(By.name("wp")).sendKeys("teststadt");
    driver.findElement(By.name("telnr")).clear();
    driver.findElement(By.name("telnr")).sendKeys("061234567");
    driver.findElement(By.name("rnr")).clear();
    driver.findElement(By.name("rnr")).sendKeys("123456789");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
    driver.findElement(By.name("gebrnaam")).clear();
    driver.findElement(By.name("gebrnaam")).sendKeys("Testje");
    driver.findElement(By.name("nm")).clear();
    driver.findElement(By.name("nm")).sendKeys("Test");
    driver.findElement(By.name("ww1")).clear();
    driver.findElement(By.name("ww1")).sendKeys("test");
    driver.findElement(By.name("ww2")).clear();
    driver.findElement(By.name("ww2")).sendKeys("test");
    driver.findElement(By.name("mail1")).clear();
    driver.findElement(By.name("mail1")).sendKeys("test@hotmail.com");
    driver.findElement(By.name("mail2")).clear();
    driver.findElement(By.name("mail2")).sendKeys("test@hotmail.com");
    driver.findElement(By.name("adr")).clear();
    driver.findElement(By.name("adr")).sendKeys("teststraat");
    driver.findElement(By.name("wp")).clear();
    driver.findElement(By.name("wp")).sendKeys("teststadt");
    driver.findElement(By.name("telnr")).clear();
    driver.findElement(By.name("telnr")).sendKeys("nullzesetc");
    driver.findElement(By.name("rnr")).clear();
    driver.findElement(By.name("rnr")).sendKeys("12345");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3[name=\"error\"]")));
    driver.findElement(By.name("gebrnaam")).clear();
    driver.findElement(By.name("gebrnaam")).sendKeys("Testje");
    driver.findElement(By.name("nm")).clear();
    driver.findElement(By.name("nm")).sendKeys("Test");
    driver.findElement(By.name("ww1")).clear();
    driver.findElement(By.name("ww1")).sendKeys("test");
    driver.findElement(By.name("ww2")).clear();
    driver.findElement(By.name("ww2")).sendKeys("test");
    driver.findElement(By.name("mail1")).clear();
    driver.findElement(By.name("mail1")).sendKeys("test@hotmail.com");
    driver.findElement(By.name("mail2")).clear();
    driver.findElement(By.name("mail2")).sendKeys("test@hotmail.com");
    driver.findElement(By.name("adr")).clear();
    driver.findElement(By.name("adr")).sendKeys("teststraat");
    driver.findElement(By.name("wp")).clear();
    driver.findElement(By.name("wp")).sendKeys("teststad");
    driver.findElement(By.name("telnr")).clear();
    driver.findElement(By.name("telnr")).sendKeys("061234567");
    driver.findElement(By.name("rnr")).clear();
    driver.findElement(By.name("rnr")).sendKeys("123456789");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("Succesvol geregistreerd!", driver.findElement(By.cssSelector("h3[name=\"msg\"] > span")).getText());
    driver.findElement(By.name("gebrnaam")).clear();
    driver.findElement(By.name("gebrnaam")).sendKeys("Testje");
    driver.findElement(By.name("nm")).clear();
    driver.findElement(By.name("nm")).sendKeys("Jiry");
    driver.findElement(By.name("ww1")).clear();
    driver.findElement(By.name("ww1")).sendKeys("hoi");
    driver.findElement(By.name("ww2")).clear();
    driver.findElement(By.name("ww2")).sendKeys("hoi");
    driver.findElement(By.name("mail1")).clear();
    driver.findElement(By.name("mail1")).sendKeys("jiry1994@live.nl");
    driver.findElement(By.name("mail2")).clear();
    driver.findElement(By.name("mail2")).sendKeys("jiry1994@live.nl");
    driver.findElement(By.name("adr")).clear();
    driver.findElement(By.name("adr")).sendKeys("lala");
    driver.findElement(By.name("wp")).clear();
    driver.findElement(By.name("wp")).sendKeys("hoihoi");
    driver.findElement(By.name("telnr")).clear();
    driver.findElement(By.name("telnr")).sendKeys("0612345432");
    driver.findElement(By.name("rnr")).clear();
    driver.findElement(By.name("rnr")).sendKeys("1234565432");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
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