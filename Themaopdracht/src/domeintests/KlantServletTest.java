package domeintests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.Klus;
import domeinklassen.Onderhoudsbeurt;
import domeinklassen.Reparatie;
import domeinklassen.User;

@RunWith(value = Parameterized.class)
public class KlantServletTest {
	private User gebruiker;
	private Klant klant;
	private Auto auto;
	private Klus klus,klus2;
	private StringBuffer verificationErrors = new StringBuffer();
	private static int i;
	private ArrayList<Auto> autos = new ArrayList<Auto>();
	private ArrayList<Klus> klussen = new ArrayList<Klus>();
	private Date dat = new Date();
	
	public KlantServletTest(int nummer){
		nummer = i;
	}
	  @Parameters
	  public static Collection<Object[]> data() {
		   Object[][] data = new Object[20][];
		   for(int i = 0; i < 20; i++){
			   Object[] zet = new Object[] {i};
			   data[i] = zet;
		   }
		   return Arrays.asList(data);
	  }
		
	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		String csvPersonen = "/Themaopdracht/Klant.csv	";
		String cvsSplitBy = ";";	
		BufferedReader brp = null;
		String linep = "";
		try {
			brp = new BufferedReader(new FileReader(csvPersonen));
			while ((linep = brp.readLine()) != null) {
				String[] regel = linep.split(cvsSplitBy); //file is gescheiden door ;
			    int klantnummer = Integer.parseInt(regel[0]);
			    String naam = regel[1];
			    String adres = regel[2];
			    String plaats = regel[3];
			    String rekeningnummer = regel[4];
			    int telefoonnummer = Integer.parseInt(regel[5]);
			    klant = new Klant(klantnummer, naam, adres, plaats, rekeningnummer, telefoonnummer);
			    gebruiker = new User(1, 3, klant.getNaam(),"wachtwoord", "test@test.test");
			    auto = new Auto("kenteken", "merk", "type", klant);
			    klus = new Reparatie(dat, "testreparatie");
				klus2 = new Onderhoudsbeurt(dat, "testrekeningnummer");
				auto.voegKlusToe(klus);
				auto.voegKlusToe(klus2);
			    gebruiker.setDeKlant(klant);
				klant.setUser(gebruiker);
				autos.add(auto);
				klant.setAutos(autos);
				
				
			    try {
			      assertEquals(klant.toString(),"Naam " + naam + "; adres " + adres + "; woonplaats " + plaats + "; rekeningnummer " + rekeningnummer + "; telefoonnummer " + telefoonnummer);
			      assertEquals(gebruiker.toString(),"User username=" + gebruiker.getGebruikersnaam()+ ", password=" + gebruiker.getWachtwoord() + "\n" + "Account is van klant:\n" + klant.toString());
			      klant.setNaam("piet");
			      assertEquals(klant.getNaam(),"piet");
			      assertEquals(klant.getAdres(),adres);
			      assertEquals(klant.getPlaats(),plaats);
			      klant.setRekeningnummer(rekeningnummer);
			      assertEquals(rekeningnummer,"rekeningnummer");
			      assertEquals(gebruiker.getID(),1);
			      assertEquals(gebruiker.getType(),3);
			      assertEquals(gebruiker.getWachtwoord(),"wachtwoord");
			      gebruiker.setEmail("test2@test.test");
			      assertEquals(gebruiker.getEmail(),"test2@test.test");
			      klus.addManuren(5);
			      klus2.addManuren(1700);
			      assertEquals(klus.getManuren(),5);
			      assertEquals(klus2.getManuren(),1700);
			      assertEquals(klus.berekenKosten(),42.50);
			      assertEquals(klus2.berekenKosten(),(1700*9.50));
			      assertEquals(klus.getBeschrijving(),"testreparatie");
			      assertEquals(klus2.getBeschrijving(),"testrekeningnummer");
			      assertEquals(klus.getStatus(),"Nog niet begonnen");
			      klus.setStatus("voltooid");
			      assertEquals(klus.getStatus(),"voltooid");
			      
			      
			      
			      
			      
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
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
