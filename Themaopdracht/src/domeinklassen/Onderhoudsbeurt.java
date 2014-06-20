package domeinklassen;

import java.util.Date;
/**	
*	Dit is klasse Onderhoudsbeurt. Dit is een dochterklasse van klus
*	Deze klasse heeft een andere berekenKosten() methode en een andere toString()
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class Onderhoudsbeurt extends Klus {
	/**
	*	Constructor voor Onderhoudsbeut
	*	@param Date dat is de datum van de onderhoudsbeurt
	*	@param String b is de beschrijving van de onderhoudsbeurt
	**/	
	public Onderhoudsbeurt(Date dat, String b){
		super(dat, b);
	}
	/**
	*	Constructor voor Onderhoudsbeurt
	*	@param Date dat is de datum van de onderhoudsbeurt
	*	@param String b is de beschrijving van de onderhoudsbeurt
	*	@param Auto dA is de Auto gekoppeld aan de onderhoudsbeurt
	**/	
	public Onderhoudsbeurt(Date dat, String b, Auto dA){
		super(dat, b, dA);
	}
	/**
	*	Deze methode berekent de kosten van een onderhoudsbeurt
	*	@return double kosten
	**/
	public double berekenKosten(){
		double kosten = manuren * 9.50;
		return kosten;
	}
	/**
	*	Deze methode returnt een geformatteerde String van een onderhoudsbeurt
	*	@return String s
	**/	
	public String toString(){
		String s = super.toString() + "Onderhoudsbeurt: " + datum + "; ";
		return s;
	}
}