package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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
    driver.get(baseUrl + "/Themaopdracht/index.html");
    driver.findElement(By.linkText("Nieuwe reservering(3)")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("iets");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("iets");
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("knop")).click();
    assertEquals("Error!", driver.findElement(By.cssSelector("h3")).getText());
    driver.findElement(By.name("knop")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*css=form > div[\\s\\S]*$"));
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("05-03-2005");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("03-03-2005");
    driver.findElement(By.name("knop")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*css=form > div[\\s\\S]*$"));
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("01-03-2006");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("01-04-2006");
    driver.findElement(By.name("knop")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*css=h3[\\s\\S]*$"));
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("01-02-2006");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("05-03-2006");
    driver.findElement(By.name("knop")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*css=form > div[\\s\\S]*$"));
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
