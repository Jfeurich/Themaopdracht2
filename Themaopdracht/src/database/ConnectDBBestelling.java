package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;

public class ConnectDBBestelling{
	
	private Connection con = null;
	//maak connectie
	public ConnectDBBestelling(Connection c){
		con = c;
	}
	
	//alle geleverde bestellingen in het systeem
	public ArrayList<Bestelling> getBestellingenGeleverd(){
		ArrayList<Bestelling> terug = new ArrayList<Bestelling>();
		try{			
			String sql = "SELECT * FROM Bestelling WHERE isGeleverd='t'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("bestellingid");
				Bestelling b = new Bestelling(id);
				b.setIsGeleverd(true);
				java.sql.Date dat = rs.getDate("datum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				b.setVerwachteDatum(datum);
				ConnectDBBesteldProduct bpconn = new ConnectDBBesteldProduct(con);
				bpconn.getProductenVanBestelling(b);
				terug.add(b);
			}
		}
		catch(Exception ex){
			System.out.println("Probleem bij geleverde bestellingen ophalen" + ex);
		}		
		return terug;
	}
	
	//alle bestellingen in het systeem die nog NIET zijn geleverd
	public ArrayList<Bestelling> getBestellingenNietGeleverd(){
		ArrayList<Bestelling> terug = new ArrayList<Bestelling>();
		try{			
			String sql = "SELECT * FROM Bestelling WHERE isGeleverd='f'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("bestellingid");
				java.sql.Date dat = rs.getDate("datum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				Bestelling b = new Bestelling(id);
				if(datum != null){
					b.setVerwachteDatum(datum);
				}
				ConnectDBBesteldProduct bpconn = new ConnectDBBesteldProduct(con);
				bpconn.getProductenVanBestelling(b);
				terug.add(b);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij niet-geleverde bestellingen ophalen" + ex);
		}		
		return terug;
	}
	
	//get bestellingen met een bepaald product erin (per productid)
	public ArrayList<Bestelling> getBestellingenVanProduct(int pid){
		ArrayList<Bestelling> terug = new ArrayList<Bestelling>();
		ArrayList<Integer> bestellingids = new ArrayList<Integer>();
		try{
			String sql = "SELECT * FROM BesteldProduct WHERE productid=" + pid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				bestellingids.add(rs.getInt("bestellingid"));
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij bestellingen per product ophalen " + ex);
		}
		for(int i : bestellingids){
			terug.add(zoekBestelling(i));
		}
		return terug;		
	}
	
	//get bestellingen met een bepaalde datum
	public ArrayList<Bestelling> getBestellingenOpDatum(java.util.Date dat){
		ArrayList<Bestelling> terug = new ArrayList<Bestelling>();
		try{
			java.sql.Date datum = new java.sql.Date(dat.getTime());
			String sql = "SELECT * FROM Bestelling WHERE datum=" + datum;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("bestellingid");
				String isGeleverd = rs.getString("isGeleverd");
				Bestelling b = new Bestelling(id);
				if(isGeleverd.equals("t")){
					b.setIsGeleverd(true);
				}
				b.setVerwachteDatum(dat);
				ConnectDBBesteldProduct bpconn = new ConnectDBBesteldProduct(con);
				bpconn.getProductenVanBestelling(b);
				terug.add(b);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij bestellingen per product ophalen " + ex);
		}
		return terug;		
	}
	
	//zoek bestelling (per id)
	public Bestelling zoekBestelling(int id){
		Bestelling terug = null;
		try{			
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
				ConnectDBBesteldProduct bpconn = new ConnectDBBesteldProduct(con);
				bpconn.getProductenVanBestelling(b);
				terug = b;
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij bestelling zoeken" + ex);
		}
		return terug;
	}
	
	//maak nieuwe bestelling (per Bestelling-object)
	public Bestelling nieuwBestelling(Bestelling deBestelling){
		Bestelling terug = deBestelling;
		try{			
			//maak een bestelling aan
			Calendar now = Calendar.getInstance();
			java.util.Date datum = now.getTime();
			java.sql.Date dat = new java.sql.Date(datum.getTime());
			String sql1 = "INSERT INTO Bestelling (datum, isGeleverd) VALUES ('" + dat + "', 'f');";
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
			terug.setBestelNummer(bestellingid);
			//BesteldeProducten aanmaken
			String sql3 = "INSERT INTO BesteldProduct (hoeveelheid, productid, bestellingid) VALUES (?, ?, " + bestellingid + ");";
			PreparedStatement pstmt = con.prepareStatement(sql3);
			for(BesteldProduct bp: deBestelling.getBesteldeProducten()){
				pstmt.setInt(1, bp.getHoeveelheid());
				pstmt.setInt(2, bp.getProduct().getArtikelNr());
				pstmt.executeUpdate();
			}
			pstmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuwe bestelling maken" + ex);
		}
		return terug;
	}
	//boolean updateClass
	public boolean updateBestelling(Bestelling b){
		try{
			java.util.Date datum = b.getVerwachteDatum();
		    java.sql.Date dat = new java.sql.Date(datum.getTime());	
			String sql = "UPDATE Bestelling SET datum='" + dat + "',  isGeleverd='t' WHERE bestellingid = " + b.getBestelNummer();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij bestelling updaten" + ex);
		}
		return false;
	}
}
