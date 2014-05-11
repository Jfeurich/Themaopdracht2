package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Herinneringsbrief{

	private int id;
	private Klant deKlant;
	private String reden;
	private Date datum;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	public Herinneringsbrief(Klant k, String r){
		deKlant = k;
		k.voegBriefToe(this);
		reden = r;
		datum = new Date();
	}

	public Herinneringsbrief(int i, Klant k, String r, Date dat){
		id = i;
		deKlant = k;
		k.voegBriefToe(this);
		reden = r;
		datum = dat;
	}

	public Klant getDeKlant() {
		return deKlant;
	}

	public String getReden() {
		return reden;
	}

	public Date getDatum(){
		return datum;
	}
	
	public int getID(){
		return id;
	}

	//kijkt of het om een herinnering voor onderhoud gaat of een klant die al lang niet meer is geweest
	public String getDetails() throws Exception{
		String s = "";
		if(reden.equals("onderhoud")){
			ArrayList<Auto> oh = new ArrayList<Auto>(deKlant.onderhoudNodig());
			s += "Betreft:\n";
			for(Auto a : oh){
				s += a+ "\n";
				s += "Deze auto is op " + df.format(a.laatsteKlus()) + " voor het laatst bij ons geweest.\n";
			}
			s += "Wij raden u aan om langs te komen voor een onderhoudsbeurt.";
		}
		if(reden.equals("regelmaat")){
			s += "U bent " + df.format(deKlant.laatsteBezoek()) + " voor het laatst bij ons op bezoek geweest. Wij nodigen u graag uit om weer eens langs te komen.";
		}
		return s;
	}

	public String toString(){
		String s = "";
		try{
			s += "Geachte mijnheer/mevrouw " + deKlant.getNaam() + "\n" + getDetails();
		}
		catch(Exception e){
			System.out.println("error!");
		}
		return s;
	}
}