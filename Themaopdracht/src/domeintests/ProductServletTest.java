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

import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;
import domeinklassen.GebruiktProduct;
import domeinklassen.Product;

@RunWith(value = Parameterized.class)
public class ProductServletTest {
	private StringBuffer verificationErrors = new StringBuffer();
	private Date dat = new Date();
	private static String[] naam = new String[21];
	private static String[] artikelNr = new String[21];
	private static String[] minimumAanwezig = new String[21];
	private static String[] eenheid = new String[21];
	private static String[] aantal = new String[21];
	private static String[] prijsPerStuk = new String[21];
	private static int nummer;
	private ArrayList<BesteldProduct> besteldeproducten = new ArrayList<BesteldProduct>();
	private static final double DELTA = 1e-5;
	
	public ProductServletTest(int nr){
		nr = nummer;
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
	  	public static void setUpBeforeClass() throws Exception{
		  String csvPersonen = "/users/jayfeurich/Themaopdracht2/Themaopdracht/Product.csv";
			String cvsSplitBy = ";";	
			BufferedReader brp = null;
			String linep = ">";
			try {
				brp = new BufferedReader(new FileReader(csvPersonen));
				while ((linep = brp.readLine()) != null) {
					String[] regel = linep.split(cvsSplitBy); //file is gescheiden door ;
					naam[nummer] = "";
				    artikelNr[nummer] = "";
				    minimumAanwezig[nummer]= "";
				    eenheid[nummer] = "";
				    aantal[nummer] = "";
				    prijsPerStuk[nummer] = "";
					try{
					    naam[nummer] = regel[0];
					    artikelNr[nummer] = regel[1];
					    minimumAanwezig[nummer]= regel[2];
					    eenheid[nummer] = regel[3];
					    aantal[nummer] = regel[4];
					    prijsPerStuk[nummer] = regel[5];
					}
					catch(ArrayIndexOutOfBoundsException e){
					}
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
	  
		
	  @Test
		public void test() throws Exception{					
				    try {
				    	String nm = naam[nummer];
				    	int an = Integer.parseInt(artikelNr[nummer]);
				    	int ma = Integer.parseInt(minimumAanwezig[nummer]);
				    	String eh = eenheid[nummer];
				    	double pps = Double.parseDouble(prijsPerStuk[nummer]);
				    	
				    	Product p = new Product(nm,an,ma,eh,pps);
				    	GebruiktProduct gp = new GebruiktProduct(an, p);
				    	BesteldProduct bp = new BesteldProduct(p, an);
				    	Bestelling b = new Bestelling(an);
				    	b.setBestelNummer(an);
				    	Product hetProduct = p;
				    	besteldeproducten.add(bp);
				    	b.setBesteldeProducten(besteldeproducten);
				    	assertEquals(b.getIsGeleverd(),false);
				    	p.setAantal(an);
				    	assertEquals(p.toString(),"Naam: " + nm + "; Artikelnummer: " + an + "; Minimum aanwezig: " + ma+ "; Eenheid: " + eh + "; Voorraad: " + an);
				    	assertEquals(gp.getKosten(),an*pps,DELTA);
				    	assertEquals(gp.getHetProduct(),p);
				    	p.voegAantalToe(5);
				    	assertEquals(p.getAantal(),an+5);
				    	p.setNaam("naam");
				    	assertEquals(p.getNaam(),"naam");
				    	assertEquals(bp.toString(),hetProduct.getNaam() + "; " + bp.getHoeveelheid() + " " + hetProduct.getEenheid());
				    	bp.setID(an);
				    	assertEquals(bp.getHoeveelheid(),an);
				    	assertEquals(bp.getProduct(),p);
				    	assertEquals(an,bp.getID());
				    	assertEquals(an,b.getBestelNummer());
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
