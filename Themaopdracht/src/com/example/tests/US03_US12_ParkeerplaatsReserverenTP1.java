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

public class US03_US12_ParkeerplaatsReserverenTP1 {
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
  public void testParkeerplaatsReserverenTP1() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("sandri");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("sww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.get("http://localhost:8080/Themaopdracht/parkeerplaatsoverzicht.jsp");
    driver.findElement(By.name("begindat")).click();
    driver.findElement(By.name("begindat")).clear();
    driver.findElement(By.name("begindat")).sendKeys("01-07-2014");
    driver.findElement(By.name("einddat")).click();
    driver.findElement(By.name("einddat")).clear();
    driver.findElement(By.name("einddat")).sendKeys("14-07-2014");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    driver.findElement(By.cssSelector("#parkeerplaatsoverzicht > table > tbody > tr > td > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Auto met kenteken ABC is een Fiesta van Ford", driver.findElement(By.cssSelector("#content > form > p")).getText());
    assertEquals("Parkeerplek: 1", driver.findElement(By.xpath("//div[@id='content']/form/p[2]")).getText());
    assertEquals("Van: 01-07-2014", driver.findElement(By.xpath("//div[@id='content']/form/p[4]")).getText());
    assertEquals("Tot: 14-07-2014", driver.findElement(By.xpath("//div[@id='content']/form/p[5]")).getText());
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("Reservering met succes aangemaakt voor parkeerplaats: 1", driver.findElement(By.cssSelector("h3.msg > span")).getText());
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
