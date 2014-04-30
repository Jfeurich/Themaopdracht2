package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Product;

public class ConnectDBProduct extends ConnectDB{
	
	//maak connectie
	public ConnectDBProduct(){
		super();
	}
	
	//alle producten in het systeem
	public ArrayList<Product> getProducten(){
		ArrayList<Product> deVoorraad = new ArrayList<Product>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM PRODUCT";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				int aNr = rs.getInt("productid");
				int mA = rs.getInt("minimumAanwezig");
				String ee = rs.getString("eenheid");
				double pPS = rs.getDouble("prijsPerStuk");
				Product p = new Product(nm, aNr, mA, ee, pPS);
				try{
					int aantal = rs.getInt("aantal");
					p.setAantal(aantal);
				}
				catch(Exception ex){
					System.out.println(ex);
				}
				deVoorraad.add(p);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return deVoorraad;
	}
	
	//zoek product op productid
	public Product zoekProduct(int artikelnr){
		Product terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
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
				try{
					int aantal = rs.getInt("aantal");
					terug.setAantal(aantal);
				}
				catch(Exception ex){
					System.out.println(ex);
				}
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//zoek producten die de zoekterm in hun naam hebben
	public ArrayList<Product> zoekProduct(String naam){
		ArrayList<Product> terug = new ArrayList<Product>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM PRODUCT WHERE naam LIKE '%" + naam + "%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				int aNr = rs.getInt("productid");
				int mA = rs.getInt("minimumAanwezig");
				String ee = rs.getString("eenheid");
				double pPS = rs.getDouble("prijsPerStuk");
				Product p = new Product(nm, aNr, mA, ee, pPS);
				try{
					int aantal = rs.getInt("aantal");
					p.setAantal(aantal);
				}
				catch(Exception e){
					System.out.println(e);
				}
				terug.add(p);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//maakt nieuw product.  id wordt automatisch toegewezen. geeft product-object terug zodat je het id weet.
	public Product nieuwProduct(String nm, int min, String eh, double pps){
		Product terug = null;
		try{			
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			//maak nieuw product met gegeven waarden
			String sql = "INSERT INTO Product (naam, minimumAanwezig, eenheid, prijsPerStuk) VALUES ('" + nm + "', " + min + ", '" + eh + "', " + pps + ");";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//zoek meest recente product (hoogste artikelnr) momenteel in database (dat is degene die we net toe hebben gevoegd)
			String sql2 = "SELECT MAX(productid) FROM PRODUCT";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int zoeknummer = 0;
			while(rs.next()){
				zoeknummer = rs.getInt(1);			
			}
			stmt2.close();
			con.close();
			//zoek product op basis van gevonden artikelnummer
			terug = zoekProduct(zoeknummer);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//wijzigt product in database naar alle waarden van ingevoerde product-object (exclusief het id)
	public boolean updateProduct(Product p){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "UPDATE Product SET naam='" + p.getNaam() + "',  minimumAanwezig=" + p.getMinimumAanwezig() + 
					", eenheid='" + p.getEenheid() + "', prijsPerStuk=" + p.getPrijsPerStuk() + ", aantal=" + p.getAantal()+ 
					" WHERE productid = " + p.getArtikelNr();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			con.close();
			return true;
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return false;
	}
	
	//delete tabelrij met ingevoerd productid
	public boolean verwijderProduct(int productid){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "DELETE FROM Product WHERE productid=" + productid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
			return true;
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return false;
	}
}
