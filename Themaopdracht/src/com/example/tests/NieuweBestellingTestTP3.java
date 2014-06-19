package com.example.tests;

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

public class NieuweBestellingTestTP3 {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testNieuweBestellingTestTP3() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.get("http://localhost:8080/Themaopdracht/nieuwebestelling.jsp");
    driver.findElement(By.name("gekozenProduct")).click();
    driver.findElement(By.xpath("(//input[@name='gekozenProduct'])[2]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.name("wijzigaantal")).clear();
    driver.findElement(By.name("wijzigaantal")).sendKeys("0");
    driver.findElement(By.xpath("(//input[@name='wijzigaantal'])[2]")).clear();
    driver.findElement(By.xpath("(//input[@name='wijzigaantal'])[2]")).sendKeys("0");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
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
