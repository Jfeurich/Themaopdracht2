package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US02_Klus_inplannen_door_klant {
  private static WebDriver driver;
  private static String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass
  public static void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("sandri");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("sww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  }

  @Test
  public void testUS2KlusInplannenDoorKlant() throws Exception {
	driver.get(baseUrl + "/Themaopdracht/index.jsp");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    driver.findElement(By.xpath("(//input[@name='type'])[2]")).click();
    driver.findElement(By.id("dat")).click();
    driver.findElement(By.linkText("30")).click();
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Test voor het aanmaken van een nieuwe klus door klant");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Nieuwe onderhoudsbeurt toevoegd voor: ABC", driver.findElement(By.cssSelector("h3.msg > span")).getText());
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    driver.findElement(By.id("dat")).click();
    driver.findElement(By.linkText("20")).click();
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Test2 voor inplannen van klus door klant");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Nieuwe reparatie toevoegd voor: ABC", driver.findElement(By.cssSelector("h3.msg > span")).getText());
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
    driver.findElement(By.id("dat")).click();
    driver.findElement(By.linkText("27")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Test3 voor klus aanmaken door klant");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
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
