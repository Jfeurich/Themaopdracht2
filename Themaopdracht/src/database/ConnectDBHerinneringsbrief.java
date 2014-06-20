package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Herinneringsbrief;
import domeinklassen.Klant;
/**	
*	Dit is klasse ConnectDBHerinneringsBrief.
*	Deze klasse haalt alle database informatie op in relatie met het object Auto.
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class ConnectDBHerinneringsbrief{
	/**
	 * Variabele con, type Connection.
	 * Deze variabele wordt aangeroepen als er een SQL statement uigevoerd moet worden.
	 */
	private Connection con = null;
	//maak connectie
	/**
	 * Constructor ConnectDBHerinneringsBrief.
	 * Dit is de constructor van de ConnectDBHerinneringsBrief klasse.
	 * @param c		de connectie met de database wordt opgeslagen in de klasse
	 */
	public ConnectDBHerinneringsbrief(Connection c){
		con = c;
	}

	//zoek naar alle brieven van een bepaalde klant 
	/**
	 * Methode getBrievenVan.
	 * Deze methode geeft alle brieven van het gezochte Klant-object.
	 * @param k	Het Klant-object van de gezochte klant
	 * @return terug	Een ArrayList met alle brieven van de gezochte klant
	 */
	public ArrayList<Herinneringsbrief> getBrievenVan(Klant k){
		ArrayList<Herinneringsbrief> terug = new ArrayList<Herinneringsbrief>();
		try{
			int klantid = k.getKlantnummer();
			String sql = "SELECT * FROM Herinneringsbrief WHERE klantid=" + klantid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("herinneringsbriefid");
				String reden = rs.getString("reden");
				java.sql.Date dat = rs.getDate("datum");
				java.util.Date datum = new Date(dat.getTime());
				Herinneringsbrief h = new Herinneringsbrief(id, k, reden, datum);
				terug.add(h);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij brieven van klant ophalen " + ex);
		}
		return terug;
	}
	
	//zoek brief op id
	/**
	 * Methode zoekBrief.
	 * Deze methode zoekt de brief met het ingevoerde BriefID.
	 * @param id	BriefID van het gezochte Herinneringsbrief-object
	 * @return terug	Het Herinneringsbrief-object van de gezochte BriefID. Als deze niet gevonden is wordt null gereturned
	 */
	public Herinneringsbrief zoekBrief(int id){
		Herinneringsbrief terug = null;
		try{
			String sql = "SELECT * FROM Herinneringsbrief WHERE herinneringsbriefid=" + id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String reden = rs.getString("reden");
				java.sql.Date dat = rs.getDate("datum");
				java.util.Date datum = new Date(dat.getTime());
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant deKlant = klantconn.zoekKlant(klantid);
				Herinneringsbrief h = new Herinneringsbrief(id, deKlant, reden, datum);
				terug = h;
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij brief zoeken " + ex);
		}
		return terug;		
	}
	
	//maak nieuwe Herinneringsbrief. id wordt automatisch toegewezen. geeft Herinneringsbrief-object terug zodat je het id weet.
	/**
	 * Methode nieuweBrief.
	 * Deze methode maakt een nieuwe brief aan .
	 * @param klantid	KlantID van de klant waarvoor de brief aangemaakt wordt
	 * @param reden	De reden van de herinneringsbrief
	 * @return	terug	Het Herinneringsbrief-object van de nieuwe brief. Als dit niet gelukt is wordt er null gereturned
	 */
	public Herinneringsbrief nieuweBrief(int klantid, String reden){
		Herinneringsbrief terug = null;
		try{
			java.util.Date dat = new java.util.Date();
			java.sql.Date datum = new java.sql.Date(dat.getTime());
			String sql = "INSERT INTO Herinneringsbrief (klantid, reden, datum) VALUES (" + klantid + ", '" + reden + "', '" + datum + "');";
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
			terug = zoekBrief(id);
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuwe brief " + ex);
		}
		return terug;
	}
}
