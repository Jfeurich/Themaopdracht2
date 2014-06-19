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

public class US5_Artikel_Toevoegen_Aan_Klus {
  private static WebDriver driver;
  private static String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass
  public static void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    //Log in als Henk om producten in het systeem te zetten
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.get("http://localhost:8080//Themaopdracht/product.jsp");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.name("wijzigaantal")).clear();
    driver.findElement(By.name("wijzigaantal")).sendKeys("10");
    driver.findElement(By.xpath("(//input[@name='wijzigaantal'])[2]")).clear();
    driver.findElement(By.xpath("(//input[@name='wijzigaantal'])[2]")).sendKeys("50");
    driver.findElement(By.xpath("(//input[@name='wijzigaantal'])[3]")).clear();
    driver.findElement(By.xpath("(//input[@name='wijzigaantal'])[3]")).sendKeys("50");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    driver.get("http://localhost:8080//Themaopdracht/bestelling.jsp");
    driver.findElement(By.xpath("(//input[@name='gekozenbestelling'])[4]")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.name("knop")).click();
  }

  @Test
  public void testUS5ArtikelToevoegenAanKlus() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("mike");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("mww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.get("http://localhost:8080//Themaopdracht/klus.jsp");
    driver.findElement(By.name("zoekid")).clear();
    driver.findElement(By.name("zoekid")).sendKeys("1");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[4]")).click();
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Geen producten toegevoegd!", driver.findElement(By.cssSelector("h3.msg > span")).getText());
    driver.findElement(By.name("product")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
    driver.findElement(By.name("product")).click();
    driver.findElement(By.name("aantal")).clear();
    driver.findElement(By.name("aantal")).sendKeys("iets");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertTrue(isElementPresent(By.cssSelector("h3.error")));
    driver.findElement(By.name("product")).click();
    driver.findElement(By.name("aantal")).clear();
    driver.findElement(By.name("aantal")).sendKeys("1");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.xpath("(//input[@name='status'])[2]")).click();
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Product toegevoegd");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.name("zoekid")).clear();
    driver.findElement(By.name("zoekid")).sendKeys("1");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    assertEquals("1", driver.findElement(By.xpath("//div[@id='content']/form/div[2]/table/tbody/tr[5]/td[2]")).getText());
    assertEquals("1", driver.findElement(By.xpath("//div[@id='content']/form/div[2]/table/tbody/tr[5]/td[4]")).getText());
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
