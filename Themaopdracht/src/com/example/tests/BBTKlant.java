package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;//lol
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(value = Parameterized.class)
public class BBTKlant {
  private static WebDriver driver;
  private static String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer(); 
  private static String[] gebrnaam = new String[30];
  private static String[] nm = new String[30];
  private static String[] ww1 = new String[30];
  private static String[] ww2 = new String[30];
  private static String[] mail1 = new String[30];
  private static String[] mail2 = new String[30];
  private static String[] adr = new String[30];
  private static String[] wp = new String[30];
  private static String[] telnr = new String[30];
  private static String[] rnr = new String[30];
  private static int i;
  
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
	  baseUrl = "http://localhost:8080/";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    driver.get(baseUrl + "Themaopdracht/loginpage.jsp");
	    driver.findElement(By.name("username")).clear();
	    driver.findElement(By.name("username")).sendKeys("Henk");
	    driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys("hww");
	    driver.findElement(By.cssSelector("form > input[type=\"submit\"]")).click();
		String csvPersonen = "/Themaopdracht/BBT_Klant.csv	";
		String cvsSplitBy = ";";	
		BufferedReader brp = null;
		String linep = "";
		try {
			brp = new BufferedReader(new FileReader(csvPersonen));
			while ((linep = brp.readLine()) != null) {
				String[] regel = linep.split(cvsSplitBy); //file is gescheiden door ;
			    gebrnaam[i] = regel[0];
			    nm[i] = regel[1];
			    ww1[i] = regel[2];
			    ww2[i] = regel[3];
			    mail1[i] = regel[4];
			    mail2[i] = regel[5];
			    adr[i] = regel[6];
			    wp[i] = regel[7];
			    telnr[i] = regel[8];
			    rnr[i] = regel[9]; 
			    i++;
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

  @Test
  public void testBBTKlant() throws Exception {
	    driver.get(baseUrl + "/Themaopdracht/index.jsp");
	    driver.findElement(By.linkText("Registreer nieuwe klant(7)")).click();
	    driver.findElement(By.name("gebrnaam")).clear();
	    driver.findElement(By.name("gebrnaam")).sendKeys(gebrnaam[i]);
	    driver.findElement(By.name("nm")).clear();
	    driver.findElement(By.name("nm")).sendKeys(nm[i]);
	    driver.findElement(By.name("ww1")).clear();
	    driver.findElement(By.name("ww1")).sendKeys(ww1[i]);
	    driver.findElement(By.name("ww2")).clear();
	    driver.findElement(By.name("ww2")).sendKeys(ww2[i]);
	    driver.findElement(By.name("mail1")).clear();
	    driver.findElement(By.name("mail1")).sendKeys(mail1[i]);
	    driver.findElement(By.name("mail2")).clear();
	    driver.findElement(By.name("mail2")).sendKeys(mail2[i]);
	    driver.findElement(By.name("adr")).clear();
	    driver.findElement(By.name("adr")).sendKeys(adr[i]);
	    driver.findElement(By.name("wp")).clear();
	    driver.findElement(By.name("wp")).sendKeys(wp[i]);
	    driver.findElement(By.name("telnr")).clear();
	    driver.findElement(By.name("telnr")).sendKeys(telnr[i]);
	    driver.findElement(By.name("rnr")).clear();
	    driver.findElement(By.name("rnr")).sendKeys(rnr[i]);
	    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
	    driver.findElement(By.cssSelector("form > input[name=\"knop\"]")).click();
	    assertEquals("Succesvol geregistreerd!", driver.findElement(By.cssSelector("h3[name=\"msg\"] > span")).getText());
	  }

  @After
  public void tearDown() throws Exception {
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
  
  @AfterClass
  public static void shutDown() throws Exception {
    driver.quit();
  }

}
