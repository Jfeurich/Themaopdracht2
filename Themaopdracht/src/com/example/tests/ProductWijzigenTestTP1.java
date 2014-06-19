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

public class ProductWijzigenTestTP1 {
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
  public void testProductWijzigenTestTP1() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.get("http://localhost:8080/Themaopdracht/product.jsp");
    driver.findElement(By.name("zoeknummer")).clear();
    driver.findElement(By.name("zoeknummer")).sendKeys("1");
    driver.findElement(By.xpath("(//input[@name='knop'])[5]")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[5]")).click();
    driver.findElement(By.name("aantal")).clear();
    driver.findElement(By.name("aantal")).sendKeys("10");
    driver.findElement(By.name("minaantal")).clear();
    driver.findElement(By.name("minaantal")).sendKeys("20");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Het product is gewijzigd.", driver.findElement(By.cssSelector("h3.msg > span")).getText());
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
