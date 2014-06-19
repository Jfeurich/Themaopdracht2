package domeinklassen;

public class Product{
	private String naam;
	private int artikelNr;
	private int minimumAanwezig;
	private String eenheid;
	private int aantal;
	private double prijsPerStuk;
	private boolean actief = true;
	
	public Product(String nm, int aNr, int mA, String ee, double pPS){
		naam = nm;
		artikelNr = aNr;
		minimumAanwezig = mA;
		eenheid = ee;
		aantal = 0;
		prijsPerStuk = pPS;
	}
	public boolean isActief() {
		return actief;
	}
	public void setActief(boolean actief) {
		this.actief = actief;
	}
	public String getNaam(){
		return naam;
	}
	public int getArtikelNr(){
		return artikelNr;
	}
	public int getMinimumAanwezig(){
		return minimumAanwezig;
	}
	public void setMinimumAanwezig(int mA){
		minimumAanwezig = mA;
	}
	public int getAantal(){
		return aantal;
	}
	public void setAantal(int aa){
		aantal = aa;
	}
	public String getEenheid(){
		return eenheid;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public void setArtikelNr(int artikelNr) {
		this.artikelNr = artikelNr;
	}
	public void setEenheid(String eenheid) {
		this.eenheid = eenheid;
	}
	public void setPrijsPerStuk(double prijsPerStuk) {
		this.prijsPerStuk = prijsPerStuk;
	}
	//methode om het aantal te verhogen bij een levering van een bestelling
	public void voegAantalToe(int aa){
		aantal += aa;
	}
	public double getPrijsPerStuk(){
		return prijsPerStuk;
	}
	public String toString(){
		String s = "";
		if(!actief){
			s += "NON-ACTIEF PRODUCT: ";
		}
		s += "Naam: " + naam + "; Artikelnummer: " + artikelNr + "; Minimum aanwezig: " + minimumAanwezig + "; Eenheid: " + eenheid + "; Voorraad: " + aantal;
		return s;
	}
}