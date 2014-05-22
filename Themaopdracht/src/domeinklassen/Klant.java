package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Klant {

	private int klantnummer;
	private String naam, adres, plaats, rekeningnummer;
	private Date datum = new Date();
	private int telefoonnummer;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private ArrayList<Auto> deAutos = new ArrayList<Auto>();
	private ArrayList<Herinneringsbrief> deBrieven = new ArrayList<Herinneringsbrief>();
	private User account;

	public Klant(int kn, String nm, String adr, String wp, String rnr, int nr){
		klantnummer = kn;
		naam = nm;
		adres = adr;
		plaats = wp;
		rekeningnummer = rnr;
		telefoonnummer = nr;
	}
	
	public void setUser(User u){
		account = u;
	}
	
	public User getAccount(){
		return account;
	}

	public String toString(){
		return "Naam " + naam + "; adres " + adres + "; woonplaats " + plaats + "; rekeningnummer " + rekeningnummer + "; telefoonnummer " + telefoonnummer;
	}

	public int getKlantnummer(){
		return klantnummer;
	}
	
	public String getNaam(){
		return naam;
	}

	public String getAdres() {
		return adres;
	}

	public String getPlaats() {
		return plaats;
	}

	public String getRekeningnummer() {
		return rekeningnummer;
	}

	public int getTelefoonnummer() {
		return telefoonnummer;
	}

	public SimpleDateFormat getDf() {
		return df;
	}

	public ArrayList<Auto> getDeAutos() {
		return deAutos;
	}

	public ArrayList<Herinneringsbrief> getDeBrieven() {
		return deBrieven;
	}

	public ArrayList<Auto> getAutos(){
		return deAutos;
	}

	public void voegAutoToe(Auto a){
		deAutos.add(a);
	}

	public void voegBriefToe(Herinneringsbrief br){
		deBrieven.add(br);
	}

	public ArrayList<Auto> onderhoudNodig(){
		ArrayList<Auto> oh = new ArrayList<Auto>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(datum);
		cal.add(Calendar.MONTH, -6);
		Date ZesMaandenGeleden = cal.getTime();
		for(Auto a : deAutos){
			if(a.laatsteKlus().before(ZesMaandenGeleden)){
				oh.add(a);
			}
		}
		return oh;
	}

	public Date laatsteBezoek() throws Exception{
		Date d = df.parse("00-00-0000");
		for(Auto a : deAutos){
			Date l = a.laatsteKlus();
			if(l.after(d)){
				d = l;
			}
		}
		return d;
	}

	public String getLaatsteBezoek() throws Exception{
		boolean gewijzigd = false;
		Date d = df.parse("00-00-0000");
		for(Auto a : deAutos){
			Date l = a.laatsteKlus();
			if(l.after(d)){
				d = l;
				gewijzigd = true;
			}
		}
		if(gewijzigd){
			return df.format(d);
		}
		return "Geen bezoeken";
	}
	
	public String getLaatsteBrief() throws Exception{
		boolean gewijzigd = false;
		Date d = df.parse("00-00-0000");
		for(Herinneringsbrief h : deBrieven){
			Date l = h.getDatum();
			if(l.after(d)){
				gewijzigd = true;
				d = l;
			}
		}
		if(gewijzigd){
			return df.format(d);
		}
		return "Geen brieven";
	}
	
	public void setAutos(ArrayList<Auto> lijst){
		deAutos = lijst;
	}

	public boolean isRegelmatig(){
		boolean b = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(datum);
		cal.add(Calendar.YEAR, -1);
		Date jaarterug = cal.getTime();
		int aantal = 0;
		for(Auto a : deAutos){
			ArrayList<Klus> klussen = new ArrayList<Klus>(a.getKlussen());
			if(klussen.size() > 0){
				for(Klus k : klussen){
					Date dat = k.getDatum();
					if(dat.before(jaarterug)){
						aantal++;
					}
				}
			}
		}
		if(aantal >= 6){
			b = true;
		}
		return b;
	}

	public ArrayList<Herinneringsbrief> getHerinneringsBrieven(){
		return deBrieven;
	}
}