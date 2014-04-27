package domeinklassen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectDBVoorraad {
	final static String DB_DRIV = "com.mysql.jdbc.Driver";
	private String databaseURL = "jdbc:mysql://localhost:3306/ThemaopdrachtDB";
	
	//maak connectie
	public ConnectDBVoorraad(){
		try{
			Class.forName(DB_DRIV).newInstance();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	public ArrayList<Product> getVoorraad(){
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
				catch(Exception e){
					System.out.println(e);
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
				catch(Exception e){
					System.out.println(e);
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
	public Product zoekProduct(String naam){
		Product terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM PRODUCT WHERE naam=" + naam;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				int aNr = rs.getInt("productid");
				int mA = rs.getInt("minimumAanwezig");
				String ee = rs.getString("eenheid");
				double pPS = rs.getDouble("prijsPerStuk");
				terug = new Product(nm, aNr, mA, ee, pPS);
				try{
					int aantal = rs.getInt("aantal");
					terug.setAantal(aantal);
				}
				catch(Exception e){
					System.out.println(e);
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
}
