package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Herinneringsbrief{

	private int id;
	private Klant deKlant;
	private String reden;
	private Date datum;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	public Herinneringsbrief(int i, Klant k, String r, Date dat){
		id = i;
		deKlant = k;
		reden = r;
		datum = dat;
		k.voegBriefToe(this);
	}

	public Date getDatum(){
		return datum;
	}

	public int getId() {
		return id;
	}

	public Klant getDeKlant() {
		return deKlant;
	}

	public String getReden() {
		return reden;
	}

	public SimpleDateFormat getDf() {
		return df;
	}
	
}