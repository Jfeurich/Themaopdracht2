package com.example.tests;

import static org.junit.Assert.assertEquals;
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

public class US28_Nieuwe_Klus_Aanmaken {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  }

  @Test
  public void testUS28NieuweKlusAanmaken() throws Exception {
	driver.get("http:/localhost:8080/Themaopdracht/klus.jsp");
    driver.findElement(By.cssSelector("div > p > input[name=\"knop\"]")).click();
    driver.findElement(By.xpath("(//input[@name='gekozenklant'])[3]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("iets");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
    driver.findElement(By.id("dat")).click();
    driver.findElement(By.linkText("2")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
    driver.findElement(By.id("dat")).click();
    driver.findElement(By.linkText("2")).click();
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Check op aanmaken reparatie");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Nieuwe reparatie toevoegd voor: GHI", driver.findElement(By.cssSelector("h3.msg > span")).getText());
    driver.findElement(By.xpath("(//input[@name='gekozenklant'])[3]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.xpath("(//input[@name='type'])[2]")).click();
    driver.findElement(By.id("dat")).click();
    driver.findElement(By.linkText("2")).click();
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Check toevoegen onderhoudsbeurt");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Nieuwe onderhoudsbeurt toevoegd voor: GHI", driver.findElement(By.cssSelector("h3.msg > span")).getText());
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
