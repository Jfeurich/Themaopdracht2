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

public class KlusOverzichtTest {
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
  public void testKlusOverzicht() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.jsp");
    driver.findElement(By.linkText("Hoofdmenu klussen(9)")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    driver.findElement(By.linkText("Hoofdmenu")).click();
    assertEquals("Kies", driver.findElement(By.cssSelector("th")).getText());
    assertEquals("Klus wijzigen", driver.findElement(By.xpath("//h2[2] > span")).getText());
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
