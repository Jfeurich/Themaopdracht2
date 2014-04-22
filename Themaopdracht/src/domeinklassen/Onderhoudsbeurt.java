package domeinklassen;

import java.util.Date;

public class Onderhoudsbeurt extends Klus {

	public Onderhoudsbeurt(Date dat, String b, Auto dA){
		super(dat, b, dA);
	}

	public Onderhoudsbeurt(String dat, String b, Auto dA) throws Exception{
		super(dat, b, dA);
	}

	public double berekenKosten(){
		double kosten = manuren * 9.50;
		return kosten;
	}

	public String toString(){
		String s = "Onderhoudsbeurt: " + datum + "; " + deAuto.getKenteken();
		return s;
	}
}