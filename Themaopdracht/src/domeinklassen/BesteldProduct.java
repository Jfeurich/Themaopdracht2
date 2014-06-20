package domeinklassen;

/**	
*	Dit is klasse BesteldProduct
*	Deze klasse zorgt ervoor bestelde producten een id en hoeveelheid kunnen hebben
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class BesteldProduct{
	
	/**
	*	Variabele id
	*	Zodat een BesteldProduct een uniek id kan hebben
	**/
	private int id;
	
	/**
	*	Variabele hoeveelheid
	*	ZOdat een BesteldProduct een hoeveelheid heeft 
	**/
	private int hoeveelheid;
	
	/**
	*	Variabele hetProduct
	*	Zodat er een Product object aan het BesteldProduct kan worden gekoppeld, zodat je weet wat het bestelde product is
	**/
	private Product hetProduct;
	
	/**
	 * Constructor BesteldProduct
	 * Constructor met Product en hoeveelheid
	 * @param pr	is het product
	 * @param hh	is de hoeveelheid
	 */
	public BesteldProduct(Product pr, int hh){
		hetProduct = pr;
		hoeveelheid = hh;
	}
	
	/**
	 * Constructor BesteldProduct
	 * Constructor met ID en hoeveelheid
	 * @param i		is het ID
	 * @param hh	is de hoeveelheid
	 */
	public BesteldProduct(int i, int hh){
		id = i;
		hoeveelheid = hh;
	}
	
	/**
	*	Methode setID
	*	Deze methode zorgt ervoor dat je een ID kan instellen voor BesteldProduct
	*	@param i	int een getal
	*	@return void
	**/
	public void setID(int i){
		id = i;
	}
	
	/**
	*	Methode setProduct
	*	Deze methode zorgt ervoor dat je een Product aan het BesteldProduct kan koppelen
	*	@param p	Product het product dat je aan BesteldProduct wilt koppelen
	*	@return void
	**/
	public void setProduct(Product p){
		hetProduct = p;
	}
	
	/**
	*	Methode getID
	*	Haalt het ID op van BesteldProduct
	*	@return int
	**/
	public int getID(){
		return id;
	}
	
	/**
	*	Methode getHoeveelheid
	*	Deze methode haalt de hoeveelheid op van BesteldProduct
	*	@return int
	**/
	public int getHoeveelheid(){
		return hoeveelheid;
	}
	
	/**
	*	Methode getProduct
	*	Deze methode haalt het product op van BesteldProduct
	*	@return Product
	**/
	public Product getProduct(){
		return hetProduct;
	}
	
	/**
	*	Methode toString
	*	Deze methode geeft alle informatie van het BesteldProduct
	*	@return String
	**/
	public String toString(){
		return hetProduct.getNaam() + "; " + hoeveelheid + " " + hetProduct.getEenheid();
	}
}