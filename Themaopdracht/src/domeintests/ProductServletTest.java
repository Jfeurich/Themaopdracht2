package domeintests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;
import domeinklassen.GebruiktProduct;
import domeinklassen.Product;

@RunWith(value = Parameterized.class)
public class ProductServletTest {
	private StringBuffer verificationErrors = new StringBuffer();
	private static int i;
	private Date dat = new Date();
	private String naam;
	private int artikelNr;
	private int minimumAanwezig;
	private String eenheid;
	private int aantal;
	private double prijsPerStuk;
	private ArrayList<Product> producten = new ArrayList<Product>();
	private ArrayList<BesteldProduct> besteldeproducten = new ArrayList<BesteldProduct>();
	
	public ProductServletTest(int nummer){
		nummer = i;
	}
	  @Parameters
	  public static Collection<Object[]> data() {
		   Object[][] data = new Object[21][];
		   for(int i = 0; i < 21; i++){
			   Object[] zet = new Object[] {i};
			   data[i] = zet;
		   }
		   return Arrays.asList(data);
	  }
	  
	  @BeforeClass
	  	public void setUpBeforeClass() throws Exception{
		  String csvPersonen = "/Themaopdracht/Product.csv	";
			String cvsSplitBy = ";";	
			BufferedReader brp = null;
			String linep = "";
			try {
				brp = new BufferedReader(new FileReader(csvPersonen));
				while ((linep = brp.readLine()) != null) {
					String[] regel = linep.split(cvsSplitBy); //file is gescheiden door ;
				    naam = regel[0];
				    artikelNr = Integer.parseInt(regel[1]);
				    minimumAanwezig= Integer.parseInt(regel[2]);
				    eenheid = regel[3];
				    aantal = Integer.parseInt(regel[4]);
				    prijsPerStuk = Double.parseDouble(regel[5]);
				    //declaraties gevonden objecten
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
		}
	  }
	  
		
	  @SuppressWarnings("deprecation")
	  @Test
		public void test() throws Exception{					
				    try {
				    	Product p = new Product(naam,artikelNr,minimumAanwezig,eenheid,prijsPerStuk);
				    	GebruiktProduct gp = new GebruiktProduct(aantal, p);
				    	BesteldProduct bp = new BesteldProduct(p, aantal);
				    	Bestelling b = new Bestelling(aantal);
				    	b.setBestelNummer(aantal);
				    	Product hetProduct = p;
				    	besteldeproducten.add(bp);
				    	b.setBesteldeProducten(besteldeproducten);
				    	assertEquals(b.getIsGeleverd(),false);
				    	p.setAantal(aantal);
				    	assertEquals(p.toString(),"Naam: " + naam + "; Artikelnummer: " + artikelNr + "; Minimum aanwezig: " + minimumAanwezig + "; Eenheid: " + eenheid + "; Voorraad: " + aantal);
				    	assertEquals(gp.getKosten(),aantal*prijsPerStuk);
				    	assertEquals(gp.getHetProduct(),p);
				    	p.voegAantalToe(5);
				    	assertEquals(p.getAantal(),aantal+5);
				    	p.setNaam("naam");
				    	assertEquals(p.getNaam(),naam);
				    	assertEquals(bp.toString(),hetProduct.getNaam() + "; " + bp.getHoeveelheid() + " " + hetProduct.getEenheid());
				    	bp.setID(aantal);
				    	assertEquals(bp.getHoeveelheid(),aantal);
				    	assertEquals(bp.getProduct(),p);
				    	assertEquals(aantal,bp.getID());
				    	assertEquals(aantal,b.getBestelNummer());
				    	b.setVerwachteDatum(dat);
				    	assertEquals(b.getVerwachteDatum(),dat);
				    	
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
