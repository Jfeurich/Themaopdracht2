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

public class FiltersTest {
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
  public void testFilters() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/index.jsp");
    driver.findElement(By.linkText("Hoofdmenu producten(14, 27)")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    assertEquals("", driver.getCurrentUrl());
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("sandri");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("sww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.linkText("Hoofdmenu producten(14, 27)")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    assertEquals("Index van alle pagina's", driver.findElement(By.cssSelector("span")).getText());
    driver.findElement(By.linkText("Hoofdmenu producten(14, 27)")).click();
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("Jopie");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("jww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.linkText("Overzicht Facturen(18, 21)")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    assertEquals("Index van alle pagina's", driver.findElement(By.cssSelector("span")).getText());
    driver.findElement(By.linkText("Overzicht parkeerplaats(12)")).click();
    driver.findElement(By.name("begindat")).clear();
    driver.findElement(By.name("begindat")).sendKeys("01-01-2000");
    driver.findElement(By.name("einddat")).clear();
    driver.findElement(By.name("einddat")).sendKeys("01-01-2015");
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertEquals("BEZET", driver.findElement(By.xpath("//div[3]/table/tbody/tr/td")).getText());
    driver.findElement(By.xpath("(//input[@name='knop'])[5]")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.xpath("(//input[@name='gekozenklant'])[3]")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.name("begindatum")).clear();
    driver.findElement(By.name("begindatum")).sendKeys("01-01-2005");
    driver.findElement(By.name("einddatum")).clear();
    driver.findElement(By.name("einddatum")).sendKeys("01-02-2005");
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    assertEquals("Reservering met succes aangemaakt voor parkeerplaats: 1", driver.findElement(By.cssSelector("h3[name=\"msg\"] > span")).getText());
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("Mike");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("mww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.linkText("Overzicht Facturen(18, 21)")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    assertEquals("Index van alle pagina's", driver.findElement(By.cssSelector("span")).getText());
    driver.findElement(By.linkText("Overzicht parkeerplaats(12)")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[2]")).click();
    assertEquals("Index van alle pagina's", driver.findElement(By.cssSelector("span")).getText());
    driver.findElement(By.linkText("Klus wijzigen(5, 16)")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.xpath("(//input[@name='gekozenklant'])[3]")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.name("beschrijving")).clear();
    driver.findElement(By.name("beschrijving")).sendKeys("Day of the Beast\nSacrificed a few mosquitos.");
    driver.findElement(By.name("manuren")).clear();
    driver.findElement(By.name("manuren")).sendKeys("5");
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    assertEquals("Klus gewijzigd", driver.findElement(By.cssSelector("h3[name=\"msg\"] > span")).getText());
    driver.findElement(By.name("knop")).click();
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("Henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.linkText("Overzicht Facturen(18, 21)")).click();
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    assertEquals("De onbetaalde facturen zijn:", driver.findElement(By.cssSelector("h1 > span")).getText());
    driver.findElement(By.name("knop")).click();
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
