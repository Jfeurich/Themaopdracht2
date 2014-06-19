package com.example.tests;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class US18_Statu_Factuur_Bijwerken {
	private WebDriver driver;
	private String baseUrl;
	@SuppressWarnings("unused")
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testStatusFactuur() throws Exception {
		driver.get(baseUrl + "/Themaopdracht/loginpage.jsp");
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("Henk");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("hww");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.get("http://localhost:8080/Themaopdracht/factuur.jsp");
		driver.findElement(By.cssSelector("div > input[name=\"knop\"]"))
				.click();
		driver.findElement(
				By.cssSelector("#content > form > p > input[name=\"knop\"]"))
				.click();
		driver.findElement(By.xpath("(//input[@name='betaalmiddel'])[3]"))
				.click();
		driver.findElement(
				By.cssSelector("#content > form > p > input[name=\"knop\"]"))
				.click();
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
