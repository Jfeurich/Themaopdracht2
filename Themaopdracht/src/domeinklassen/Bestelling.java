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
	*	Voor het formatten en parsen van de datum
	**/
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	*	Variabele datum
	*	Zodat een bestelling een datum heeft
	**/
	private Date datum;
	
	/**
	*	Variabele deBesteldeProducten
	*	Zodat je Product objecten in een bestelling kan opslaan in een ArrayList
	**/
	private ArrayList<BesteldProduct> deBesteldeProducten;
	
	/**
	 * Constructor Bestelling
	 * Constructor met als parameter een ID.
	 * @param bN	is het ID
	 */
	public Bestelling(int bN){
		id = bN;
		isGeleverd = false;
		datum = null;
	}
	
	/**
	 * Constructor Bestelling
	 * Lege constructor voor bestelling
	 */
	public Bestelling(){
		isGeleverd = false;
		datum = null;
	}
	
	/**
	*	Methode getBestelNummer
	*	Haalt het bestelnummer op, op basis van ID van de bestelling
	*	@return int
	**/	
	public int getBestelNummer(){
		return id;
	}
	
	/**
	*	Methode setBestelNummer
	*	Met deze methode kan je een bestelling een BestelNummer geven
	*	@param nr	int een getal
	*	@return void
	**/	
	public void setBestelNummer(int nr){
		id = nr;
	}
	
	/**
	*	Methode getTotaal
	*	Berekend en geeft de totale kosten van de bestelling
	*	@return int
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
	*	@return ArrayList<BesteldProduct>
	**/	
	public ArrayList<BesteldProduct> getBesteldeProducten(){
		return deBesteldeProducten;
	}
	
	/**
	*	Methode setBesteldeProducten
	*	Zet de producten van de bestelling om naar de gegeven ArrayList
	*	@param bp	ArrayList<BesteldProduct>
	*	@return void
	**/	
	public void setBesteldeProducten(ArrayList<BesteldProduct> bp){
		deBesteldeProducten = bp;
	}
	
	/**
	*	Methode setIsGeleverd
	*	Met deze methode kan je een bestelling zeggen dat hij is geleverd
	*	@param boolean
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
	*	@return boolean
	**/	
	public boolean getIsGeleverd(){
		return isGeleverd;
	}
	
	/**
	*	Methode setVerwacht
	*	Met deze methode kan je een verwachte datum meegeven aan een bestelling
	*	@param da	Date verwachte datum
	*	@return void
	**/	
	public void setVerwachteDatum(Date da) throws Exception{
		datum = da;
	}
	
	/**
	*	Methode getVerwachteDatum
	*	Met deze methode kan je een verwachte datum opvragen
	*	@return Date
	**/	
	public Date getVerwachteDatum(){
		return datum;
	}
	
	/**
	*	Methode datum
	*	Parst de datum naar een SimpleDateFormat en geeft hem als String
	*	@return String
	**/	
	public String datum(){
		return df.format(datum);
	}
	
	/**
	*	Methode toString
	*	Geeft alle informatie van een bestelling 
	*	@return String
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