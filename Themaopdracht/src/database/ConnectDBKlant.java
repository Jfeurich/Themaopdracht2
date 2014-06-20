package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Klant;

/**	
*	Dit is klasse ConnectDBKlant
*	Deze klasse is verantwoordelijk voor het communiceren met en beheren van de tabel Klant in de database
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/

public class ConnectDBKlant{
	
	/**
	*   Variabele con, type Connection
	*	Hier wordt de verbinding met de database opgeslagen
	*	Deze fungeert als brug tussen de java code en de MySQL database
	**/
	private Connection con;
	
	/**
	*	Constructor ConnectDBKlant
	*	Dit is de constructor voor ConnectDBKlant
	*	@param c	Connection met de database
	**/
	public ConnectDBKlant(Connection c){
		con = c;
	}

	/**
	*	Methode getKlanten
	*	Haalt alle regels op uit tabel Klant waar actief 't' is
	*	@return ArrayList<Klant>
	**/	
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

	/**
	*	Methode zoekKlant
	*	Haalt de regel uit de tabel waar het klantid overeen komt met het gegeven nummer, maakt hier een Klant-object van, en geeft dit terug.
	*	@return Klant
	**/	
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

	/**
	*	Methode zoekKlant
	*	Voor gebruik door ConnectDBAuto. Het Klant-object wat hier mee terug wordt gegeven heeft geen aangesloten Auto-objecten.
	*	Haalt de regel uit de tabel waar het klantid overeen komt met het gegeven nummer, maakt hier een Klant-object van, en geeft dit terug.
	*	@return Klant
	**/	
	//zoek klant op klantnummer
	public Klant zoekEigenaar(int klantnummer){
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
	
	/**
	*	Methode nieuweKlant
	*	Voegt een regel toe met de ingevoerde gegevens, zoekt de hoogste Primary Key in Klant, en roept zoekKlant aan met dit nummer.
	*	@param	nm	String De naam van de nieuwe Klant.
	*	@param	adr	String Het adres van de nieuwe Klant.
	*	@param	wp	String De woonplaats van de nieuwe Klant.
	*	@param	rnr	String	Het rekeningnummer van de nieuwe Klant (String ivm introductie van IBAN).
	*	@param	nr	String	Het telefoonnummer van de nieuwe Klant.
	*	@return Klant
	**/		
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
	
	/**
	*	Methode updateKlant
	*	Haalt de regel uit de tabel waar het klantid overeen komt met het id van de gegeven Klant en wijzig de gegevens naar die van de Klant.
	*	@param	k	Klant	De klant waarvoor de tabel moet worden geupdate.
	*	@return boolean
	**/		
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
	
	/**
	*	Methode verwijderKlant
	*	Haalt de regel uit de tabel waar het klantid overeen komt met het geven nummer en zet actief op 'f'.
	*	@param	klantid	int	Het klantid van de klant die op non-actief wordt gezet.
	*	@return boolean
	**/		
	//zet klant op non-actief
	public boolean verwijderKlant(int klantid){
		try{
			ConnectDBAuto acon = new ConnectDBAuto(con);
			acon.verwijderAutosVan(klantid);
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
	
	/**
	*	Methode activeerKlant
	*	Haalt de regel uit de tabel waar het klantid overeen komt met het geven nummer en zet actief op 't'.
	*	@param	klantid	int	Het klantid van de klant die op actief wordt gezet.
	*	@return boolean
	**/	
	//zet klant op actief
	public boolean activeerKlant(int klantid){
		try{
			ConnectDBAuto acon = new ConnectDBAuto(con);
			acon.activeerAutosVan(klantid);
			String sql = "UPDATE Klant SET actief='t' WHERE klantid=" + klantid;
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
