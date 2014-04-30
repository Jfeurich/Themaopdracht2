package database;

import java.sql.Connection;
import java.sql.DriverManager;
<<<<<<< HEAD
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Auto;
import domeinklassen.Bestelling;
import domeinklassen.Klant;
import domeinklassen.Onderhoudsbeurt;
import domeinklassen.Reparatie;

public class ConnectDBBestelling extends ConnectDB {
	public ConnectDBBestelling(){
		super();
	}
	
	//ArrayList<Class> getAlles
	public ArrayList<Bestelling> getBestellingen(){
		ArrayList<Bestelling> terug = new ArrayList<Bestelling>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Bestelling";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("bestellingid");
				java.sql.Date dat = rs.getDate("datum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				String isGeleverd = rs.getString("isGeleverd");
				Bestelling b = new Bestelling(id);
				b.setVerwachteDatum(datum);
				if(isGeleverd.equals("t")){
					b.setIsGeleverd(true);
				}
				ConnectDBBesteldProduct bpconn = new ConnectDBBesteldProduct();
				b.setBesteldeProducten(bpconn.getProductenVanBestelling(b.getBestelNummer()));
				terug.add(b);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//Class zoekClass
	public Bestelling zoekBestelling(int bestellingid){
		Bestelling terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Bestelling WHERE bestellingid=" + bestellingid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("bestellingid");
				java.sql.Date dat = rs.getDate("datum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				String isGeleverd = rs.getString("isGeleverd");
				Bestelling b = new Bestelling(id);
				b.setVerwachteDatum(datum);
				if(isGeleverd.equals("t")){
					b.setIsGeleverd(true);
				}
				terug = b;
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	//Class nieuweClass
	public Bestelling nieuweBestelling(){
		Bestelling terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "INSERT INTO Bestelling Bestelling (isGeleverd) VALUES ('f');";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			String sql2 = "SELECT MAX(bestellingid) FROM Bestelling";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int bestellingid = 0;
			while(rs.next()){
				bestellingid = rs.getInt(1);			
			}
			stmt2.close();
			con.close();
			terug = zoekBestelling(bestellingid);
=======
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;
import domeinklassen.Product;

public class ConnectDBBestelling extends ConnectDB{
	//maak connectie
	public ConnectDBBestelling(){
		super();
	}
	public Bestelling nieuwBestelling(Bestelling deBestelling){
		Bestelling terug = null;
		try{			
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			//maak een bestelling aan
			Calendar now = Calendar.getInstance();
			String sql1 = "INSERT INTO Bestelling (datum) VALUES (now);";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql1);
			stmt.close();
			//de zojuist gemaakt bestelling zoeken
			String sql2 = "SELECT MAX(bestellingid) as bestellingid FROM BESTELLING";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			rs.next();
			int bestellingid = rs.getInt("bestellingid");			
			stmt2.close();
			//de producten aan de bestelling toevoegen
			String sql3 = "INSERT INTO BesteldProduct (hoeveelheid, productid, bestellingid) VALUES ( ?, ?, " + bestellingid + ")";
			PreparedStatement pstmt = con.prepareStatement(sql3);
			for(BesteldProduct bp: deBestelling.getBesteldeProducten()){
				pstmt.setInt(1, bp.getHoeveelheid());
				pstmt.setInt(2, bp.getProduct().getArtikelNr());
				pstmt.executeUpdate();
			}
			pstmt.close();
			con.close();
>>>>>>> f0446b9d2f95ab35f0a862faebf5ea4e939c2215
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
<<<<<<< HEAD
	//boolean updateClass
	public boolean updateBestelling(Bestelling b){
		try{
			java.util.Date datum = b.getVerwachteDatum();
		    java.sql.Date dat = new java.sql.Date(datum.getTime());	
		    String isGeleverd = "f";
		    if(b.getIsGeleverd()){
		    	isGeleverd = "t";
		    }
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "UPDATE Bestelling SET datum='" + dat + "',  isGeleverd='" + isGeleverd + "' WHERE bestellingid = " + b.getBestelNummer();
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
	//boolean verwijderClass	
	public boolean verwijderBestelling(int bestellingid){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "DELETE FROM Bestelling WHERE bestellingid=" + bestellingid;
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
=======
>>>>>>> f0446b9d2f95ab35f0a862faebf5ea4e939c2215
}
