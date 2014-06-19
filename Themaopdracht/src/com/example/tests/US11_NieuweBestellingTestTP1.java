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

public class US11_NieuweBestellingTestTP1 {
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
  public void testNieuweBestellingTestTP1() throws Exception {
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
    driver.findElement(By.name("wijzigaantal")).sendKeys("20");
    driver.findElement(By.xpath("(//input[@name='wijzigaantal'])[2]")).clear();
    driver.findElement(By.xpath("(//input[@name='wijzigaantal'])[2]")).sendKeys("60");
    assertEquals("Totaalprijs: 830 euro", driver.findElement(By.id("totaalprijs")).getText());
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertEquals("Bestelnummer: 4; is niet geleverd; nog geen verwachte leverdatum bekend", driver.findElement(By.cssSelector("#content > form > p")).getText());
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
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
