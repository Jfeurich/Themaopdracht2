package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.BesteldProduct;
import domeinklassen.GebruiktProduct;

public class ConnectDBGebruiktProduct extends ConnectDB {
	
	public ArrayList<GebruiktProduct> getGebruikteProducten(){
		ArrayList<GebruiktProduct> terug = new ArrayList<GebruiktProduct>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM GebruiktProduct";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int gpid = rs.getInt("gebruiktproductid");
				int pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				GebruiktProduct gp = new GebruiktProduct(gpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct();
				gp.setHetProduct(pconn.zoekProduct(pid));
				terug.add(gp);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public ArrayList<GebruiktProduct> getProductenVanKlus(int klusid){
		ArrayList<GebruiktProduct> terug = new ArrayList<GebruiktProduct>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM GebruiktProduct WHERE klusid=" + klusid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int gpid = rs.getInt("gebruiktproductid");
				int pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				GebruiktProduct gp = new GebruiktProduct(gpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct();
				gp.setHetProduct(pconn.zoekProduct(pid));
				terug.add(gp);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public GebruiktProduct zoekGebruiktProduct(int gpid){
		GebruiktProduct terug = null;
		int pid = 0;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM GebruiktProduct WHERE gebruiktproductid=" + gpid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				terug = new GebruiktProduct(gpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct();
				terug.setHetProduct(pconn.zoekProduct(pid));
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public GebruiktProduct nieuwGebruiktProduct(int klusid, int productid, int aantal){
		GebruiktProduct terug = null;
		try{			
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			//maak nieuw product met gegeven waarden
			String sql = "INSERT INTO GebruiktProduct (klusid, productid, aantal) VALUES (" + klusid + ", " + productid + ", " + aantal + ");";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//zoek meest recente klant (hoogste id) momenteel in database (dat is degene die we net toe hebben gevoegd)
			String sql2 = "SELECT MAX(gebruiktproductid) FROM GebruiktProduct";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int gpid = 0;
			while(rs.next()){
				gpid = rs.getInt(1);		
			}
			stmt2.close();
			con.close();
			//zoek klant op basis van gevonden klantnummer
			terug = zoekGebruiktProduct(gpid);	
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public boolean updateGebruiktProduct(GebruiktProduct gp){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "UPDATE GebruiktProduct SET aantal=" + gp.getAantal() + " WHERE gebruiktproductid = " + gp.getID();
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
	
	public boolean verwijderGebruiktProduct(int gpid){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "DELETE FROM GebruiktProduct WHERE gebruiktproductid=" + gpid;
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
