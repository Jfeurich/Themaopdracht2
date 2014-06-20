package domeinklassen;
/**	
*	Dit is klasse Product
*	Deze klasse wordt gebruikt om de gegevens van producten op te kunnen slaan.
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class Product{
	/**
	*	De naam van het product
	**/
	private String naam;
	/**
	*	Het artikelnummer
	**/
	private int artikelNr;
	/**
	*	Minimum aantal aanwezig product in de voorraad
	**/
	private int minimumAanwezig;
	/**
	*	De eenheid waarin het product opgeslagen/aangekocht wordt
	**/
	private String eenheid;
	/**
	*	Aantal van het product in de voorraad
	**/
	private int aantal;
	/**
	*	Prijs per individueel product
	**/
	private double prijsPerStuk;
	
	/**
	*	Wordt het product gebruikt of niet (winterbanden/zomerbanden)
	**/
	private boolean actief = true;
	/**
	*	Constructor voor Product
	*	@param String naam	 naam van het product
	*	@param int artikelNr	artikelnummer van het product
	*	@param int minimumAanwezig minimale voorraad van het product
	*	@param String eenheid	eenheid van het product
	*	@param int aantal	aantal product in de voorraad
	*	@param int prijsPerStuk	Prijs per product
	**/	
	public Product(String nm, int aNr, int mA, String ee, double pPS){
		naam = nm;
		artikelNr = aNr;
		minimumAanwezig = mA;
		eenheid = ee;
		aantal = 0;
		prijsPerStuk = pPS;
	}
	/**
	*	Deze methode returnt de status van een product
	*	@return boolean actief
	**/	
	public boolean isActief() {
		return actief;
	}
	/**
	*	Deze methode verandert de status van een product
	*	@param boolean actief 
	**/	
	public void setActief(boolean actief) {
		this.actief = actief;
	}
	/**
	*	Deze methode returnt de naam van een product
	*	@return String naam
	**/	
	public String getNaam(){
		return naam;
	}
	/**
	*	Deze methode returnt het artikelnummer van een product
	*	@return int artikelNr
	**/	
	public int getArtikelNr(){
		return artikelNr;
	}
	/**
	*	Deze methode returnt de minimumvoorraad van een product
	*	@return minimumAanwezig
	**/	
	public int getMinimumAanwezig(){
		return minimumAanwezig;
	}
	/**
	*	Deze methode verandert de minimumvoorraad van een product
	*	@param minimumAanwezig
	**/	
	public void setMinimumAanwezig(int mA){
		minimumAanwezig = mA;
	}
	/**
	*	Deze methode returnt de voorraad van een product
	*	@return aantal
	**/	
	public int getAantal(){
		return aantal;
	}
	/**
	*	Deze methode verandert de voorraad van een product
	*	@param aantal
	**/	
	public void setAantal(int aa){
		aantal = aa;
	}
	/**
	*	Deze methode returnt de eenheid van een product
	*	@return eenheid
	**/	
	public String getEenheid(){
		return eenheid;
	}
	/**
	*	Deze methode verandert de naam van een product
	*	@param naam
	**/	
	public void setNaam(String naam) {
		this.naam = naam;
	}
	/**
	*	Deze methode verandert het artikelnummer van een product
	*	@param artikelNr 
	**/	
	public void setArtikelNr(int artikelNr) {
		this.artikelNr = artikelNr;
	}
	/**
	*	Deze methode verandert de eenheid van een product
	*	@param eenheid
	**/	
	public void setEenheid(String eenheid) {
		this.eenheid = eenheid;
	}
	/**
	*	Deze methode verandert de prijs van een product
	*	@param prijsPerStukf 
	**/	
	public void setPrijsPerStuk(double prijsPerStuk) {
		this.prijsPerStuk = prijsPerStuk;
	}
	//methode om het aantal te verhogen bij een levering van een bestelling
	public void voegAantalToe(int aa){
		aantal += aa;
	}
	/**
	*	Deze methode returnt de prijs van een product
	*	@return prijsPerStuk
	**/	
	public double getPrijsPerStuk(){
		return prijsPerStuk;
	}
	/**
	*	Deze methode returnt een geformatteerde String van een product
	*	@return s
	**/	
	public String toString(){
		String s = "";
		if(!actief){
			s += "NON-ACTIEF PRODUCT: ";
		}
		s += "Naam: " + naam + "; Artikelnummer: " + artikelNr + "; Minimum aanwezig: " + minimumAanwezig + "; Eenheid: " + eenheid + "; Voorraad: " + aantal;
		return s;
	}
}