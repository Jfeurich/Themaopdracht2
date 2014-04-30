package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import domeinklassen.Klant;

public class ConnectDBKlant extends ConnectDB{
	
	//maak connectie
	public ConnectDBKlant(){
		super();
	}

	//alle klanten in de database
	public ArrayList<Klant> getKlanten(){
		ArrayList<Klant> terug = new ArrayList<Klant>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Klant";
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
				terug.add(k);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//zoek klant op klantnummer
	public Klant zoekKlant(int klantnummer){
		Klant terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Klant WHERE klantid=" + klantnummer;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int kn = rs.getInt("klantid");
				String nm = rs.getString("naam");
				String adr = rs.getString("adres");
				String wp = rs.getString("plaats");
				String rnr = rs.getString("rekeningnummer");
				int nr = rs.getInt("telefoonnummer");
				terug = new Klant(kn, nm, adr, wp, rnr, nr);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;		
	}
	
	//maakt nieuwe klant.  id wordt automatisch toegewezen. geeft klant-object terug zodat je het id weet.
	public Klant nieuweKlant(String nm, String adr, String wp, String rnr, int nr){
		Klant terug = null;
		try{			
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			//maak nieuw product met gegeven waarden
			String sql = "INSERT INTO Klant (naam, adres, plaats, telefoonnummer, rekeningnummer) VALUES ('" + nm + "', '" + adr + "', '" + wp + "', '" + rnr + "', " + nr + ");";
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
			con.close();
			//zoek klant op basis van gevonden klantnummer
			terug = zoekKlant(klantid);	
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//wijzigt klant in database naar alle waarden van ingevoerde klant-object (exclusief het id)
	public boolean updateKlant(Klant k){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "UPDATE Klant SET naam='" + k.getNaam() + "',  adres='" + k.getAdres() + 
					"', plaats='" + k.getPlaats() + "', telefoonnummer=" + k.getTelefoonnummer() + ", ' rekeningnummer='" + 
					k.getRekeningnummer() + "' WHERE klantid = " + k.getKlantnummer();
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
	
	//delete tabelrij met ingevoerd klantid
	public boolean verwijderKlant(int klantid){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "DELETE FROM Klant WHERE klantid=" + klantid;
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
