package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservering {
	private int id;
	private Date beginDat;
	private Date eindDat;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private int deParkeerplek;
	private Auto deAuto;
	private boolean isGeweest;
	
	public Reservering(Auto a, int i, Date bD, Date eD, int dP){
		deAuto = a;
		id = i;
		beginDat = bD;
		eindDat = eD;
		deParkeerplek = dP;
		isGeweest = false;
	}

	public boolean isGeweest() {
		return isGeweest;
	}

	public void setGeweest(boolean isGeweest) {
		this.isGeweest = isGeweest;
	}

	public void voegAutoToe(Auto a){
		deAuto = a;
	}
	public int getID(){
		return id;
	}
	public Date getBegDat(){
		return beginDat;
	}
	public Date getEindDat(){
		return eindDat;
	}
	public String getBegDatNetjes(){
		return df.format(beginDat);
	}
	public String getEindDatNetjes(){
		return df.format(eindDat);
	}
	public Auto getAuto(){
		return deAuto;
	}
	public int getDeParkeerplek(){
		return deParkeerplek;
	}

	public String toString(){
		return "Van " + df.format(beginDat) + " tot " + df.format(eindDat) + " op parkeerplek " + deParkeerplek + " staat auto met kenteken " + deAuto.getKenteken();
	}
}
