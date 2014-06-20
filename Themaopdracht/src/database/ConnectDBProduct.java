package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Product;

/**	
*	Dit is klasse ConnectDBProduct
*	Deze klasse is verantwoordelijk voor het communiceren met en beheren van de tabel Product in de database
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/

public class ConnectDBProduct{
	
	/**
	*   Variabele con, type Connection
	*	Hier wordt de verbinding met de database opgeslagen
	*	Deze fungeert als brug tussen de java code en de MySQL database
	**/
	private Connection con;
	
	/**
	*	Constructor ConnectDBProduct
	*	Dit is de constructor voor ConnectDBProduct
	*	@param c	Connection met de database
	**/
	public ConnectDBProduct(Connection c){
		con = c;
	}
	
	/**
	*	Methode getProducten
	*	Haalt alle regels op uit tabel Product waar actief 't' is
	*	@return ArrayList<Product>
	**/	
	//alle producten in het systeem
	public ArrayList<Product> getProducten(){
		ArrayList<Product> deVoorraad = new ArrayList<Product>();
		try{
			String sql = "SELECT * FROM Product WHERE actief='t'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				int aNr = rs.getInt("productid");
				int mA = rs.getInt("minimumAanwezig");
				String ee = rs.getString("eenheid");
				double pPS = rs.getDouble("prijsPerStuk");
				Product p = new Product(nm, aNr, mA, ee, pPS);
				int aantal = rs.getInt("aantal");
				p.setAantal(aantal);
				deVoorraad.add(p);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij producten ophalen " + ex);
		}
		return deVoorraad;
	}

	/**
	*	Methode getProductenOpVoorraad
	*	Haalt alle regels op uit tabel Product waar voorraad groter is dan 0
	*	@return ArrayList<Product>
	**/	
	//alle producten waarvan de voorraad groter is dan 0
	public ArrayList<Product> getProductenOpVoorraad(){
		ArrayList<Product> deVoorraad = new ArrayList<Product>();
		try{
			String sql = "SELECT * FROM Product WHERE actief='t' AND aantal>0";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				int aNr = rs.getInt("productid");
				int mA = rs.getInt("minimumAanwezig");
				String ee = rs.getString("eenheid");
				double pPS = rs.getDouble("prijsPerStuk");
				int aantal = rs.getInt("aantal");
				Product p = new Product(nm, aNr, mA, ee, pPS);
				p.setAantal(aantal);
				deVoorraad.add(p);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij producten onder minimumvoorraad ophalen " + ex);
		}
		return deVoorraad;
	}

	/**
	*	Methode getProductenOnderMinimum
	*	Haalt alle regels op uit tabel Product waar de voorraad kleiner is dan de minimumvoorraad
	*	@return ArrayList<Product>
	**/	
	//alle producten waarvan de voorraad lager is dan de minimumvoorraad
	public ArrayList<Product> getProductenOnderMinimum(){
		ArrayList<Product> deVoorraad = new ArrayList<Product>();
		try{
			String sql = "SELECT * FROM Product WHERE actief='t' AND aantal<minimumAanwezig";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				int aNr = rs.getInt("productid");
				int mA = rs.getInt("minimumAanwezig");
				String ee = rs.getString("eenheid");
				double pPS = rs.getDouble("prijsPerStuk");
				int aantal = rs.getInt("aantal");
				Product p = new Product(nm, aNr, mA, ee, pPS);
				p.setAantal(aantal);
				deVoorraad.add(p);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij producten onder minimumvoorraad ophalen " + ex);
		}
		return deVoorraad;
	}

	/**
	*	Methode zoekProduct
	*	Zoekt naar de tabelregel met het ingevoerde productid, maakt een Product-object met het resultaat, en geeft dit object terug
	*	@param	artikelnr	int	Het productid waar op moet worden gezocht.
	*	@return Product
	**/		
	//zoek product op productid
	public Product zoekProduct(int artikelnr){
		Product terug = null;
		try{
			String sql = "SELECT * FROM PRODUCT WHERE productid=" + artikelnr;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				int aNr = artikelnr;
				int mA = rs.getInt("minimumAanwezig");
				String ee = rs.getString("eenheid");
				double pPS = rs.getDouble("prijsPerStuk");
				terug = new Product(nm, aNr, mA, ee, pPS);
				int aantal = rs.getInt("aantal");
				terug.setAantal(aantal);
				String actief = rs.getString("actief");
				if(actief.equals("f")){
					terug.setActief(false);
				}
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij product zoeken op artikelnr " + ex);
		}
		return terug;
	}
	
	/**
	*	Methode zoekProductNaam
	*	Haalt alle regels op uit tabel Product waar de naam (deels) overeenkomt met de gegeven naam
	*	@param	naam	String	De naam waar op moet worden gezocht.
	*	@return ArrayList<Product>
	**/		
	//zoek producten die de zoekterm in hun naam hebben
	public ArrayList<Product> zoekProductNaam(String naam){
		ArrayList<Product> terug = new ArrayList<Product>();
		try{
			String sql = "SELECT * FROM Product WHERE naam LIKE '%" + naam + "%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				int aNr = rs.getInt("productid");
				int mA = rs.getInt("minimumAanwezig");
				String ee = rs.getString("eenheid");
				double pPS = rs.getDouble("prijsPerStuk");
				Product p = new Product(nm, aNr, mA, ee, pPS);
				int aantal = rs.getInt("aantal");
				p.setAantal(aantal);
				String actief = rs.getString("actief");
				if(actief.equals("f")){
					p.setActief(false);
				}
				terug.add(p);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij product zoeken op naam " + ex);
		}
		return terug;
	}
	
	/**
	*	Methode zoekProductEenheid
	*	Haalt alle regels op uit tabel Product waar de eenheid (deels) overeenkomt met de gegeven tekst
	*	@param	naam	String	De tekst waar op moet worden gezocht.
	*	@return ArrayList<Product>
	**/		
	//zoek producten op eenheid
	public ArrayList<Product> zoekProductEenheid(String eh){
		ArrayList<Product> terug = new ArrayList<Product>();
		try{
			String sql = "SELECT * FROM Product WHERE eenheid LIKE '%" + eh + "%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				int aNr = rs.getInt("productid");
				int mA = rs.getInt("minimumAanwezig");
				String ee = rs.getString("eenheid");
				double pPS = rs.getDouble("prijsPerStuk");
				Product p = new Product(nm, aNr, mA, ee, pPS);
				int aantal = rs.getInt("aantal");
				p.setAantal(aantal);
				String actief = rs.getString("actief");
				if(actief.equals("f")){
					p.setActief(false);
				}
				terug.add(p);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij product zoeken op naam " + ex);
		}
		return terug;
	}

	/**
	*	Methode nieuwProduct
	*	Maakt een nieuwe regel in tabel Product met de ingevoerde parameters, zoekt de hoogste Primary Key in Product, en roept hiermee zoekProduct aan.
	*	@param	nm	String De naam van het product.
	*	@param	min	int	De minimumvoorraad van dit product.
	*	@param	eh	String	De eenheid waarin dit product wordt getelt.
	*	@param	pps	double	De prijs voor 1 item van dit Product
	*	@return Product
	**/		
	//maakt nieuw product.  id wordt automatisch toegewezen. geeft product-object terug zodat je het id weet.
	public Product nieuwProduct(String nm, int min, String eh, double pps){
		Product terug = null;
		try{			
			//maak nieuw product met gegeven waarden
			String sql = "INSERT INTO Product (naam, minimumAanwezig, eenheid, prijsPerStuk, aantal, actief) VALUES ('" + nm + "', " + min + ", '" + eh + "', " + pps + ", 0, 't');";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//zoek meest recente product (hoogste artikelnr) momenteel in database (dat is degene die we net toe hebben gevoegd)
			String sql2 = "SELECT MAX(productid) AS max FROM Product";
			Statement stmt2;
			stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			rs.next();
			int maximum = rs.getInt(1);			
			stmt2.close();
			//zoek product op basis van gevonden artikelnummer
			terug = zoekProduct(maximum);
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuw product " + ex);
		}
		return terug;
	}

	/**
	*	Methode updateProduct
	*	Zoekt de regel waarvan het productid overeen komt met het gegeven Product en wijzigt alle gegevens naar die van het Product
	*	@param	p	Product	Het product waarvan de regel een update moet krijgen.
	*	@return boolean
	**/		
	//wijzigt product in database naar alle waarden van ingevoerde product-object (exclusief het id)
	public boolean updateProduct(Product p){
		try{
			String sql = "UPDATE Product SET naam='" + p.getNaam() + "',  minimumAanwezig=" + p.getMinimumAanwezig() + 
					", eenheid='" + p.getEenheid() + "', prijsPerStuk=" + p.getPrijsPerStuk() + ", aantal=" + p.getAantal()+ 
					" WHERE productid = " + p.getArtikelNr();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij product updaten " + ex);
		}
		return false;
	}

	/**
	*	Methode verwijderProduct
	*	Zoekt de regel waarvan het productid overeen komt met het gegeven nummer en zet actief op 'f'
	*	@param	productid	int	Het productid van de regel die op non-actief moet worden gezet.
	*	@return boolean
	**/	
	//zet product op non-actief
	public boolean verwijderProduct(int productid){
		try{
			String sql = "UPDATE Product SET actief='f' WHERE productid=" + productid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij product verwijderen " + ex);
		}
		return false;
	}
	
	/**
	*	Methode activeerProduct
	*	Zoekt de regel waarvan het productid overeen komt met het gegeven nummer en zet actief op 't'
	*	@param	productid	int	Het productid van de regel die op actief moet worden gezet.
	*	@return boolean
	**/	
	//zet product op actief
	public boolean activeerProduct(int productid){
		try{
			String sql = "UPDATE Product SET actief='t' WHERE productid=" + productid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij product activeren " + ex);
		}
		return false;
	}
}
