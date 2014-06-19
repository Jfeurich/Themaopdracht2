package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;

public class ConnectDBBesteldProduct{
	
	private Connection con = null;
	
	public ConnectDBBesteldProduct(Connection c){
		con = c;;
	}
	
	//alle producten van een bestelling
	public void getProductenVanBestelling(Bestelling b){
		ArrayList<BesteldProduct> terug = new ArrayList<BesteldProduct>();
		try{
			String sql = "SELECT * FROM BesteldProduct WHERE bestellingid=" + b.getBestelNummer();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int bpid = rs.getInt("besteldproductid");
				int pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				BesteldProduct bp = new BesteldProduct(bpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				bp.setProduct(pconn.zoekProduct(pid));
				terug.add(bp);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij producten van bestelling ophalen " + ex);
		}
		b.setBesteldeProducten(terug);
	}
	
	//zoek specifiek besteld product
	public BesteldProduct zoekBesteldProduct(int bpid){
		BesteldProduct terug = null;
		int pid = 0;
		try{
			String sql = "SELECT * FROM BesteldProduct WHERE besteldproductid=" + bpid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				terug = new BesteldProduct(bpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				terug.setProduct(pconn.zoekProduct(pid));
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij besteldproduct zoeken " + ex);
		}
		return terug;
	}
	
	//maak besteldproduct aan
	public BesteldProduct nieuwBesteldProduct(int bestellingid, int productid, int hoeveelheid){
		BesteldProduct terug = null;
		try{			
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
			//zoek product op basis van gevonden klantnummer
			terug = zoekBesteldProduct(bpid);	
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuw besteldproduct" + ex);
		}
		return terug;
	}
}
