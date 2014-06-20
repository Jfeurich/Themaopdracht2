package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**	
*	Dit is klasse Bestelling
*	Deze klasse geeft informatie over een bestelling
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class Bestelling {
	
	/**
	*	Variabele id
	*	Zodat een bestelling een id kan hebben
	**/
	private int id;
	
	/**
	*	Variabele isGeleverd
	*	Zodat je weet of een bestelling is geleverd of nog niet
	**/
	private boolean isGeleverd;
	
	/**
	*	Variabele df
	*	Zodat een bestelling een mooie datum heeft
	**/
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	*	Variabele datum
	*	Zodat een bestelling een datum heeft
	**/
	private Date datum;
	
	/**
	*	Variabele deBesteldeProducten
	*	Zodat je Product objecten in een bestelling kan opslaan
	**/
	private ArrayList<BesteldProduct> deBesteldeProducten;
	
	/**
	 * Constructor1 voor Bestelling
	 * @param bN	is het ID
	 */
	public Bestelling(int bN){
		id = bN;
		isGeleverd = false;
		datum = null;
	}
	
	/**
	 * Constructor2 voor Bestelling
	 */
	public Bestelling(){
		isGeleverd = false;
		datum = null;
	}
	
	/**
	*	Methode getBestelNummer
	*	Haalt het bestelnummer op, op basis van ID van de bestelling
	*	@return id van type int
	**/	
	public int getBestelNummer(){
		return id;
	}
	
	/**
	*	Methode setBestelNummer
	*	Met deze methode kan je een bestelling een BestelNummer geven
	*	@param nr	int een getal
	*	@return id van type int
	**/	
	public void setBestelNummer(int nr){
		id = nr;
	}
	
	/**
	*	Methode getTotaal
	*	Haalt het totaal op van de bestelling
	*	@return totaal van type int
	**/	
	public int getTotaal(){
		int totaal = 0;
		for(BesteldProduct bp: deBesteldeProducten){
			totaal += bp.getHoeveelheid();
		}
		return totaal;
	}
	
	/**
	*	Methode getBesteldeProducten
	*	Haalt alle Producten op van een bestelling
	*	@return deBesteldeProducten van type Array
	**/	
	public ArrayList<BesteldProduct> getBesteldeProducten(){
		return deBesteldeProducten;
	}
	
	/**
	*	Methode setBesteldeProducten
	*	Zodat je producten kan toevoegen aan een bestelling
	*	@param bp	ArrayList
	*	@return deBesteldeProducten van type Array
	**/	
	public void setBesteldeProducten(ArrayList<BesteldProduct> bp){
		deBesteldeProducten = bp;
	}
	
	/**
	*	Methode setIsGeleverd
	*	Met deze methode kan je een bestelling zeggen dat hij is geleverd
	*	@param b	boolean
	**/	
	public void setIsGeleverd(boolean b){
		isGeleverd = b;
		if(b){
			for(BesteldProduct bp : deBesteldeProducten){
				Product p = bp.getProduct();
				p.setAantal(p.getAantal() + bp.getHoeveelheid());
			}
		}
	}
	
	/**
	*	Methode getIsGeleverd
	*	Haalt op of een Bestelling is geleverd
	*	@return isGeleverd van type boolean
	**/	
	public boolean getIsGeleverd(){
		return isGeleverd;
	}
	
	/**
	*	Methode setVerwacht
	*	Met deze methode kan je een verwachte datum meegeven aan een bestelling
	*	@param da	Date verwachte datum
	*	@return datum van type Date
	**/	
	public void setVerwachteDatum(Date da) throws Exception{
		datum = da;
	}
	
	/**
	*	Methode getVerwachteDatum
	*	Met deze methode kan je een verwachte datum opvragen
	*	@return datum van type Date
	**/	
	public Date getVerwachteDatum(){
		return datum;
	}
	
	/**
	*	Methode datum
	*	Parst een datum naar een SimpleDateFormat
	*	@return df.format(datum)
	**/	
	public String datum(){
		return df.format(datum);
	}
	
	/**
	*	Methode toString
	*	Geeft alle informatie van een bestelling 
	*	@return s van type String
	**/	
	public String toString(){
		String s = "Bestelnummer: " + id;
		if(isGeleverd == true){
			s += "; is geleverd";
		}
		else{
			s += "; is niet geleverd";
		}
		if(datum == null){
			s += "; nog geen verwachte leverdatum bekend";
		}
		else{
			s += "; verwachte leverdatum: " + df.format(datum);
		}
		return s;
	}
}