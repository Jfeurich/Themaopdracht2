package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.Date;

/**	
*	Dit is klasse Herinneringsbrief
*	Deze klasse is verantwoordelijk maken van brieven voor Klanten
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class Herinneringsbrief{
	
	/**
	*   Variabele id, type int
	*	Dit is het id van de brief (uit de database).
	**/
	private int id;
	/**
	*   Variabele deKlant, type Klant
	*	Dit is de Klant aan wie de brief is gestuurd.
	**/
	private Klant deKlant;
	/**
	*   Variabele reden, type String
	*	Dit is de inhoud van de brief.
	**/
	private String reden;
	/**
	*   Variabele datum, type Date
	*	De datum waarop de brief aan is gemaakt.
	**/
	private Date datum;
	/**
	*   Variabele df, type SimpleDateFormat
	*	Het dateformat dat wordt gebruikt om de datum de parsen en te formatten.
	**/
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	/**
	*	Constructor Herinneringsbrief
	*	Dit is de constructor voor Herinneringsbrief
	*	@param i	int Het id van de brief.
	*	@param k	Klant De Klant waar de brief voor wordt gemaakt.
	*	@param r	String	De inhoud van de brief.
	*	@param dat	Date De datum waarop de brief wordt gemaakt.
	**/
	public Herinneringsbrief(int i, Klant k, String r, Date dat){
		id = i;
		deKlant = k;
		reden = r;
		datum = dat;
		k.voegBriefToe(this);
	}
	
	/**
	*	Methode getDatum
	*	Geeft de datum van de brief.
	*	@return Date
	**/	
	public Date getDatum(){
		return datum;
	}
	
	/**
	*	Methode getId
	*	Geeft get id van de brief.
	*	@return int
	**/	
	public int getId() {
		return id;
	}

	/**
	*	Methode getDeKlant
	*	Geeft de Klant aan wie de brief is geschreven.
	*	@return Klant
	**/	
	public Klant getDeKlant() {
		return deKlant;
	}

	/**
	*	Methode getReden
	*	Geeft de inhoud van de brief.
	*	@return String
	**/	
	public String getReden() {
		return reden;
	}

	/**
	*	Methode getDf
	*	Geeft het dateformat zodat de datum geparsed en geformat kan worden.
	*	@return SimpleDateFormat
	**/	
	public SimpleDateFormat getDf() {
		return df;
	}
	
}