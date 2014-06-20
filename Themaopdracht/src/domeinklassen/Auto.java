package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Auto {

	private int autoid;
	private String kenteken, merk, type;
	private ArrayList<Klus> deKlussen = new ArrayList<Klus>();
	private Klant deEigenaar;
	private boolean actief = true;
	/**
	 * Constructor voor auto
	 * @param ken
	 * @param mk
	 * @param tp
	 * @param dE
	 */
	public Auto(String ken, String mk, String tp, Klant dE){
		kenteken = ken;
		merk = mk;
		type = tp;
		deEigenaar = dE;
		deEigenaar.voegAutoToe(this);
	}

	public boolean isActief() {
		return actief;
	}
	public void setActief(boolean actief) {
		this.actief = actief;
	}
	public void setID(int id){
		autoid = id;
	}
	
	public int getID(){
		return autoid;
	}

	public String getKenteken(){
		return kenteken;
	}
	
	public String getMerk(){
		return merk;
	}
	
	public String getType(){
		return type;
	}

	public void voegKlusToe(Klus k){
		deKlussen.add(k);
	}
	public Klant getEigenaar(){
		return deEigenaar;
	}

	public Date laatsteKlus() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date vandaag = new Date();
		Date d = df.parse("01-01-1950");
		for(Klus k : deKlussen){
			Date l = k.getDatum();
			if(l.after(d) && l.before(vandaag)){
				d = l;
			}
		}
		return d;
	}

	public ArrayList<Klus> getKlussen(){
		return deKlussen;
	}

	@Override
	public String toString() {
		return "Auto [autoid=" + autoid + ", kenteken=" + kenteken + ", merk="
				+ merk + ", type=" + type + ", deEigenaar=" + deEigenaar
				+ ", actief=" + actief + "]";
	}
	
}