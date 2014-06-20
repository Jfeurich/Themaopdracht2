package domeinklassen;

import java.util.Date;
/**	
*	Dit is klasse Reparatie. Dit is een dochterklasse van klus
*	Deze klasse heeft een andere berekenKosten() methode en een andere toString()
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class Reparatie extends Klus {
	/**
	*	Constructor voor Reparatie
	*	@param Date dat is de datum van de reparatie
	*	@param String b is de beschrijving van de reparatie
	**/	
	public Reparatie(Date dat, String b){
		super(dat, b);
	}
	/**
	*	Constructor voor Reparatie
	*	@param Date dat is de datum van de reparatie
	*	@param String b is de beschrijving van de reparatie
	*	@param Auto dA is de Auto gekoppeld aan de reparatie
	**/	
	public Reparatie(Date dat, String b, Auto dA){
		super(dat, b, dA);
	}
	/**
	*	Deze methode berekent de kosten van een reparatie
	*	@return double kosten
	**/	
	public double berekenKosten(){
		double kosten = manuren * 8.50;
		return kosten;
	}
	/**
	*	Deze methode returnt een geformatteerde String van een reparatie
	*	@return String s
	**/	
	public String toString(){
		String s = super.toString() + "Reparatie: " + datum + "; ";
		return s;
	}
}