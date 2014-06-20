package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**	
*	Dit is de abstracte klasse klus. Deze klasse implementeert functionaliteit
*	voor zowel reparatie als onderhoudsbeurt
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public abstract class Klus {
	/**
	*	De ID van de Klus
	**/
	public int id;
	/**
	*	De datum van de Klus
	**/
	protected Date datum;
	/**
	*	De beschrijving van de Klus
	**/
	protected String beschrijving;
	/**
	*	Het aantal manuren van de Klus
	**/
	protected int manuren = 0;
	/**
	*	De status van de Klus
	**/
	protected String status = "Nog niet begonnen";
	/**
	*	Het Factuur-Object dat gekoppeld kan zijn aan de Klus
	**/
	protected Factuur deFactuur;
	/**
	*	Het Auto-Object dat gekoppeld is aan de Klus
	**/
	protected Auto deAuto;
	/**
	*	De Arraylist met gebruikte onderdelen en manuren voor de klus
	**/
	protected ArrayList<GebruiktProduct> gebruikteProducten = new ArrayList<GebruiktProduct>();
	/**
	*	De Datum formatter voor Klus
	**/
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	/**
	*	Constructor voor Klus
	*	@param Date datum	datum van de klus
	*	@param String beschrijving	beschrijving van de klus
	*	@param int manuren	manuren besteed aan de klus
	*	@param Auto deAuto	Auto gekoppeld aan de klus
	*	@param Factuur deFactuur	Factuur gekoppeld aan de klus
	**/	
	public Klus(Date dat, String b){
		datum = dat;
		beschrijving = b;
		manuren = 0;
		deAuto = null;
		deFactuur = null;	
	}
	/**
	*	Constructor voor Klus
	*	@param Date datum	datum van de klus
	*	@param String beschrijving	beschrijving van de klus
	*	@param int manuren	manuren besteed aan de klus
	*	@param Auto deAuto	Auto gekoppeld aan de klus
	*	@param Factuur deFactuur	Factuur gekoppeld aan de klus
	**/	
	public Klus(Date dat, String b, Auto dA){
		datum = dat;
		beschrijving = b;
		manuren = 0;
		deAuto = dA;
		dA.voegKlusToe(this);
		deFactuur = null;
	}
	
	public void setDeAuto(Auto a){
		deAuto = a;
		a.voegKlusToe(this);
	}
	/**
	*	Deze methode returnt het ID van een Klus
	*	@return int id
	**/
	public int getID(){
		return id;
	}
	/**
	*	Deze methode returnt de Auto van een Klus
	*	@return Auto deAuto
	**/
	public Auto getAuto(){
		return deAuto;
	}
	/**
	*	Deze methode returnt de status van een Klus
	*	@return String status
	**/
	public String getStatus(){
		return status;
	}
	/**
	*	Deze methode returnt de datum van een Klus
	*	@return Date datum
	**/
	public Date getDatum(){
		return datum;
	}
	/**
	*	Deze methode returnt de beschrijving van een Klus
	*	@return String beschrijving
	**/
	public String getBeschrijving(){
		return beschrijving;
	}
	/**
	*	Deze methode verandert de status van een Klus
	*	@param String status
	**/	
	public void setStatus(String st){
		status = st;
	}
	/**
	*	Deze methode verandert de beschrijving van een Klus
	*	@param String beschrijving
	**/	
	public void setBeschrijving(String b){
		beschrijving = b;
	}
	/**
	*	Deze methode verandert de datum van een Klus
	*	@param Date datum
	**/
	public void setDatum(Date d){
		datum = d;
	}
	/**
	*	Deze methode verandert het ID van een Klus
	*	@param int id 
	**/
	public void setID(int i){
		id = i;
	}
	/**
	*	Deze methode returnt de manuren van een Klus
	*	@return int manuren
	**/
	public  int getManuren(){
		return manuren;
	}
	/**
	*	Deze methode voegt manuren toe aan een Klus
	*	@param int manuren
	**/
	public void addManuren(int mu){
		manuren += mu;
	}
	/**
	*	Deze methode returnt de gebruikte producten van een Klus
	*	@return ArrayList<GebruiktProduct> gebruikteproducten
	**/
	public ArrayList<GebruiktProduct> getGebruikteProducten(){
		return gebruikteProducten;
	}
	/**
	*	Deze methode verandert de ArrayList<GebruiktProduct> van een Klus
	*	@param ArrayList<GebruiktProduct> array
	**/
	public void setGebruikteProducten(ArrayList<GebruiktProduct> array){
		gebruikteProducten = array;
	}
	/**
	*	Deze methode voegt een gebruikt product toe aan de ArrayList<GebruiktProduct> van een Klus
	*	@param GebruiktProduct g
	**/
	public void addGebruiktProduct(GebruiktProduct g){
		gebruikteProducten.add(g);
	}

	public abstract double berekenKosten();

	public void setDeFactuur(Factuur f){
		deFactuur = f;
	}
	/**
	*	Deze methode returnt de geformatteerde datum van een Klus
	*	@return String datum
	**/
	public String getFormattedDatum(){
		return df.format(datum);
	}
	/**
	*	Deze methode returnt de Factuur van een Klus
	*	@return Factuur deFactuur
	**/
	public Factuur getFactuur(){
		return deFactuur;
	}
	/**
	*	Deze methode returnt de geformatteerde String van een Klus
	*	@return String s
	**/
	public String toString(){
		return "Bij deze klus is gewerkt aan: " + deAuto.getKenteken();
	}
}