package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Auto;
import domeinklassen.Herinneringsbrief;
import domeinklassen.Klant;

public class ConnectDBKlant{
	
	private Connection con;
	//maak connectie
	public ConnectDBKlant(Connection c){
		con = c;
	}

	//alle klanten in de database
	public ArrayList<Klant> getKlanten(){
		ArrayList<Klant> terug = new ArrayList<Klant>();
		try{
			String sql = "SELECT * FROM Klant WHERE actief='t'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int kn = rs.getInt("klantid");
				String nm = rs.getString("naam");
				String adr = rs.getString("adres");
				String wp = rs.getString("plaats");
				String rnr = rs.getString("rekeningnummer");
				int nr = rs.getInt("telefoonnummer");
				Klant k = new Klant(kn, nm, adr, wp, rnr, nr);
				ConnectDBAuto autocon = new ConnectDBAuto(con);
				autocon.getAutosVan(k);
				ConnectDBHerinneringsbrief hcon = new ConnectDBHerinneringsbrief(con);
				hcon.getBrievenVan(k);
				ConnectDBUser usercon = new ConnectDBUser(con);
				usercon.zoekUserVanKlant(k);
				terug.add(k);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij klanten ophalen " + ex);
		}
		return terug;
	}
	
	//zoek klant op klantnummer
	public Klant zoekKlant(int klantnummer){
		Klant terug = null;
		try{
			String sql = "SELECT * FROM Klant WHERE klantid=" + klantnummer;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String nm = rs.getString("naam");
				String adr = rs.getString("adres");
				String wp = rs.getString("plaats");
				String rnr = rs.getString("rekeningnummer");
				int nr = rs.getInt("telefoonnummer");
				terug = new Klant(klantnummer, nm, adr, wp, rnr, nr);
				ConnectDBAuto autocon = new ConnectDBAuto(con);
				autocon.getAutosVan(terug);
				ConnectDBHerinneringsbrief hcon = new ConnectDBHerinneringsbrief(con);
				hcon.getBrievenVan(terug);
				ConnectDBUser usercon = new ConnectDBUser(con);
				usercon.zoekUserVanKlant(terug);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij klant zoeken" + ex);
		}
		return terug;		
	}
	
	//maakt nieuwe klant.  id wordt automatisch toegewezen. geeft klant-object terug zodat je het id weet.
	public Klant nieuweKlant(String nm, String adr, String wp, String rnr, int nr){
		Klant terug = null;
		try{			
			//maak nieuw product met gegeven waarden
			String sql = "INSERT INTO Klant (naam, adres, plaats, rekeningnummer, telefoonnummer, actief) VALUES ('" + nm + "', '" + adr + "', '" + wp + "', '" + rnr + "', '" + nr +  "', 't');";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//zoek meest recente klant (hoogste id) momenteel in database (dat is degene die we net toe hebben gevoegd)
			String sql2 = "SELECT MAX(klantid) FROM Klant";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int klantid = 0;
			while(rs.next()){
				klantid = rs.getInt(1);		
			}
			stmt2.close();
			//zoek klant op basis van gevonden klantnummer
			terug = zoekKlant(klantid);	
			ConnectDBAuto autocon = new ConnectDBAuto(con);
			autocon.getAutosVan(terug);
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuwe klant " + ex);
		}
		return terug;
	}
	
	//wijzigt klant in database naar alle waarden van ingevoerde klant-object (exclusief het id)
	public boolean updateKlant(Klant k){
		try{
			String sql = "UPDATE Klant SET naam='" + k.getNaam() + "',  adres='" + k.getAdres() + 
					"', plaats='" + k.getPlaats() + "', telefoonnummer=" + k.getTelefoonnummer() + ", rekeningnummer='" + 
					k.getRekeningnummer() + "' WHERE klantid = " + k.getKlantnummer();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij klant updaten " + ex);
		}
		return false;
	}
	
	//zet klant op non-actief
	public boolean verwijderKlant(int klantid){
		try{
			String sql = "UPDATE Klant SET actief='f' WHERE klantid=" + klantid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij klant verwijderen " + ex);
		}
		return false;
	}
}
