package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US07_Registreren {
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
	public void testRegistreer() throws Exception {
		driver.get(baseUrl + "/Themaopdracht/registreer.jsp");
		driver.findElement(By.name("gebrnaam")).clear();
		driver.findElement(By.name("gebrnaam")).sendKeys("Testje");
		driver.findElement(By.name("nm")).clear();
		driver.findElement(By.name("nm")).sendKeys("test");
		driver.findElement(By.name("ww1")).clear();
		driver.findElement(By.name("ww1")).sendKeys("test");
		driver.findElement(By.name("ww2")).clear();
		driver.findElement(By.name("ww2")).sendKeys("test");
		driver.findElement(By.name("mail1")).clear();
		driver.findElement(By.name("mail1")).sendKeys("test@hotmail.com");
		driver.findElement(By.name("mail2")).clear();
		driver.findElement(By.name("mail2")).sendKeys("test@hotmail.com");
		driver.findElement(By.name("adr")).clear();
		driver.findElement(By.name("adr")).sendKeys("teststraat");
		driver.findElement(By.name("wp")).clear();
		driver.findElement(By.name("wp")).sendKeys("teststadt");
		driver.findElement(By.name("telnr")).clear();
		driver.findElement(By.name("telnr")).sendKeys("061234567");
		driver.findElement(By.name("rnr")).clear();
		driver.findElement(By.name("rnr")).sendKeys("123456789");
		driver.findElement(By.name("knop")).click();
		assertEquals("Succesvol geregistreerd!",
		driver.findElement(By.cssSelector("h3.msg > span")).getText());
		driver.findElement(By.name("knop")).click();
		assertTrue(isElementPresent(By.cssSelector("h3.error")));
		driver.findElement(By.name("gebrnaam")).clear();
		driver.findElement(By.name("gebrnaam")).sendKeys("Testje");
		driver.findElement(By.name("nm")).clear();
		driver.findElement(By.name("nm")).sendKeys("Test");
		driver.findElement(By.name("ww1")).clear();
		driver.findElement(By.name("ww1")).sendKeys("testfout");
		driver.findElement(By.name("ww2")).clear();
		driver.findElement(By.name("ww2")).sendKeys("test");
		driver.findElement(By.name("mail1")).clear();
		driver.findElement(By.name("mail1")).sendKeys("test@hotmail.com");
		driver.findElement(By.name("mail2")).clear();
		driver.findElement(By.name("mail2")).sendKeys("testfout@hotmail.com");
		driver.findElement(By.name("adr")).clear();
		driver.findElement(By.name("adr")).sendKeys("teststraat");
		driver.findElement(By.name("wp")).clear();
		driver.findElement(By.name("wp")).sendKeys("teststadt");
		driver.findElement(By.name("telnr")).clear();
		driver.findElement(By.name("telnr")).sendKeys("061234567");
		driver.findElement(By.name("rnr")).clear();
		driver.findElement(By.name("rnr")).sendKeys("123456789");
		driver.findElement(By.name("knop")).click();
		assertTrue(isElementPresent(By.cssSelector("h3.error")));
		driver.findElement(By.name("gebrnaam")).clear();
		driver.findElement(By.name("gebrnaam")).sendKeys("Testje");
		driver.findElement(By.name("nm")).clear();
		driver.findElement(By.name("nm")).sendKeys("Test");
		driver.findElement(By.name("ww1")).clear();
		driver.findElement(By.name("ww1")).sendKeys("test");
		driver.findElement(By.name("ww2")).clear();
		driver.findElement(By.name("ww2")).sendKeys("test");
		driver.findElement(By.name("mail1")).clear();
		driver.findElement(By.name("mail1")).sendKeys("test@hotmail.com");
		driver.findElement(By.name("mail2")).clear();
		driver.findElement(By.name("mail2")).sendKeys("test@hotmail.com");
		driver.findElement(By.name("adr")).clear();
		driver.findElement(By.name("adr")).sendKeys("teststraat");
		driver.findElement(By.name("wp")).clear();
		driver.findElement(By.name("wp")).sendKeys("teststadt");
		driver.findElement(By.name("telnr")).clear();
		driver.findElement(By.name("telnr")).sendKeys("nullzesetc");
		driver.findElement(By.name("rnr")).clear();
		driver.findElement(By.name("rnr")).sendKeys("12345");
		driver.findElement(By.name("knop")).click();
		assertTrue(isElementPresent(By.cssSelector("h3.error")));
		driver.findElement(By.name("gebrnaam")).clear();
		driver.findElement(By.name("gebrnaam")).sendKeys("Testje");
		driver.findElement(By.name("nm")).clear();
		driver.findElement(By.name("nm")).sendKeys("Jiry");
		driver.findElement(By.name("ww1")).clear();
		driver.findElement(By.name("ww1")).sendKeys("hoi");
		driver.findElement(By.name("ww2")).clear();
		driver.findElement(By.name("ww2")).sendKeys("hoi");
		driver.findElement(By.name("mail1")).clear();
		driver.findElement(By.name("mail1")).sendKeys("jiry@hotmail.com");
		driver.findElement(By.name("mail2")).clear();
		driver.findElement(By.name("mail2")).sendKeys("jiry@hotmail.com");
		driver.findElement(By.name("adr")).clear();
		driver.findElement(By.name("adr")).sendKeys("lala");
		driver.findElement(By.name("wp")).clear();
		driver.findElement(By.name("wp")).sendKeys("hoihoi");
		driver.findElement(By.name("telnr")).clear();
		driver.findElement(By.name("telnr")).sendKeys("061234567");
		driver.findElement(By.name("rnr")).clear();
		driver.findElement(By.name("rnr")).sendKeys("12345");
		driver.findElement(By.name("knop")).click();
		assertTrue(isElementPresent(By.cssSelector("h3.error")));
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
}
