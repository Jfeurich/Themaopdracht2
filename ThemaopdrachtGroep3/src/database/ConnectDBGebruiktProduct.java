package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.GebruiktProduct;

public class ConnectDBGebruiktProduct{
	
	private Connection con = null;
	
	public ConnectDBGebruiktProduct(Connection c){
		con = c;
	}
	
	public ArrayList<GebruiktProduct> getGebruikteProducten(){
		ArrayList<GebruiktProduct> terug = new ArrayList<GebruiktProduct>();
		try{
			String sql = "SELECT * FROM GebruiktProduct";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int gpid = rs.getInt("gebruiktproductid");
				int pid = rs.getInt("productid");
				int a = rs.getInt("aantal");
				GebruiktProduct gp = new GebruiktProduct(gpid, a);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				gp.setHetProduct(pconn.zoekProduct(pid));
				terug.add(gp);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij gebruikte producten ophalen " + ex);
		}
		return terug;
	}
	
	public ArrayList<GebruiktProduct> getProductenVanKlus(int klusid){
		ArrayList<GebruiktProduct> terug = new ArrayList<GebruiktProduct>();
		try{
			String sql = "SELECT * FROM GebruiktProduct WHERE klusid=" + klusid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int gpid = rs.getInt("gebruiktproductid");
				int pid = rs.getInt("productid");
				int a = rs.getInt("aantal");
				GebruiktProduct gp = new GebruiktProduct(gpid, a);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				gp.setHetProduct(pconn.zoekProduct(pid));
				terug.add(gp);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij producten van klus ophalen " + ex);
		}
		return terug;
	}
	
	public ArrayList<GebruiktProduct> getGebruikVanProduct(int pid){
		ArrayList<GebruiktProduct> terug = new ArrayList<GebruiktProduct>();
		try{
			String sql = "SELECT * FROM GebruiktProduct WHERE productid=" + pid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int bpid = rs.getInt("besteldproductid");
				int a = rs.getInt("aantal");
				GebruiktProduct bp = new GebruiktProduct(bpid, a);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				bp.setHetProduct(pconn.zoekProduct(pid));
				terug.add(bp);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij klussen met gebruiktproduct ophalen" + ex);
		}
		return terug;		
	}	
	
	public GebruiktProduct zoekGebruiktProduct(int gpid){
		GebruiktProduct terug = null;
		int pid = 0;
		try{
			String sql = "SELECT * FROM GebruiktProduct WHERE gebruiktproductid=" + gpid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				pid = rs.getInt("productid");
				int a = rs.getInt("aantal");
				terug = new GebruiktProduct(gpid, a);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				terug.setHetProduct(pconn.zoekProduct(pid));
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij gebruiktproduct zoeken " + ex);
		}
		return terug;
	}
	
	public GebruiktProduct nieuwGebruiktProduct(int klusid, int productid, int aantal){
		GebruiktProduct terug = null;
		try{			
			//maak nieuw product met gegeven waarden
			String sql = "INSERT INTO GebruiktProduct (klusid, productid, aantal) VALUES (" + klusid + ", " + productid + ", " + aantal + ");";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			String sql2 = "SELECT MAX(gebruiktproductid) FROM GebruiktProduct";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int gpid = 0;
			while(rs.next()){
				gpid = rs.getInt(1);		
			}
			stmt2.close();
			terug = zoekGebruiktProduct(gpid);	
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuw gebruiktproduct" + ex);
		}
		return terug;
	}
	
	public boolean updateGebruiktProduct(GebruiktProduct gp){
		try{
			String sql = "UPDATE GebruiktProduct SET aantal=" + gp.getAantal() + " WHERE gebruiktproductid = " + gp.getID();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij gebruiktproduct updaten " + ex);
		}
		return false;
	}
	
	public boolean verwijderGebruiktProduct(int gpid){
		try{
			String sql = "DELETE FROM GebruiktProduct WHERE gebruiktproductid=" + gpid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij gebruiktproduct verwijderen" + ex);
		}
		return false;
	}
}
