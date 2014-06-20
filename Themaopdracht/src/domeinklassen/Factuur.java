package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.Date;

/**	
*	Dit is klasse Factuur
*	Deze klasse geeft informatie over een Factuur
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class Factuur {
	
	/**
	*	Variabele factuurid
	*	Zodat je een Factuur een uniek nummer kan geven
	**/
	private int factuurid;
	
	/**
	*	Variabele aanmaakDatum
	*	De aanmaak datum van een factuur
	**/
	private Date aanmaakDatum;
	
	/**
	*	Variabele betaalDatum
	*	De betaal datum van een factuur
	**/
	private Date betaaldDatum;
	
	/**
	*	Variabele betalingswijze
	*	Zodat een factuur op meerdere manieren betaald kan worden 
	**/
	private String betalingswijze;
	
	/**
	*	Variabele kortingsPercentage
	*	Zodat je korting kan geven
	**/
	private int kortingsPercentage;
	
	/**
	*	Variabele isBetaald
	*	Zodat je kan aangeven dat een factuur betaald is
	**/
	private boolean isBetaald;
	
	/**
	*	Variabele deKlus
	*	Zodat je een Klus object kan meegeven aan een Factuur
	**/
	private Klus deKlus;
	
	/**
	*	Variabele df
	*	Zodat de factuur een mooie datum heeft
	**/
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	*	Constructor Factuur
	*	@param aD	Date is een datum
	*	@param dK	Klus is een klus object
	**/
	public Factuur(Date aD, Klus dK){
		aanmaakDatum = aD;
		kortingsPercentage = 0;
		isBetaald = false;
		deKlus = dK;
		deKlus.setDeFactuur(this);
	}
	
	/**
	*	Methode betaal
	*	Zodat je een factuur kan betalen
	*	@param bw	String de betalingswijze
	*	@param bD	Date de betaalDatum
	**/
	public void betaal(String bw, Date bD){
		isBetaald = true;
		betalingswijze = bw;
		betaaldDatum = bD;
	}
	
	/**
	*	Methode toString
	*	Geeft alle informatie van een factuur 
	*	@param btw	double de btw
	*	@return s van type String
	**/
	public String toString(double btw){
		double totaal = 0.0;
		String s = "Aanmaakdatum: " + df.format(aanmaakDatum) + "\nIs betaald: ";
		if(isBetaald == true){
			s += "Ja" + "\nBetaalddatum: " + df.format(betaaldDatum) + "\nBetaalwijze: " + betalingswijze;
		}
		else{
			s += "Nee";
		}
		s += "\nKortingspercentage: " + kortingsPercentage + "%";
		for(GebruiktProduct gp: deKlus.getGebruikteProducten()){
			s += "\n" + gp.toString(btw);
			totaal += gp.getKosten();
		}
		s += "\nManuren: " + deKlus.getManuren() + " €" + deKlus.berekenKosten();
		totaal += deKlus.berekenKosten();
		totaal = totaal - (totaal * (kortingsPercentage*0.01));
		s += "\nTotaal: €" + totaal;
		return s;
	}
	
	/**
	*	Methode getID
	*	Haalt het ID op van de factuur
	*	@return factuurid van type int
	**/
	public int getID(){
		return factuurid;
	}
	
	/**
	*	Methode setID
	*	Zodat je een factuur een uniek nummer kan geven
	*	@param id	int een getal
	*	@return factuurid
	**/
	public void setID(int id){
		factuurid = id;
	}
	
	/**
	*	Methode getAanmaakDatum
	*	Haalt de datum op van wanneer de factuuur is gemaakt
	*	@return aanmaakDatum van type Date
	**/
	public Date getAanmaakDatum(){
		return aanmaakDatum;
	}
	
	/**
	*	Methode getBetaalDatum
	*	Haalt de betaal datum op van de factuur
	*	@return betaalDatum van type Date
	**/
	public Date getBetaalDatum(){
		return betaaldDatum;
	}
	
	/**
	*	Methode getKorting
	*	Haalt de gegeven korting op van een factuur
	*	@return kortingsPercentage van type int
	**/
	public int getKorting(){
		return kortingsPercentage;
	}
	
	/**
	*	Methode setKortingsPercentage
	*	Zodat je korting kan geven
	*	@param kp	int een getal
	*	@return kortingsPercentage van type int
	**/
	public void setKortingsPercentage(int kP){
		kortingsPercentage = kP;
	}
	
	/**
	*	Methode getKlus
	*	Haalt de klus op van de factuur
	*	@return deKlus van type Klus
	**/
	public Klus getDeKlus(){
		return deKlus;
	}
	
	/**
	*	Methode getBetaalwijze
	*	Haalt de betaalwijze op hoe de factuur betaald is
	*	@return betalingswijze type String
	**/
	public String getBetaalwijze(){
		return betalingswijze;
	}
	
	/**
	*	Methode getTotaal
	*	Haalt het totaalbedrag op van de factuur
	*	@return totaal type double 
	**/
	public double getTotaal(){
		double totaal = 0.0;
		for(GebruiktProduct gp: deKlus.getGebruikteProducten()){
			totaal += gp.getKosten();
		}
		totaal += deKlus.berekenKosten();
		totaal = totaal - (totaal * (kortingsPercentage*0.01));
		return totaal;
	}
	
	/**
	*	Methode getBTW
	*	Haalt het BTW op van de factuur
	*	@return getTotaal() * (btw * 0.01) van type double
	**/
	public double getBTW(double btw){
		return getTotaal() * (btw * 0.01);
	}

	/**
	*	Methode getBetaalDatumNetjes
	*	Haalt de datum op van de factuur wanneer die is betaald
	*	@return df.format(betaalDatum) van type String
	**/
	public String getBetaalDatumNetjes(){
		return df.format(betaaldDatum);
	}

	/**
	*	Methode getAanmaakDatumNetjes
	*	Haalt de datum op wanneer de factuur is aangemaakt
	*	@return df.format(aanmaakDatum) van type String
	**/
	public String getAanmaakDatumNetjes(){
		return df.format(aanmaakDatum);
	}
	
	/**
	*	Methode getIsBetaald
	*	Haalt de status van een factuur op of die is betaald of niet
	*	@return isBetaald van type boolean
	**/
	public boolean getIsBetaald(){
		return isBetaald;
	}
}
