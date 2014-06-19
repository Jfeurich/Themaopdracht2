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

public class US25_Reservering_Annuleren {
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
	public void testReserveringAnnuleren() throws Exception {
		driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("Henk");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("hww");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.get("http://localhost:8080/Themaopdracht/reserveringannuleren.jsp");
		driver.findElement(By.cssSelector("div > p > input[name=\"knop\"]"))
				.click();
		driver.findElement(By.name("gekozenklant")).click();
		driver.findElement(By.cssSelector("div > p > input[name=\"knop\"]"))
				.click();
		driver.findElement(By.cssSelector("div > p > input[name=\"knop\"]"))
				.click();
		driver.findElement(By.cssSelector("div > p > input[name=\"knop\"]"))
				.click();
		assertEquals("De reservering is succesvol geannuleerd", driver
				.findElement(By.cssSelector("h3.msg > span")).getText());
		driver.findElement(By.cssSelector("div > p > input[name=\"knop\"]"))
				.click();
		driver.findElement(By.name("gekozenklant")).click();
		driver.findElement(By.cssSelector("div > p > input[name=\"knop\"]"))
				.click();
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
