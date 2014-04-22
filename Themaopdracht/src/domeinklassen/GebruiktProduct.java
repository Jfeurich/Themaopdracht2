package domeinklassen;

import java.text.DecimalFormat;

public class GebruiktProduct {
	private int aantal;
	private Product hetProduct;
	private DecimalFormat df = new DecimalFormat("#.##");

	public GebruiktProduct(int a, Product hP){
		aantal = a;
		hetProduct = hP;
	}

	public double getKosten(){
		return aantal * hetProduct.getPrijsPerStuk();
	}
	public String toString(double btw){
		double totaalPrijs = hetProduct.getPrijsPerStuk() * aantal;
		double totaalPrijsBTW = totaalPrijs * (1.0 + (btw * 0.01));
		return hetProduct.getNaam() + " Aantal " + aantal + " € " + hetProduct.getPrijsPerStuk() + " Totaalbedrag €" + df.format(totaalPrijs) + "; Totaalbedrag inclusief BTW: €" + df.format(totaalPrijsBTW);
	}
}
