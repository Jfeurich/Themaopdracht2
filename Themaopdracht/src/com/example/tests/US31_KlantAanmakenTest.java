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

public class US31_KlantAanmakenTest {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://127.0.0.1:8080/Leasemaatschappij/index.jsp";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUS31KlantAanmaken() throws Exception {
    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("Henk");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("hww");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.get("http://localhost:8080/Themaopdracht/nieuwegebruikersaccount.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("klant");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("klant");
    driver.findElement(By.name("password2")).clear();
    driver.findElement(By.name("password2")).sendKeys("klant");
    driver.findElement(By.xpath("(//input[@name='type'])[4]")).click();
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("klant@klant.klant");
    driver.findElement(By.name("email2")).clear();
    driver.findElement(By.name("email2")).sendKeys("klant@klant.klant");
    driver.findElement(By.name("naam")).clear();
    driver.findElement(By.name("naam")).sendKeys("klant");
    driver.findElement(By.name("adres")).clear();
    driver.findElement(By.name("adres")).sendKeys("klant");
    driver.findElement(By.name("plaats")).clear();
    driver.findElement(By.name("plaats")).sendKeys("klant");
    driver.findElement(By.name("rekeningnummer")).clear();
    driver.findElement(By.name("rekeningnummer")).sendKeys("klant");
    driver.findElement(By.name("telefoonnummer")).clear();
    driver.findElement(By.name("telefoonnummer")).sendKeys("123");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    try {
      assertEquals("Gebruiker klant met Klantnummer 4 succesvol geregistreerd!", driver.findElement(By.cssSelector("h3.msg > span")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.get("http://localhost:8080/Themaopdracht/nieuwegebruikersaccount.jsp");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("klant");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("klant");
    driver.findElement(By.name("password2")).clear();
    driver.findElement(By.name("password2")).sendKeys("klant");
    driver.findElement(By.xpath("(//input[@name='type'])[4]")).click();
    driver.findElement(By.name("email")).click();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("klant@klant.klant");
    driver.findElement(By.name("email2")).clear();
    driver.findElement(By.name("email2")).sendKeys("klant@klant.klant");
    driver.findElement(By.name("naam")).clear();
    driver.findElement(By.name("naam")).sendKeys("klant");
    driver.findElement(By.name("adres")).clear();
    driver.findElement(By.name("adres")).sendKeys("klant");
    driver.findElement(By.name("plaats")).clear();
    driver.findElement(By.name("plaats")).sendKeys("klant");
    driver.findElement(By.name("rekeningnummer")).clear();
    driver.findElement(By.name("rekeningnummer")).sendKeys("klant");
    driver.findElement(By.name("telefoonnummer")).clear();
    driver.findElement(By.name("telefoonnummer")).sendKeys("123");
    driver.findElement(By.cssSelector("#content > form > p > input[name=\"knop\"]")).click();
    try {
      assertEquals("Error! \n Er bestaat al een gebruiker met deze gebruikersnaam en/of dit emailadres!", driver.findElement(By.cssSelector("h3.error")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
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
