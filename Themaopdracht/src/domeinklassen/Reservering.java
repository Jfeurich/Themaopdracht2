package domeinklassen;


import java.text.SimpleDateFormat;
import java.util.Date;
/**	
*	Dit is klasse Reservering
*	Deze klasse zorgt ervoor dat Klanten Parkeerplaatsten kunnen reserveren.
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class Reservering {
	/**
	*	De ID van de reservering in de database 
	**/
	private int id;
	/**
	*	De begindatum van de reservering
	**/
	private Date beginDat;
	/**
	*	De einddatum van de reservering
	**/
	private Date eindDat;
	/**
	*	De dateformat formatteert het Datum object naar een gewenst formaat
	**/
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	/**
	*	Dit is de ID van de parkeerplek
	**/
	private int deParkeerplek;
	/**
	*	Dit is de Auto die op de parkeerplek komt te staan
	**/
	private Auto deAuto;
	/**
	*	Dit beschrijft of de Auto aanwezig was tijdens de reservering
	**/
	private boolean isGeweest;
	/**
	*	Constructor voor Reservering.
	*	@param Auto deAuto 	Dit is de Auto gekoppeld aan de reservering
	*	@param int id 		Dit is het ID van de reservering voor de database
	*	@param Date beginDat Dit is de begindatum van de reservering
	*	@param Date eindDat Dit is de einddatum van de reservering
	*	@param int deParkeerplek Dit is het ID van de parkeerplek
	*	@param boolean isGeweest Dit beschrijft of de Auto aanwezig was op de parkeerplek
	**/	
	public Reservering(Auto a, int i, Date bD, Date eD, int dP){
		deAuto = a;
		id = i;
		beginDat = bD;
		eindDat = eD;
		deParkeerplek = dP;
		isGeweest = false;
	}
	/**
	*	Methode isGeweest() returnt of de Auto aanwezig was tijdens de reservering
	*	@return boolean isGeweest
	**/
	public boolean isGeweest() {
		return isGeweest;
	}
	/**
	*	Methode setGeweest verandert de waarde van isGeweest naar true of false
	*	@param boolean isGeweest
	**/
	public void setGeweest(boolean isGeweest) {
		this.isGeweest = isGeweest;
	}
	/**
	*	Methode voegAutoToe voegt een auto toe aan een reservering
	*	@param Auto a
	**/
	public void voegAutoToe(Auto a){
		deAuto = a;
	}
	/**
	*	Methode getID() returnt het ID van de reservering
	*	@return int id
	**/
	public int getID(){
		return id;
	}
	/**
	*	Methode getBegDat() returnt de begindatum van de reservering
	*	@return Date beginDat
	**/
	public Date getBegDat(){
		return beginDat;
	}
	/**
	*	Methode getEindDat() returnt de einddatum van de reservering
	*	@return Date eindDat
	**/
	public Date getEindDat(){
		return eindDat;
	}
	/**
	*	Methode getBegDatNetjes() returnt een geformatteerde versie van begindatum
	*	@return String beginDat
	**/
	public String getBegDatNetjes(){
		return df.format(beginDat);
	}
	/**
	*	Methode getEindDatNetjes() returnt een geformatteerde verdie van einddatum
	*	@return String eindDat
	**/
	public String getEindDatNetjes(){
		return df.format(eindDat);
	}
	/**
	*	Methode getAuto() returnt het Auto-Object van een reservering
	*	@return Auto deAuto
	**/
	public Auto getAuto(){
		return deAuto;
	}
	/**
	*	Methode getDeParkeerplek() returnt het id van de parkeerplek
	*	@return int deParkeerplek
	**/
	public int getDeParkeerplek(){
		return deParkeerplek;
	}
	/**
	*	Methode toString() returnt een geformatteerde string van een reservering
	*	@return String s
	**/
	public String toString(){
		return "Van " + df.format(beginDat) + " tot " + df.format(eindDat) + " op parkeerplek " + deParkeerplek + " staat auto met kenteken " + deAuto.getKenteken();
	}
}
