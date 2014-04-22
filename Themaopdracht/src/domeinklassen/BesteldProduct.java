package domeinklassen;

public class BesteldProduct{
	private int hoeveelheid;
	private Product hetProduct;
	public BesteldProduct(Product pr, int hh){
		hetProduct = pr;
		hoeveelheid = hh;
	}
		
	public int getHoeveelheid(){
		return hoeveelheid;
	}
	
	public Product getProduct(){
		return hetProduct;
	}
	
	public String toString(){
		return hetProduct.getNaam() + "; " + hoeveelheid + " " + hetProduct.getEenheid();
	}
}