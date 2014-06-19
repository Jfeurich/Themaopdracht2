package domeintests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

	private StringBuffer verificationErrors = new StringBuffer();
	private ArrayList<Auto> autos = new ArrayList<Auto>();
	private static Date dat = new Date();
	private static int[] klantnummer = new int[11];
	private static String[] naam = new String[11];
	private static String[] adres = new String[11];
	private static String[] plaats = new String [11];
	private static String[] rekeningnummer = new String[11];
	private static int[] telefoonnummer = new int[11];

	private static int nummer;
	
	public KlantServletTest(int nr){
		nr = nummer;
	}
	  @Parameters
	  public static Collection<Object[]> data() {
		   Object[][] data = new Object[11][];
		   for(int i = 0; i < 11; i++){
			   Object[] zet = new Object[] {i};
			   data[i] = zet;
		   }
		   return Arrays.asList(data);
	  }
	  
	  @BeforeClass
	  	public static void setUpBeforeClass() throws Exception{
		  String csvPersonen = "/users/jayfeurich/Themaopdracht2/Themaopdracht/Klant.csv";
			String cvsSplitBy = ";";	
			BufferedReader brp = null;
			String linep = "";
			try {
				brp = new BufferedReader(new FileReader(csvPersonen));
				while ((linep = brp.readLine()) != null) {
					String[] regel = linep.split(cvsSplitBy); //file is gescheiden door ;
				    klantnummer[nummer] = 0;
				    naam[nummer] = "";
				    adres[nummer] = "";
				    plaats[nummer] = "";
				    rekeningnummer[nummer] = "";
				    telefoonnummer[nummer] = 0;
				    try{
					    klantnummer[nummer] = Integer.parseInt(regel[0]);
					    naam[nummer] = regel[1];
					    adres[nummer] = regel[2];
					    plaats[nummer] = regel[3];
					    rekeningnummer[nummer] = regel[4];
					    telefoonnummer[nummer] = Integer.parseInt(regel[5]);
				    }catch(ArrayIndexOutOfBoundsException e){ }
				    nummer++;
				}
			}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			} finally {
			if (brp != null) {
				try {
					brp.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			nummer = 0;
		}
	  }
	  
		
	  @SuppressWarnings("deprecation")
	  @Test
		public void test() throws Exception{
		  int knr = klantnummer[nummer];
		  String nm = naam[nummer];
		  String adr = adres[nummer];
		  String ps = plaats[nummer];
		  String rkn = rekeningnummer[nummer];
		  int tnr = telefoonnummer[nummer];
		  
		    Klant klant = new Klant(knr, nm, adr, ps, rkn, tnr);
		    User gebruiker = new User(1, 3, klant.getNaam(),"wachtwoord", "test@test.test");
		    Auto auto = new Auto("kenteken", "merk", "type", klant);
		    Klus klus = new Reparatie(dat, "testreparatie");
			Klus klus2 = new Onderhoudsbeurt(dat, "testrekeningnummer");
			auto.voegKlusToe(klus);
			auto.voegKlusToe(klus2);
		    gebruiker.setDeKlant(klant);
			klant.setUser(gebruiker);
			autos.add(auto);
			klant.setAutos(autos);
				    try {
			      assertEquals(klant.toString(),"Naam " + nm + "; adres " + adr + "; woonplaats " + ps + "; rekeningnummer " + rkn + "; telefoonnummer " + tnr);
			      assertEquals(gebruiker.toString(),"User username=" + gebruiker.getGebruikersnaam()+ ", password=" + gebruiker.getWachtwoord() + "\n" + "Account is van klant:\n" + klant.toString());
			      klant.setNaam("piet");
			      assertEquals(klant.getNaam(),"piet");
			      assertEquals(klant.getAdres(),adr);
			      assertEquals(klant.getPlaats(),ps);
			      klant.setRekeningnummer(rkn);
			      assertEquals(klant.getRekeningnummer(),"rkn");
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
			      //hier nog iets over factuur 
			    } 
			    catch (Error e) {
			      verificationErrors.append(e.toString());
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