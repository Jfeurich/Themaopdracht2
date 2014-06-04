package com.example.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.Selenium;

public class NieuweGebruikerTest3 {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://127.0.0.1:8080/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testNieuweGebruikerTest3() throws Exception {
		selenium.open("/Themaopdracht/nieuwegebruikersaccount.jsp");
		selenium.click("xpath=(//input[@name='knop'])[2]");
		selenium.waitForPageToLoad("30000");
		// selenium.("css=div.bericht", "  De volgende velden zijn niet ingevuld: Gebruikersnaam Wachtwoord Wachtwoord controle Email Email controle naam adres woonplaats rekeningnummer telefoonnummer ");
		selenium.type("name=username", "test4");
		selenium.type("name=password", "test4");
		selenium.type("name=password2", "test4");
		selenium.click("name=type");
		selenium.type("name=email", "test");
		selenium.type("name=email2", "test");
		selenium.type("name=klantnummer", "test");
		selenium.type("name=naam", "test");
		selenium.type("name=adres", "test");
		selenium.type("name=plaats", "test");
		selenium.type("name=rekeningnummer", "test");
		selenium.type("name=telefoonnummer", "123");
		selenium.click("xpath=(//input[@name='knop'])[2]");
		selenium.waitForPageToLoad("30000");
		// selenium.("css=h3[name=\"msg\"]", " Gebruiker test4 Van type 0 succesvol geregistreerd! ");
		selenium.type("name=username", "test5");
		selenium.type("name=username", "test4");
		selenium.type("name=password", "test4");
		selenium.type("name=password2", "test4");
		selenium.click("xpath=(//input[@name='type'])[2]");
		selenium.type("name=email", "test");
		selenium.type("name=email2", "test");
		selenium.type("name=klantnummer", "test");
		selenium.type("name=naam", "test");
		selenium.type("name=adres", "test");
		selenium.type("name=plaats", "test");
		selenium.type("name=rekeningnummer", "test");
		selenium.type("name=telefoonnummer", "123");
		selenium.click("xpath=(//input[@name='knop'])[2]");
		selenium.waitForPageToLoad("30000");
		// selenium.("css=h3[name=\"msg\"]", "Gebruiker test4 Van type 1 succesvol geregistreerd! ");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
