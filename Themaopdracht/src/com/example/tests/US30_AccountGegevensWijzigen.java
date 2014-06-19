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

public class US30_AccountGegevensWijzigen {
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
  public void testUS30AccountGegevensWijzigen() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("sandri");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("sww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.linkText("Mijn Account")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("sari@hotmail.com");
    driver.findElement(By.name("wachtwoord")).clear();
    driver.findElement(By.name("wachtwoord")).sendKeys("sw");
    driver.findElement(By.name("naam")).clear();
    driver.findElement(By.name("naam")).sendKeys("Sandrileine");
    driver.findElement(By.name("adr")).clear();
    driver.findElement(By.name("adr")).sendKeys("Padualaan 2");
    driver.findElement(By.name("wp")).clear();
    driver.findElement(By.name("wp")).sendKeys("Rotterdam");
    driver.findElement(By.name("telnr")).clear();
    driver.findElement(By.name("telnr")).sendKeys("103333333");
    driver.findElement(By.name("rnr")).clear();
    driver.findElement(By.name("rnr")).sendKeys("SandriRekening");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    driver.findElement(By.name("bevestigwachtwoord")).clear();
    driver.findElement(By.name("bevestigwachtwoord")).sendKeys("sww");
    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
    assertEquals("Email: sari@hotmail.com", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[2]/th")).getText());
    assertEquals("Naam: Sandrilene", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[4]/th")).getText());
    assertEquals("Adres: Padualaan 2", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[5]/th")).getText());
    assertEquals("Woonplaats: Rotterdam", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[6]/th")).getText());
    assertEquals("Telefoonnummer: 103333333", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[7]/th")).getText());
    assertEquals("Rekeningnummer: SandriRekening", driver.findElement(By.xpath("//div[@id='content']/form/table/tbody/tr[8]/th")).getText());
    assertEquals("Wijzigingen opgeslagen!", driver.findElement(By.cssSelector("h3.msg > span")).getText());
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
