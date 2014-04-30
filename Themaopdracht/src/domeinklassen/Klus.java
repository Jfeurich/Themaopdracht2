package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class Klus {

	public int id;
	protected Date datum;
	protected String beschrijving;
	protected int manuren;
	protected String status;
	protected Factuur deFactuur;
	protected Auto deAuto;
	protected ArrayList<GebruiktProduct> gebruikteProducten = new ArrayList<GebruiktProduct>();
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	public Klus(Date dat, String b){
		datum = dat;
		beschrijving = b;
		manuren = 0;
		deAuto = null;
		deFactuur = null;	
	}
	public Klus(Date dat, String b, Auto dA){
		datum = dat;
		beschrijving = b;
		manuren = 0;
		deAuto = dA;
		dA.voegKlusToe(this);
		deFactuur = null;
	}

	public Klus(String dat, String b, Auto dA) throws Exception{
		datum = df.parse(dat);
		beschrijving = b;
		manuren = 0;
		deAuto = dA;
		dA.voegKlusToe(this);
		deFactuur = null;
	}
	
	public void setDeAuto(Auto a){
		deAuto = a;
		a.voegKlusToe(this);
	}
	
	public int getID(){
		return id;
	}

	public Auto getAuto(){
		return deAuto;
	}

	public String getStatus(){
		return status;
	}
	public Date getDatum(){
		return datum;
	}
	public String getBeschrijving(){
		return beschrijving;
	}

	public void setStatus(String st){
		status = st;
	}
	
	public void setID(int i){
		id = i;
	}

	public  int getManuren(){
		return manuren;
	}
	public void addManuren(int mu){
		manuren = manuren + mu;
	}

	public ArrayList<GebruiktProduct> getGebruikteProducten(){
		return gebruikteProducten;
	}
	
	public void setGebruikteProducten(ArrayList<GebruiktProduct> array){
		gebruikteProducten = array;
	}
	public void addGebruiktProduct(GebruiktProduct g){
		gebruikteProducten.add(g);
	}

	public abstract double berekenKosten();

	public void setDeFactuur(Factuur f){
		deFactuur = f;
	}

	public Factuur getFactuur(){
		return deFactuur;
	}

	public abstract String toString();
}