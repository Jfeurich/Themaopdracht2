package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Bestelling {
	private int bestelNummer;
	private boolean isGeleverd;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private Date datum;
	private ArrayList<BesteldProduct> deBesteldeProducten;
	
	public Bestelling(int bN){
		bestelNummer = bN;
		isGeleverd = false;
		datum = null;
	}

	public Bestelling(){
		isGeleverd = false;
		datum = null;
	}
	public Bestelling(int bN, ArrayList<BesteldProduct> deBP) {
		bestelNummer = bN;
		deBesteldeProducten = deBP;
		isGeleverd = false;
		datum = null;
	}
	public int getBestelNummer(){
		return bestelNummer;
	}
	public void setBestelNummer(int nr){
		bestelNummer = nr;
	}
	public int getTotaal(){
		int totaal = 0;
		for(BesteldProduct bp: deBesteldeProducten){
			totaal += bp.getHoeveelheid();
		}
		return totaal;
	}
	public ArrayList<BesteldProduct> getBesteldeProducten(){
		return deBesteldeProducten;
	}
	public void setBesteldeProducten(ArrayList<BesteldProduct> bp){
		deBesteldeProducten = bp;
	}
	public void setIsGeleverd(boolean b){
		isGeleverd = b;
		if(b){
			for(BesteldProduct bp : deBesteldeProducten){
				Product p = bp.getProduct();
				p.setAantal(p.getAantal() + bp.getHoeveelheid());
			}
		}
	}
	public boolean getIsGeleverd(){
		return isGeleverd;
	}
	public void setVerwachteDatum(String da) throws Exception{
		datum = df.parse(da);
	}	
	public void setVerwachteDatum(Date da) throws Exception{
		datum = da;
	}
	public Date getVerwachteDatum(){
		return datum;
	}
	public String datum(){
		return df.format(datum);
	}
	public String toString(){
		String s = "Bestelnummer: " + bestelNummer;
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