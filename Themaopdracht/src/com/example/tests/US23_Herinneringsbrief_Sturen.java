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

public class US23_Herinneringsbrief_Sturen {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  }

  @Test
  public void testUS23HerinneringsbriefSturen() throws Exception {
    driver.get("http:/localhost:8080/Themaopdracht/nieuwebrief.jsp");
    driver.findElement(By.linkText("Nieuwe herinneringsbrief")).click();
    driver.findElement(By.xpath("(//input[@name='gekozenklant'])[2]")).click();
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    assertEquals("Beste Trisana,   Het is ons op gevallen dat u sinds 12-12-2012 niet meer langs bent geweest bij ATD.   Hopelijk kunnen wij u in de toekomst weer van dienst zijn.   Met vriendelijke groet,   De medewerkers van AutoTotaalDienst Utrecht", driver.findElement(By.name("reden")).getText());
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("Brief met succes verstuurd!", driver.findElement(By.cssSelector("h3.msg > span")).getText());
    driver.get("http:/localhost:8080/Themaopdracht/factuur.jsp");
    driver.findElement(By.cssSelector("div > input[name=\"knop\"]")).click();
    driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
    assertEquals("Beste Trisana,   Graag willen wij u helpen herinneren dat er sinds 12-12-2012 een factuur open staat ter waarde van 40.0 euro.  Wij verzoeken u vriendelijk bovenstaande bedrag binnen 5 werkdagen over te maken.   Vertrouwede u hiermee voldoende te hebben geinformeerd.   Met vriendelijke groet,   De medewerkers van AutoTotaalDienst Utrecht", driver.findElement(By.name("reden")).getText());
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("Brief met succes verstuurd!", driver.findElement(By.cssSelector("h3.msg > span")).getText());
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
