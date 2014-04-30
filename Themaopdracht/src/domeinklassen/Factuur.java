package domeinklassen;

import java.util.Date;
import java.text.*;

public class Factuur {
	private int factuurid;
	private Date aanmaakDatum;
	private Date betaaldDatum;
	private String betalingswijze;
	private int kortingsPercentage;
	private boolean isBetaald;
	private Klus deKlus;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	public Factuur(Date aD, Klus dK){
		aanmaakDatum = aD;
		kortingsPercentage = 0;
		isBetaald = false;
		deKlus = dK;
		deKlus.setDeFactuur(this);
	}
	public void betaal(String bw, Date bD){
		isBetaald = true;
		betalingswijze = bw;
		betaaldDatum = bD;
	}
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
	
	public int getID(){
		return factuurid;
	}
	
	public void setID(int id){
		factuurid = id;
	}
	
	public Date getAanmaakDatum(){
		return aanmaakDatum;
	}
	
	public Date getBetaalDatum(){
		return betaaldDatum;
	}
	
	public int getKorting(){
		return kortingsPercentage;
	}

	public void setKortingsPercentage(int kP){
		kortingsPercentage = kP;
	}
	public Klus getDeKlus(){
		return deKlus;
	}
	
	public String getBetaalwijze(){
		return betalingswijze;
	}
	public double getTotaal(){
		double totaal = 0.0;
		for(GebruiktProduct gp: deKlus.getGebruikteProducten()){
			totaal += gp.getKosten();
		}
		totaal += deKlus.berekenKosten();
		totaal = totaal - (totaal * (kortingsPercentage*0.01));
		return totaal;
	}
	public double getBTW(double btw){
		return getTotaal() * (btw * 0.01);
	}
	public boolean getIsBetaald(){
		return isBetaald;
	}
}
