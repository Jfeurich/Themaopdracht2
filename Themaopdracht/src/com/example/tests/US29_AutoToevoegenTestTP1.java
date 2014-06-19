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

public class US29_AutoToevoegenTestTP1 {
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
  public void testUS29AutoToevoegenTestTP1() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.get("http://localhost:8080/Themaopdracht/autotoevoegen.jsp");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.name("kenteken")).clear();
    driver.findElement(By.name("kenteken")).sendKeys("AA1122");
    driver.findElement(By.name("merk")).clear();
    driver.findElement(By.name("merk")).sendKeys("Ferrari");
    driver.findElement(By.name("type")).clear();
    driver.findElement(By.name("type")).sendKeys("Die snelle");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Auto met succes toegevoegd!", driver.findElement(By.cssSelector("h3.msg > span")).getText());
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
