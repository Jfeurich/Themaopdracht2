package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BTWOverzichtTest {
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
  public void testBTWOverzicht() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.jsp");
    driver.findElement(By.linkText("Overzicht BTW(22)")).click();
    driver.findElement(By.name("knop")).click();
    assertEquals("Vul een geldig begin- en eindjaar in!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("iets");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("iets");
    driver.findElement(By.name("knop")).click();
    assertEquals("Vul een geldig begin- en eindjaar in!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("-300");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("-300");
    driver.findElement(By.name("knop")).click();
    assertEquals("Vul een geldig begin- en eindjaar in!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("1900");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("1910");
    driver.findElement(By.name("knop")).click();
    assertEquals("Dit programma gaat niet verder terug dan 1950!", driver.findElement(By.cssSelector("p[name=\"error\"]")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("2000");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("2005");
    driver.findElement(By.xpath("(//input[@name='eindkwartaal'])[4]")).click();
    driver.findElement(By.name("knop")).click();
    assertEquals("Geen facturen beschikbaar tussen deze data!", driver.findElement(By.cssSelector("h3[name=\"msg\"] > span")).getText());
    driver.findElement(By.name("beginjaar")).clear();
    driver.findElement(By.name("beginjaar")).sendKeys("2014");
    driver.findElement(By.name("eindjaar")).clear();
    driver.findElement(By.name("eindjaar")).sendKeys("2015");
    driver.findElement(By.xpath("(//input[@name='eindkwartaal'])[4]")).click();
    driver.findElement(By.name("knop")).click();
    assertEquals("Alle betaalde facturen", driver.findElement(By.xpath("//div[2]/h2/span")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
