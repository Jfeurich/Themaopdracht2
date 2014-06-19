package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

public class US06_Factuur_Aanmaken {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  }

  @Test
  public void testUS6FactuurAanmaken() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.get("http:/localhost:8080/Themaopdracht/klus.jsp");
    driver.findElement(By.cssSelector("div > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.id("dat")).click();
    driver.findElement(By.linkText("20")).click();
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Test voor het maken van een factuur");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.name("zoekid")).clear();
    driver.findElement(By.name("zoekid")).sendKeys("4");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[4]")).click();
    driver.findElement(By.name("manuren")).clear();
    driver.findElement(By.name("manuren")).sendKeys("5");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.get("http:/localhost:8080/Themaopdracht/factuur.jsp");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.name("korting")).clear();
    driver.findElement(By.name("korting")).sendKeys("10");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("De factuur is verstuurd", driver.findElement(By.cssSelector("h3.msg > span")).getText());
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    assertEquals("4", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[4]/td[2]")).getText());
    assertEquals("10%", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[4]/td[4]")).getText());
    assertEquals("38.25", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[4]/td[5]")).getText());
    driver.get("http:/localhost:8080/Themaopdracht/klus.jsp");
    driver.findElement(By.cssSelector("div > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.name("beschrijving")).click();
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Test voor nieuwe factuur 2");
    driver.findElement(By.id("dat")).click();
    driver.findElement(By.linkText("22")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.get("http:/localhost:8080/Themaopdracht/factuur.jsp");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("Test voor nieuwe factuur 2"));
    driver.get("http:/localhost:8080/Themaopdracht/klus.jsp");
    driver.findElement(By.name("zoekid")).clear();
    driver.findElement(By.name("zoekid")).sendKeys("10");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[4]")).click();
    driver.findElement(By.name("manuren")).clear();
    driver.findElement(By.name("manuren")).sendKeys("2");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.get("http:/localhost:8080/Themaopdracht/factuur.jsp");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Test voor nieuwe factuur 2", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[3]/td[3]")).getText());
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.xpath("(//input[@name='gekozenklus'])[2]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Factuur aangemaakt", driver.findElement(By.cssSelector("h3.msg > span")).getText());
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
