package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.Product;

public class ConnectDBAuto {
	final static String DB_DRIV = "com.mysql.jdbc.Driver";
	private String databaseURL = "jdbc:mysql://localhost:3306/ThemaopdrachtDB";
	
	//maak connectie
	public ConnectDBAuto(){
		try{
			Class.forName(DB_DRIV).newInstance();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	public ArrayList<Auto> getAutos(){
		ArrayList<Auto> terug = new ArrayList<Auto>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Auto";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant();
				Klant eigenaar = klantconn.zoekKlant(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(id);
				terug.add(a);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}

	//zoek naar alle autos van een bepaalde klant (per klantid)
	public ArrayList<Auto> getAutosVan(int zoekid){
		ArrayList<Auto> terug = new ArrayList<Auto>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Auto WHERE klantid=" + zoekid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				ConnectDBKlant klantconn = new ConnectDBKlant();
				Klant eigenaar = klantconn.zoekKlant(zoekid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(id);
				terug.add(a);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public Auto zoekAuto(int autoid){
		Auto terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Auto WHERE autoid=" + autoid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant();
				Klant eigenaar = klantconn.zoekKlant(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(id);
				terug = a;
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