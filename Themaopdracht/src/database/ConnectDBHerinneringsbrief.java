package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Herinneringsbrief;
import domeinklassen.Klant;

public class ConnectDBHerinneringsbrief extends ConnectDB{
	
	//maak connectie
	public ConnectDBHerinneringsbrief(){
		super();
	}
	
	//alle herinneringsbrieven in het systeem
	public ArrayList<Herinneringsbrief> getBrieven(){
		ArrayList<Herinneringsbrief> terug = new ArrayList<Herinneringsbrief>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Herinneringsbrief";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("herinneringsbriefid");
				String reden = rs.getString("reden");
				java.sql.Date dat = rs.getDate("datum");
				java.util.Date datum = new Date(dat.getTime());
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant();
				Klant deKlant = klantconn.zoekKlant(klantid);
				Herinneringsbrief h = new Herinneringsbrief(id, deKlant, reden, datum);
				terug.add(h);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}

	//zoek naar alle brieven van een bepaalde klant (per klantid)
	public ArrayList<Herinneringsbrief> getBrievenVan(int zoekid){
		ArrayList<Herinneringsbrief> terug = new ArrayList<Herinneringsbrief>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Herinneringsbrief WHERE klantid=" + zoekid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("herinneringsbriefid");
				String reden = rs.getString("reden");
				java.sql.Date dat = rs.getDate("datum");
				java.util.Date datum = new Date(dat.getTime());
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant();
				Klant deKlant = klantconn.zoekKlant(klantid);
				Herinneringsbrief h = new Herinneringsbrief(id, deKlant, reden, datum);
				terug.add(h);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//zoek brief op id
	public Herinneringsbrief zoekBrief(int id){
		Herinneringsbrief terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Herinneringsbrief WHERE herinneringsbriefid=" + id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String reden = rs.getString("reden");
				java.sql.Date dat = rs.getDate("datum");
				java.util.Date datum = new Date(dat.getTime());
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant();
				Klant deKlant = klantconn.zoekKlant(klantid);
				Herinneringsbrief h = new Herinneringsbrief(id, deKlant, reden, datum);
				terug = h;
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;		
	}
	
	//maak nieuwe Herinneringsbrief. id wordt automatisch toegewezen. geeft Herinneringsbrief-object terug zodat je het id weet.
	public Herinneringsbrief nieuweBrief(Klant deKlant, String reden){
		Herinneringsbrief terug = null;
		try{
			java.util.Date dat = new java.util.Date();
			java.sql.Date datum = new java.sql.Date(dat.getTime());
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "INSERT INTO Herinneringsbrief (klantid, reden, datum) VALUES (" + deKlant.getKlantnummer() + ", '" + reden + "', '" + datum + "');";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			String sql2 = "SELECT MAX(herinneringsbriefid) FROM Herinneringsbrief";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int id = 0;
			while(rs.next()){
				id = rs.getInt(1);		
			}
			stmt2.close();
			con.close();
			terug = zoekBrief(id);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}

	//zet alle waardes van brief in database naar waardes van ingevoerd Herinneringsbrief-object. met uitzondering van id. 
	public boolean updateBrief(Herinneringsbrief h){
		try{
			java.util.Date dat = h.getDatum();
			java.sql.Date datum = new java.sql.Date(dat.getTime());
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "UPDATE Herinneringsbrief SET reden='" + h.getReden() + "',  datum='" + datum + 
					"', klantid=" + h.getDeKlant().getKlantnummer() + " WHERE herinneringsbriefid= " + h.getID();
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
	
	//verwijderd tabelrij met ingevoerd id
	public boolean verwijderBrief(int id){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "DELETE FROM Herinneringsbrief WHERE herinneringsbriefid=" + id;
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
