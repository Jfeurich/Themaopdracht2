package com.example.tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.*;//lol

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class BBTKlant {
  private static WebDriver driver;
  private static String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer(); 
  private int i;
  
  public BBTKlant(int number) {
     i = number;
  }
  @Parameters
  public static Collection<Object[]> data() {
	   Object[][] data = new Object[30][];
	   for(int i = 0; i < 30; i++){
		   Object[] zet = new Object[] {i};
		   data[i] = zet;
	   }
	   return Arrays.asList(data);
  }

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  driver = new FirefoxDriver();
	  baseUrl = "http://127.0.0.1:8080/";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
	    driver.findElement(By.name("username")).clear();
	    driver.findElement(By.name("username")).sendKeys("Henk");
	    driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys("hww");
	    driver.findElement(By.cssSelector("form > input[type=\"submit\"]")).click();
	    driver.findElement(By.linkText("Registreer nieuwe klant(7)")).click();
  }

  @Test
  public void testBBTKlant() throws Exception {
		String csvPersonen = "/Themaopdracht/BBT_Klant.csv	";
		String cvsSplitBy = ";";	
		BufferedReader brp = null;
		String linep = "";
		try {
			brp = new BufferedReader(new FileReader(csvPersonen));
			while ((linep = brp.readLine()) != null) {
				String[] regel = linep.split(cvsSplitBy); //file is gescheiden door ;
			    String gebrnaam = regel[0];
			    String nm = regel[1];
			    String ww1 = regel[2];
			    String ww2 = regel[3];
			    String mail1 = regel[4];
			    String mail2 = regel[5];
			    String adr = regel[6];
			    String wp = regel[6];
			    String telnr = regel[6];
			    String rnr = regel[6];
			    driver.get(baseUrl + "/Themaopdracht/index.jsp");
			    driver.findElement(By.linkText("Registreer nieuwe klant(7)")).click();
			    driver.findElement(By.name("gebrnaam")).clear();
			    driver.findElement(By.name("gebrnaam")).sendKeys(gebrnaam);
			    driver.findElement(By.name("nm")).clear();
			    driver.findElement(By.name("nm")).sendKeys(nm);
			    driver.findElement(By.name("ww1")).clear();
			    driver.findElement(By.name("ww1")).sendKeys(ww1);
			    driver.findElement(By.name("ww2")).clear();
			    driver.findElement(By.name("ww2")).sendKeys(ww2);
			    driver.findElement(By.name("mail1")).clear();
			    driver.findElement(By.name("mail1")).sendKeys(mail1);
			    driver.findElement(By.name("mail2")).clear();
			    driver.findElement(By.name("mail2")).sendKeys(mail2);
			    driver.findElement(By.name("adr")).clear();
			    driver.findElement(By.name("adr")).sendKeys(adr);
			    driver.findElement(By.name("wp")).clear();
			    driver.findElement(By.name("wp")).sendKeys(wp);
			    driver.findElement(By.name("telnr")).clear();
			    driver.findElement(By.name("telnr")).sendKeys(telnr);
			    driver.findElement(By.name("rnr")).clear();
			    driver.findElement(By.name("rnr")).sendKeys(rnr);
			    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
			    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
			    try {
			      assertEquals("Succesvol geregistreerd!", driver.findElement(By.cssSelector("h3[name=\"msg\"] > span")).getText());
			    } catch (Error e) {
			      verificationErrors.append(e.toString());
			    }
			}	 
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
			} finally {
			if (brp != null) {
				try {
					brp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
