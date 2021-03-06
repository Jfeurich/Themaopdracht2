package domeinklassen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**	
*	Dit is klasse Auto
*	Deze klasse zorgt ervoor dat gebruikers een auto kunnen hebben
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class Auto {
	
	/**
	*	Variabele autoid
	*	Deze variabele zorgt ervoor dat elke auto een eigen id heeft
	**/
	private int autoid;
	
	/**
	*	Variabele kenteken
	*	Zorgt ervoor dat een auto een kenteken heeft
	**/
	private String kenteken;
	
	/**
	*	Variabele merk
	*	Zorgt ervoor dat een auto een merkt heeft
	**/
	private String merk;
	
	/**
	*	Variabele type
	*	Zorgt ervoor dat een auto een type heeft
	**/
	private String type;
	
	/**
	*	Variabele deKlussen
	*	Zodat er meerdere Klusobjecten aan ��n auto kan worden toegevoegd
	**/
	private ArrayList<Klus> deKlussen = new ArrayList<Klus>();
	
	/**
	*	Variabele deEigenaar
	*	Zodat er een eigenaar aan de auto kan worden gekoppeld met type Klant
	**/
	private Klant deEigenaar;
	
	/**
	*	Variabele actief
	*	Zodat een auto op actief kan worden gezet
	**/
	private boolean actief = true;
	
	/**
	 * Constructor Auto
	 * De Constructor van auto
	 * @param ken	is het kenteken van de auto
	 * @param mk	is het merk van de auto
	 * @param tp	is het auto type
	 * @param dE	is de eigenaar van het type Klant van de auto
	 */
	public Auto(String ken, String mk, String tp, Klant dE){
		kenteken = ken;
		merk = mk;
		type = tp;
		deEigenaar = dE;
		deEigenaar.voegAutoToe(this);
	}
	
	/**
	*	Methode isActief
	*	Deze methode returned actief van type boolean
	*	@return boolean
	**/	
	public boolean isActief() {
		return actief;
	}
	
	/**
	*	Methode setActief
	*	Deze methode zet de auto op (non-)actief afhankelijk van de gegeven boolean
	*	@param actief	boolean 
	*	@return void
	**/	
	public void setActief(boolean actief) {
		this.actief = actief;
	}
	
	/**
	*	Methode setID
	*	Met deze methode kan je de auto een id geven
	*	@param id	int het gewenste int getal
	*	@return void
	**/	
	public void setID(int id){
		autoid = id;
	}
	
	/**
	*	Methode getID
	*	Deze methode haalt het id op van de auto
	*	@return int
	**/	
	public int getID(){
		return autoid;
	}
	
	/**
	*	Methode getKenteken
	*	Deze methode haalt het kenteken op van de auto
	*	@return String
	**/	
	public String getKenteken(){
		return kenteken;
	}
	
	/**
	*	Methode getMerk
	*	Deze methode haalt het merk op van de auto
	*	@return String
	**/	
	public String getMerk(){
		return merk;
	}
	
	/**
	*	Methode getType
	*	Deze methode haalt het type op van de auto
	*	@return String
	**/	
	public String getType(){
		return type;
	}
	
	/**
	*	Methode voegKlusToe
	*	Deze methode voegt een Klus toe aan de ArrayList deKlussen
	*	@param k	Klus is een Klus object
	*	@return void
	**/	
	public void voegKlusToe(Klus k){
		deKlussen.add(k);
	}
	
	/**
	*	Methode getEigenaar
	*	Deze methode kan je de eigenaar van de auto opvragen
	*	@return Klant
	**/	
	public Klant getEigenaar(){
		return deEigenaar;
	}
	
	/**
	*	Methode laatsteKlus
	*	Laat de datum zien van de laatste klus
	*	@return Date
	**/	
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
	
	/**
	*	Methode getKlussen
	*	Haalt alle klussen op die in de ArrayList staat deKlussen
	*	@return ArrayList<Klus>
	**/	
	public ArrayList<Klus> getKlussen(){
		return deKlussen;
	}
	
	/**
	*	Methode toString
	*	Geeft alle informatie over de auto
	*	@return String
	**/	
	@Override
	public String toString() {
		return "Auto [autoid=" + autoid + ", kenteken=" + kenteken + ", merk="
				+ merk + ", type=" + type + ", deEigenaar=" + deEigenaar
				+ ", actief=" + actief + "]";
	}
	
}