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

public class US15_US16_Klus_Wijzigen_Manuren_Status {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("mike");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("mww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  }

  @Test
  public void testUS15US16KlusWijzigenManurenStatus() throws Exception {
    driver.get("http:/localhost:8080/Themaopdracht/klus.jsp");
    driver.findElement(By.name("zoekid")).clear();
    driver.findElement(By.name("zoekid")).sendKeys("3");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[4]")).click();
    driver.findElement(By.name("manuren")).clear();
    driver.findElement(By.name("manuren")).sendKeys("3");
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Check op beschrijving wijzigen");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    driver.findElement(By.name("zoekid")).clear();
    driver.findElement(By.name("zoekid")).sendKeys("3");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    assertEquals("Check op beschrijving wijzigen", driver.findElement(By.xpath("//div[@id='content']/form/div[2]/table/tbody/tr[2]/td[4]")).getText());
    driver.findElement(By.xpath("(//input[@name='knop'])[4]")).click();
    assertEquals("Manuren toevoegen (Huidige uren: 3)", driver.findElement(By.xpath("//div[@id='content']/form/p[4]")).getText());
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    assertEquals("Klus gewijzigd", driver.findElement(By.cssSelector("h3.msg > span")).getText());
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
