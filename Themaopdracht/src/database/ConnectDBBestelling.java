package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;

public class ConnectDBBestelling extends ConnectDB{
	//maak connectie
	public ConnectDBBestelling(){
		super();
	}
	
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
				String isGeleverd = rs.getString("isGeleverd");
				java.util.Date datum = new java.util.Date(dat.getTime());
				Bestelling b = new Bestelling(id);
				if(isGeleverd.equals("t")){
					b.setIsGeleverd(true);
				}
				if(datum != null){
					b.setVerwachteDatum(datum);
				}
				ConnectDBBesteldProduct bpconn = new ConnectDBBesteldProduct();
				b.setBesteldeProducten(bpconn.getProductenVanBestelling(id));
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
	
	public Bestelling zoekBestelling(int id){
		Bestelling terug = null;
		try{			
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Bestelling WHERE bestellingid=" + id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				java.sql.Date dat = rs.getDate("datum");
				String isGeleverd = rs.getString("isGeleverd");
				java.util.Date datum = new java.util.Date(dat.getTime());
				Bestelling b = new Bestelling(id);
				if(isGeleverd.equals("t")){
					b.setIsGeleverd(true);
				}
				if(datum != null){
					b.setVerwachteDatum(datum);
				}
				ConnectDBBesteldProduct bpconn = new ConnectDBBesteldProduct();
				b.setBesteldeProducten(bpconn.getProductenVanBestelling(id));
				terug = b;
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public Bestelling nieuwBestelling(Bestelling deBestelling){
		Bestelling terug = null;
		try{			
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			//maak een bestelling aan
			Calendar now = Calendar.getInstance();
			java.util.Date datum = now.getTime();
			java.sql.Date dat = new java.sql.Date(datum.getTime());
			String sql1 = "INSERT INTO Bestelling (datum) VALUES ('" + dat + "');";
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
			String sql3 = "INSERT INTO BesteldProduct (hoeveelheid, productid, bestellingid) VALUES (?, ?, " + bestellingid + ");";
			PreparedStatement pstmt = con.prepareStatement(sql3);
			for(BesteldProduct bp: deBestelling.getBesteldeProducten()){
				pstmt.setInt(1, bp.getHoeveelheid());
				pstmt.setInt(2, bp.getProduct().getArtikelNr());
				pstmt.executeUpdate();
			}
			pstmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
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
			//verwijder eerst alle producten van deze bestelling
			ConnectDBBesteldProduct bpconn = new ConnectDBBesteldProduct();
			ArrayList<BesteldProduct> deProducten = bpconn.getProductenVanBestelling(bestellingid);
			for(BesteldProduct bp : deProducten){
				bpconn.verwijderBesteldProduct(bp.getID());
			}
			//en vervolgens de bestelling zelf
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
}
