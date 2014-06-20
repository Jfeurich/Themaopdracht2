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
	*	Voor het formatten en parsen van de datum
	**/
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	*	Constructor Factuur
	*	De constructor voor een nieuwe Factuur
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
	*	Betaalt een factuur met de gekozen betalingswijze op de gegeven datum.
	*	@param bw	String de betalingswijze
	*	@param bD	Date de betaalDatum
	*	@return	void
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
	*	@return String
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
	*	@return int
	**/
	public int getID(){
		return factuurid;
	}
	
	/**
	*	Methode setID
	*	Zodat je een factuur een uniek nummer kan geven
	*	@param id	int een getal
	*	@return void
	**/
	public void setID(int id){
		factuurid = id;
	}
	
	/**
	*	Methode getAanmaakDatum
	*	Haalt de datum op van wanneer de factuuur is gemaakt
	*	@return Date
	**/
	public Date getAanmaakDatum(){
		return aanmaakDatum;
	}
	
	/**
	*	Methode getBetaalDatum
	*	Haalt de betaal datum op van de factuur
	*	@return Date
	**/
	public Date getBetaalDatum(){
		return betaaldDatum;
	}
	
	/**
	*	Methode getKorting
	*	Haalt de gegeven korting op van een factuur
	*	@return int
	**/
	public int getKorting(){
		return kortingsPercentage;
	}
	
	/**
	*	Methode setKortingsPercentage
	*	Zodat je korting kan geven.
	*	@param kP	int Het gewenste percentage als heel nummer.
	*	@return void
	**/
	public void setKortingsPercentage(int kP){
		kortingsPercentage = kP;
	}
	
	/**
	*	Methode getKlus
	*	Haalt de klus op van de factuur
	*	@return Klus
	**/
	public Klus getDeKlus(){
		return deKlus;
	}
	
	/**
	*	Methode getBetaalwijze
	*	Haalt de betaalwijze op hoe de factuur betaald is
	*	@return String
	**/
	public String getBetaalwijze(){
		return betalingswijze;
	}
	
	/**
	*	Methode getTotaal
	*	Berekent en geeft het totaalbedrag van de factuur
	*	@return double 
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
	*	Berekent en geeft de BTW van de factuur
	*	@return double
	**/
	public double getBTW(double btw){
		return getTotaal() * (btw * 0.01);
	}

	/**
	*	Methode getBetaalDatumNetjes
	*	Haalt de datum op van de factuur wanneer die is betaald als String.
	*	@return String
	**/
	public String getBetaalDatumNetjes(){
		return df.format(betaaldDatum);
	}

	/**
	*	Methode getAanmaakDatumNetjes
	*	Haalt de datum op wanneer de factuur is aangemaakt als String.
	*	@return String
	**/
	public String getAanmaakDatumNetjes(){
		return df.format(aanmaakDatum);
	}
	
	/**
	*	Methode getIsBetaald
	*	Haalt de status van een factuur op of die is betaald of niet
	*	@return boolean
	**/
	public boolean getIsBetaald(){
		return isBetaald;
	}
}
