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
	private static int[] artikelNr = new int[21];
	private static int[] minimumAanwezig = new int[21];
	private static String[] eenheid = new String[21];
	private static int[] aantal = new int[21];
	private static double[] prijsPerStuk = new double[21];
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
		  String csvPersonen = "1;Rolland;Klinkhamer 41;Nieuwkoop;12345678;61234567>2;Steve;GraafFloris;Gouda;IBAN1234567;18212345>3;Julie;1 Nijenoord;Utrecht;98654321;69182736>4;Vonda;Bleuland;Utrecht;45665432;61211167>5;Rolland;Klinkhamer 41;Nieuwkoop;12345678;61234567>zes;dave;lalala 1234;LOL;12712312;123456>7;Sjon;sjonniestraat 1;In The CITY;23459432;!@#$%^&*()_+>8;Henk;blabla 45;kuxh;94279483;987578>9;Jan;Hehehe 456;Stadjuh;asd;3456543>10;Timmy;<(-_-)>;O_o;(-_-);asd>11;;Gaar;Weird;cool12345;987654>12;Martin;Leermos 90;Rotterdam;12356743;nullzeseentwee>13;Rene;&&;Amsterdam;IBAN 7654321;69876543>";
			String cvsSplitBy = ";";	
			BufferedReader brp = null;
			String linep = ">";
			try {
				brp = new BufferedReader(new FileReader(csvPersonen));
				while ((linep = brp.readLine()) != null) {
					String[] regel = linep.split(cvsSplitBy); //file is gescheiden door ;
					naam[nummer] = "";
				    artikelNr[nummer] = 0;
				    minimumAanwezig[nummer]= 0;
				    eenheid[nummer] = "";
				    aantal[nummer] = 0;
				    prijsPerStuk[nummer] = 0.0;
					try{
					    naam[nummer] = regel[0];
					    artikelNr[nummer] = Integer.parseInt(regel[1]);
					    minimumAanwezig[nummer]= Integer.parseInt(regel[2]);
					    eenheid[nummer] = regel[3];
					    aantal[nummer] = Integer.parseInt(regel[4]);
					    prijsPerStuk[nummer] = Double.parseDouble(regel[5]);
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
	  
		
	  @SuppressWarnings("deprecation")
	  @Test
		public void test() throws Exception{					
				    try {
				    	String nm = naam[nummer];
				    	int an = artikelNr[nummer];
				    	int ma = minimumAanwezig[nummer];
				    	String eh = eenheid[nummer];
				    	double pps = prijsPerStuk[nummer];
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
				    	p.setNaam("nm");
				    	assertEquals(p.getNaam(),nm);
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
