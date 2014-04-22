package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservering {
	private Date beginDat;
	private Date eindDat;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private int deParkeerplek;
	private Auto deAuto;

	public Reservering(Date bD, Date eD, int dP){
		beginDat = bD;
		eindDat = eD;
		deParkeerplek = dP;
	}

	public boolean isTussenDatum(Date bD, Date eD){
		boolean b = false;
		if((bD.before(beginDat) || bD.equals(beginDat))&& (eD.after(eindDat) || eD.equals(eindDat))){
			b = true;
		}
		return b;
	}

	public void voegAutoToe(Auto a){
		deAuto = a;
	}
	public Date getBegDat(){
		return beginDat;
	}
	public Auto getAuto(){
		return deAuto;
	}
	public int getDeParkeerplek(){
		return deParkeerplek;
	}
	public void setData(Date bD, Date eD){
		beginDat = bD;
		eindDat = eD;
	}

	public String toString(){
		return "Van " + df.format(beginDat) + " tot " + df.format(eindDat) + " op parkeerplek " + deParkeerplek + " staat auto met kenteken " + deAuto.getKenteken();
	}
}
