package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**	
*	Dit is klasse Klant
*	Deze klasse wordt gebruikt om de gegevens van klanten op te kunnen slaan.
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class Klant {
	/**
	*	Het klantnummer van de Klant
	**/
	private int klantnummer;
	/**
	*	De naam van een Klant
	**/
	private String naam;
	/**
	*	Het adres van de Klant
	**/
	private String adres; 
	/**
	*	De woonplaats van de Klant
	**/
	private String plaats; 
	/**
	*	Het rekeningnummernummer van de Klant
	**/
	private String rekeningnummer;
	/**
	*	De datum van vandaag
	**/
	private Date datum = new Date();
	/**
	*	Het telefoonnummer van de Klant
	**/
	private int telefoonnummer;
	/**
	*	Een dateformatter om datums te formatteren
	**/
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	/**
	*	De ArrayList met Auto's van de Klant
	**/
	private ArrayList<Auto> deAutos = new ArrayList<Auto>();
	/**
	*	De ArrayList met Herinneringsbrieven die aan een klant zijn gestuurd
	**/
	private ArrayList<Herinneringsbrief> deBrieven = new ArrayList<Herinneringsbrief>();
	/**
	*	De gebruikersaccount van de Klant
	**/
	private User account;
	/**
	*	Constructor voor Klant
	*	@param int klantnummer	klantnummer van de klant
	*	@param String naam	naam van de klant
	*	@param String adres	adres van de klant
	*	@param String plaats	woonplaats van de klant
	*	@param String rekeningnummer	rekeningnummer van de klant
	*	@param int telefoonnummer	telefoonnummer van de klant
	**/	
	public Klant(int kn, String nm, String adr, String wp, String rnr, int nr){
		klantnummer = kn;
		naam = nm;
		adres = adr;
		plaats = wp;
		rekeningnummer = rnr;
		telefoonnummer = nr;
	}
	/**
	*	Deze methode verandert de gekoppelde User van een Klant
	*	@param User account
	**/	
	public void setUser(User u){
		account = u;
	}
	/**
	*	Deze methode returnt de Useraccount van de Klant
	*	@return User account
	**/	
	public User getAccount(){
		return account;
	}
	/**
	*	Deze methode returnt een geformatteerde String van de Klant
	*	@return String toString()
	**/	
	public String toString(){
		return "Naam " + naam + "; adres " + adres + "; woonplaats " + plaats + "; rekeningnummer " + rekeningnummer + "; telefoonnummer " + telefoonnummer;
	}
	/**
	*	Deze methode returnt het klantnummer van de Klant
	*	@return int klantnummer
	**/	
	public int getKlantnummer(){
		return klantnummer;
	}
	/**
	*	Deze methode returnt de naam van de Klant
	*	@return String naam
	**/	
	public String getNaam(){
		return naam;
	}
	/**
	*	Deze methode returnt het adres van de Klant
	*	@return String adres
	**/	
	public String getAdres() {
		return adres;
	}
	/**
	*	Deze methode returnt de woonplaats van de Klant
	*	@return String plaats
	**/	
	public String getPlaats() {
		return plaats;
	}
	/**
	*	Deze methode returnt het rekeningnummer van de Klant
	*	@return String rekeningnummer
	**/	
	public String getRekeningnummer() {
		return rekeningnummer;
	}
	/**
	*	Deze methode returnt het telefoonnummer van de Klant
	*	@return int telefoonnummer
	**/	
	public int getTelefoonnummer() {
		return telefoonnummer;
	}
	/**
	*	Deze methode verandert de naam van een Klant
	*	@param String naam
	**/	
	public void setNaam(String naam) {
		this.naam = naam;
	}
	/**
	*	Deze methode verandert het adres van een Klant
	*	@param String adres
	**/	
	public void setAdres(String adres) {
		this.adres = adres;
	}
	/**
	*	Deze methode verandert de woonplaats van een Klant
	*	@param String plaats
	**/	
	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}
	/**
	*	Deze methode verandert het rekeningnummer van een Klant
	*	@param String rekeningnummer
	**/	
	public void setRekeningnummer(String rekeningnummer) {
		this.rekeningnummer = rekeningnummer;
	}
	/**
	*	Deze methode verandert het telefoonnummer van een Klant
	*	@param int telefoonnummer
	**/	
	public void setTelefoonnummer(int telefoonnummer) {
		this.telefoonnummer = telefoonnummer;
	}
	/**
	*	Deze methode returnt een geformatteerde datum
	*	@return SimpleDateFormat df
	**/	
	public SimpleDateFormat getDf() {
		return df;
	}
	/**
	*	Deze methode returnt de ArrayList met Auto's van de Klant
	*	@return ArrayList<Auto> deAutos
	**/	
	public ArrayList<Auto> getDeAutos() {
		return deAutos;
	}
	/**
	*	Deze methode een Auto toe aan de ArrayList deAutos van een Klant
	*	@param Auto a
	**/	
	public void voegAutoToe(Auto a){
		deAutos.add(a);
	}
	/**
	*	Deze methode voegt een herrineringsbrief to aan de ArrayList deBrieven van een Klant
	*	@param HerinneringsBrief br
	**/	
	public void voegBriefToe(Herinneringsbrief br){
		deBrieven.add(br);
	}
	/**
	*	Deze methode returnt een Arraylist me Auto's die onderhoud nodig hebben
	*	Dit wordt bepaald door te kijken naar wanneer ze voor het laatst onderhouden zijn
	*	(in dit gebal zes maanden geleden
	*	@return ArrayList<Auto> oh
	**/	
	public ArrayList<Auto> onderhoudNodig() throws Exception{
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
	/**
	*	Deze methode returnt de datum waarop een klant voor het laatst langs is geweest
	*	@return Date d
	**/	
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
	/**
	*	Deze methode returnt de datum waarop een klant voor het laatst
	*bijde garage langs is geweest.
	*	@return String d
	**/	
	public String getLaatsteBezoek() throws Exception{
		boolean gewijzigd = false;
		Date d = df.parse("01-01-1950");
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
	/**
	*	Deze methode returnt de datum waarop de laatste herinneringsbrief naar
	*	een klant is verstuurd, of geen brieven.
	*	@return String d
	**/	
	public String getLaatsteBrief() throws Exception{
		boolean gewijzigd = false;
		Date d = df.parse("01-01-1950");
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
	/**
	*	Deze methode returnt de waarde van isregelmatig.
	*	Als de auto meer dan 6* in het afgelopen jaar is langs geweest is hij
	*	een regelmatige klant.
	*	@return boolean b
	**/	
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
	/**
	*	Deze methode returnt de ArrayList met Herinneringsbrieven aan de Klant
	*	@return ArrayList<Herinneringsbrief> deBrieven
	**/	
	public ArrayList<Herinneringsbrief> getHerinneringsBrieven(){
		return deBrieven;
	}
	/**
	*	Deze methode returnt de ArrayList met Klussen van de Auto
	*	@return ArrayList<Klus> klussen
	**/	
	public ArrayList<Klus> getAankomendeKlussen(){
		ArrayList<Klus> klussen = new ArrayList<Klus>();
		for(Auto a : deAutos){
			for(Klus k : a.getKlussen()){
				if(k.getDatum().after(new Date())){
					klussen.add(k);
				}
			}
		}
		return klussen;
	}
}