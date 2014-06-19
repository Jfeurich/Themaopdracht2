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

public class US13_Rollen_Gebruikersaccounts {
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
  public void testUS13RollenGebruikersaccounts() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("U bent ingelogd als: Administratie", driver.findElement(By.cssSelector("h2")).getText());
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("mike");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("mww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("U bent ingelogd als: Monteur", driver.findElement(By.cssSelector("h2")).getText());
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("jopie");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("jww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("U bent ingelogd als: Parkeergaragebeheerder", driver.findElement(By.cssSelector("h2")).getText());
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("sandri");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("sww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("Mijn herinneringen", driver.findElement(By.cssSelector("h2")).getText());
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
