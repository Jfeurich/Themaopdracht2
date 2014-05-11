package domeinklassen;

public class BesteldProduct{
	private int id;
	private int hoeveelheid;
	private Product hetProduct;
	public BesteldProduct(Product pr, int hh){
		hetProduct = pr;
		hoeveelheid = hh;
	}
	
	public BesteldProduct(int i, int hh){
		id = i;
		hoeveelheid = hh;
	}
		
	public void setID(int i){
		id = i;
	}
	
	public void setProduct(Product p){
		hetProduct = p;
	}
	public int getID(){
		return id;
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