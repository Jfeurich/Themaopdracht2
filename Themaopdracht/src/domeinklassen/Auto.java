package domeinklassen;

import java.util.ArrayList;
import java.util.Date;

public class Auto {

	private String kenteken, merk, type;
	private ArrayList<Klus> deKlussen = new ArrayList<Klus>();
	private Klant deEigenaar;
	private Date datum = new Date();

	public Auto(String ken, String mk, String tp, Klant dE){
		kenteken = ken;
		merk = mk;
		type = tp;
		deEigenaar = dE;
		deEigenaar.voegAutoToe(this);
	}

	public String getKenteken(){
		return kenteken;
	}

	public void voegKlusToe(Klus k){
		deKlussen.add(k);
	}
	public Klant getEigenaar(){
		return deEigenaar;
	}

	public Date laatsteKlus(){
		Date d = datum;
		for(Klus k : deKlussen){
			Date check = k.getDatum();
			if(check.before(d)){
				d = check;
			}
		}
		return d;
	}

	public ArrayList<Klus> getKlussen(){
		return deKlussen;
	}

	public String toString(){
		String s = "Auto met kenteken " + kenteken + " is een " + type + " van " + merk;
		return s;
	}
}