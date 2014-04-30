package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.BesteldProduct;
import domeinklassen.Klant;

public class ConnectDBBesteldProduct extends ConnectDB {
	
	public ArrayList<BesteldProduct> getBesteldeProducten(){
		ArrayList<BesteldProduct> terug = new ArrayList<BesteldProduct>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM BesteldProduct";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int bpid = rs.getInt("besteldproductid");
				int pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				BesteldProduct bp = new BesteldProduct(bpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct();
				bp.setProduct(pconn.zoekProduct(pid));
				terug.add(bp);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public ArrayList<BesteldProduct> getProductenVanBestelling(int bestellingid){
		ArrayList<BesteldProduct> terug = new ArrayList<BesteldProduct>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM BesteldProduct WHERE bestellingid=" + bestellingid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int bpid = rs.getInt("besteldproductid");
				int pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				BesteldProduct bp = new BesteldProduct(bpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct();
				bp.setProduct(pconn.zoekProduct(pid));
				terug.add(bp);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public ArrayList<BesteldProduct> getBestellingenVanProduct(int pid){
		ArrayList<BesteldProduct> terug = new ArrayList<BesteldProduct>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM BesteldProduct WHERE productid=" + pid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int bpid = rs.getInt("besteldproductid");
				int h = rs.getInt("hoeveelheid");
				BesteldProduct bp = new BesteldProduct(bpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct();
				bp.setProduct(pconn.zoekProduct(pid));
				terug.add(bp);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;		
	}
	
	public BesteldProduct zoekBesteldProduct(int bpid){
		BesteldProduct terug = null;
		int pid = 0;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM BesteldProduct WHERE besteldproductid=" + bpid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				terug = new BesteldProduct(bpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct();
				terug.setProduct(pconn.zoekProduct(pid));
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public BesteldProduct nieuwBesteldProduct(int bestellingid, int productid, int hoeveelheid){
		BesteldProduct terug = null;
		try{			
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			//maak nieuw product met gegeven waarden
			String sql = "INSERT INTO BesteldProduct (bestellingid, productid, hoeveelheid) VALUES (" + bestellingid + ", " + productid + ", " + hoeveelheid + ");";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//zoek meest recente product (hoogste id) momenteel in database (dat is degene die we net toe hebben gevoegd)
			String sql2 = "SELECT MAX(besteldproductid) FROM BesteldProduct";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int bpid = 0;
			while(rs.next()){
				bpid = rs.getInt(1);		
			}
			stmt2.close();
			con.close();
			//zoek product op basis van gevonden klantnummer
			terug = zoekBesteldProduct(bpid);	
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public boolean updateBesteldProduct(BesteldProduct bp){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "UPDATE BesteldProduct SET hoeveelheid=" + bp.getHoeveelheid() + " WHERE besteldproductid = " + bp.getID();
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
	
	public boolean verwijderBesteldProduct(int bpid){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "DELETE FROM BesteldProduct WHERE besteldproductid=" + bpid;
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
