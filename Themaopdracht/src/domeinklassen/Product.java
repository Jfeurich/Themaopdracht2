package domeinklassen;

public class Product{
	private String naam;
	private int artikelNr;
	private int minimumAanwezig;
	private String eenheid;
	private int aantal;
	private double prijsPerStuk;

	public Product(String nm, int aNr, int mA, String ee){
		naam = nm;
		artikelNr = aNr;
		minimumAanwezig = mA;
		eenheid = ee;
		aantal = 0;
		prijsPerStuk = 0.0;
	}
	public Product(String nm, int aNr, int mA, String ee, double pPS){
		naam = nm;
		artikelNr = aNr;
		minimumAanwezig = mA;
		eenheid = ee;
		aantal = 0;
		prijsPerStuk = pPS;
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
	//methode om het aantal te verhogen bij een levering van een bestelling
	public void voegAantalToe(int aa){
		aantal += aa;
	}
	public double getPrijsPerStuk(){
		return prijsPerStuk;
	}
	public String toString(){
		return "Naam: " + naam + "; Artikelnummer: " + artikelNr + "; Minimum aanwezig: " + minimumAanwezig + "; Eenheid: " + eenheid + "; Voorraad: " + aantal;
	}
}