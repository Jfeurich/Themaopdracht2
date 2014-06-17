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

public class KlantZietEigenAutosTest {
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
  public void testKlantZietEigenAutos() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("tris");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("tww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("Volkswagen", driver.findElement(By.xpath("//div[@id='content']/div[2]/table/tbody/tr[2]/td[2]")).getText());
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("sandri");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("sww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("Ford", driver.findElement(By.xpath("//div[@id='content']/div[2]/table/tbody/tr[2]/td[2]")).getText());
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("daj");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("dww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("Fiat", driver.findElement(By.xpath("//div[@id='content']/div[2]/table/tbody/tr[2]/td[2]")).getText());
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
