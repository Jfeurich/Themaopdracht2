package domeinklassen;

import java.util.Date;

public class Reparatie extends Klus {

	public Reparatie(Date dat, String b){
		super(dat, b);
	}

	public Reparatie(Date dat, String b, Auto dA){
		super(dat, b, dA);
	}

	public Reparatie(String dat, String b, Auto dA) throws Exception{
		super(dat, b, dA);
	}

	public double berekenKosten(){
		double kosten = manuren * 8.50;
		return kosten;
	}

	public String toString(){
		String s = super.toString() + "Reparatie: " + datum + "; ";
		return s;
	}
}