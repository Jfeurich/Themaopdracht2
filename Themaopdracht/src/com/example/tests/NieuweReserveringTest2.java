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

public class NieuweReserveringTest2 {
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
  public void testNieuweReserveringTest2() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("Jopie");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("jww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.linkText("Overzicht parkeerplaats(12)")).click();
    driver.findElement(By.id("dp1401098640962")).click();
    driver.findElement(By.linkText("1")).click();
    driver.findElement(By.id("dp1401098640963")).click();
    driver.findElement(By.linkText("4")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertEquals("BEZET", driver.findElement(By.xpath("//div[3]/table/tbody/tr/td[5]")).getText());
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    assertEquals("Auto met kenteken ABC is een Fiesta van Ford Parkeerplek: 2 Van: 01-05-2014 Tot: 04-05-2014", driver.findElement(By.cssSelector("div > p")).getText());
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
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
