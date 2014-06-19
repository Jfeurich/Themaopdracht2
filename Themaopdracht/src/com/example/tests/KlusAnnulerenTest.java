package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class KlusAnnulerenTest {
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
	public void testKlusAnnuleren() throws Exception {
		driver.get(baseUrl + "/Themaopdracht/klus.jsp");
		driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
		assertEquals("Geen klussen gevonden met ingevoerde zoekterm(en)",
				driver.findElement(By.cssSelector("h3.msg > span")).getText());
		driver.findElement(By.name("zoekid")).clear();
		driver.findElement(By.name("zoekid")).sendKeys("abc");
		driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
		assertTrue(isElementPresent(By.cssSelector("h3.error")));
		driver.findElement(By.name("zoekid")).clear();
		driver.findElement(By.name("zoekid")).sendKeys("1");
		driver.findElement(By.xpath("(//input[@name='knop'])[3]")).click();
		driver.findElement(By.name("gekozenklus")).click();
		driver.findElement(By.xpath("(//input[@name='knop'])[5]")).click();
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
