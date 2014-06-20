package domeinklassen;

import java.text.DecimalFormat;
/**	
*	Dit is klasse GebruiktProduct.
*	Deze klasse haalt alle database informatie op in relatie met het object Auto.
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class GebruiktProduct {
	/**
	 * Variabele id, type int.
	 * Deze variabele geeft het ID van het gebruikte product aan.
	 */
	private int id;
	/**
	 * Variabele aantal, type int.
	 * Deze variabele geeft het aantal van het bestelde product aan.
	 */
	private int aantal;
	/**
	 * Variabele hetProduct, type Product.
	 * Deze variabele geeft aan om wel product het gaat.
	 */
	private Product hetProduct;
	/**
	 * Variabele df, type DecimalFormat.
	 * Deze variabele wordt gebruikt om Strings om te zetten naar Data-objects en andersom.
	 */
	private DecimalFormat df = new DecimalFormat("#.##");
	
	/**
	 * Constructor GebruiktProduct.
	 * Dit is de constructor om een nieuwe GebruiktProduct-object te maken.
	 * @param a	Aantal van het gebruikte product
	 * @param hP	Het product dat aangeeft welk product het is
	 */
	public GebruiktProduct(int a, Product hP){
		aantal = a;
		hetProduct = hP;
	}
	
	/**
	 * Constructor GebruiktProduct.
	 * Dit is de constructor om een nieuwe GebruiktProduct-object te maken.
	 * @param a	Aantal van het gebruikte product
	 * @param i	Het ID van het gebruikte product
	 */
	public GebruiktProduct(int i, int a){
		id = i;
		aantal = a;
		hetProduct = null;
	}
	
	/**
	 * Methode getID.
	 * Deze methode geeft het ID van het gebruikte product terug.
	 * @return	id	ID van het gebruikte product
	 */
	public int getID(){
		return id;
	}
	
	/**
	 * Methode getAantal.
	 * Deze methode geeft het aantl van het gebruikte product terug.
	 * @return aantal	Aantal van het gebruikte product
	 */
	public int getAantal(){
		return aantal;
	}

	/**
	 * Methode setHetProduct.
	 * Deze methode set welk product dit BesteldProduct-object is.
	 * @param p	Het product dat dit besteld product is
	 */
	public void setHetProduct(Product p){
		hetProduct = p;
	}

	/**
	 * Methode getHetProduct.
	 * Deze methode geeft het aan welk Product dit gebruikt product is.
	 * @return hetProduct	Het Product-object van het gebruikte product
	 */
	public Product getHetProduct(){
		return hetProduct;
	}
	
	/**
	 * Methode getKosten.
	 * Deze methode geeft aan wat de kosten zijn van dit gebruikte product.
	 * Dit wordt berekend door het aantal maal de prijs per stuk van het product te doen.
	 * @return int	De totaalkosten van dit bestelde product.
	 */
	public double getKosten(){
		return aantal * hetProduct.getPrijsPerStuk();
	}
	
	/**
	 * Methode toString.
	 * Deze methode geeft informatie terug over dit bestelde product.
	 * @param btw 	Het % btw dat over deze regel geldt
	 * @return	String	Er wordt een String met informatie over dit bestelde product teruggegeven
	 */
	public String toString(double btw){
		double totaalPrijs = hetProduct.getPrijsPerStuk() * aantal;
		double totaalPrijsBTW = totaalPrijs * (1.0 + (btw * 0.01));
		return hetProduct.getNaam() + " Aantal " + aantal + " € " + hetProduct.getPrijsPerStuk() + " Totaalbedrag €" + df.format(totaalPrijs) + "; Totaalbedrag inclusief BTW: €" + df.format(totaalPrijsBTW);
	}
}
