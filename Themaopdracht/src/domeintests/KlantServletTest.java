package domeintests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import domeinklassen.Klant;
import domeinklassen.User;

@RunWith(value = Parameterized.class)
public class KlantServletTest {
	private User gebruiker;
	private Klant klant;
	private StringBuffer verificationErrors = new StringBuffer();
	private static int i;
	
	public KlantServletTest(int nummer){
		nummer = i;
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
			    gebruiker.setDeKlant(klant);
				klant.setUser(gebruiker);
			    try {
			      assertEquals(klant.toString(),"Naam " + naam + "; adres " + adres + "; woonplaats " + plaats + "; rekeningnummer " + rekeningnummer + "; telefoonnummer " + telefoonnummer);
			      assertEquals(gebruiker.toString(),"User username=" + gebruiker.getGebruikersnaam()+ ", password=" + gebruiker.getWachtwoord() + "\n" + "Account is van klant:\n" + klant.toString());
			      klant.setNaam("piet");
			      assertEquals(klant.getNaam(),"piet");
			      klant.setRekeningnummer(rekeningnummer);
			      assertEquals(rekeningnummer,"rekeningnummer");
			      assertEquals(gebruiker.getID(),1);
			      assertEquals(gebruiker.getType(),3);
			      
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
