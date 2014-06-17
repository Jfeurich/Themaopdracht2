package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Product;

public class ConnectDBProduct{
	
	private Connection con;
	//maak connectie
	public ConnectDBProduct(Connection c){
		con = c;
	}
	
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
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij product zoeken op artikelnr " + ex);
		}
		return terug;
	}
	
	//zoek producten die de zoekterm in hun naam hebben
	public ArrayList<Product> zoekProduct(String naam){
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
				terug.add(p);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij product zoeken op naam " + ex);
		}
		return terug;
	}
	
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
			int hoogste = hoogsteArtNr();
			//zoek product op basis van gevonden artikelnummer
			terug = zoekProduct(hoogste);
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuw product " + ex);
		}
		return terug;
	}
	
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
	
	//hoogste artikelnummer
	public int hoogsteArtNr(){
		try {
			String sql = "SELECT MAX(productid) AS max FROM Product";
			Statement stmt;
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int maximum = rs.getInt(1);			
			stmt.close();
			return maximum;
		} 
		catch (SQLException e) {
			System.out.println("Probleem bij product met hoogste artikelnr zoeken" + e);
			return -1;
		}
	}
}
