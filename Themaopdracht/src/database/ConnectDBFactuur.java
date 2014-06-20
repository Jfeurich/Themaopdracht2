package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Factuur;
import domeinklassen.Klus;
/**	
*	Dit is klasse ConnectDBFactuur.
*	Deze klasse haalt alle database informatie op in relatie met het object Auto.
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class ConnectDBFactuur{
	/**
	 * Variabele con, type Connection.
	 * Deze variabele wordt aangeroepen als er een SQL statement uigevoerd moet worden.
	 */
	private Connection con = null;
	/**
	 * Constructor ConnectDBFactuur.
	 * Dit is de constructor van de ConnectDBFactuur klasse.
	 * @param c		de connectie met de database wordt opgeslagen in de klasse
	 */
	public ConnectDBFactuur(Connection c){
		con = c;
	}
	
	//alle facturen die nog niet zijn betaald
	/**
	 * Methode getFacturenNietBetaald.
	 * Deze methode geeft de facturen terug die niet betaald zijn.
	 * @return terug	Een ArrayList met alle niet-betaalde facturen
	 */
	public ArrayList<Factuur> getFacturenNietBetaald(){
		ArrayList<Factuur> terug = new ArrayList<Factuur>();
		try{
			String sql = "SELECT * FROM Factuur WHERE isBetaald='f'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int factuurid = rs.getInt("factuurid");
				int klusid = rs.getInt("klusid");
				java.sql.Date dat = rs.getDate("aanmaakDatum");
				int kP = rs.getInt("kortingspercentage");
				java.util.Date datum = new java.util.Date(dat.getTime());
				Klus deKlus = null;
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
				f.setKortingsPercentage(kP);
				f.setID(factuurid);
				terug.add(f);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij niet-betaalde facturen ophalen " + ex);
		}
		return terug;
	}

	/**
	 * Methode getBetaaldeFacturenTussen.
	 * Deze methode geeft alle facturen terug die tussen de ingevoerde datums zitten.
	 * @param d1	De begindatum tussen welke gezocht wordt
	 * @param d2	De einddatum tussen welke gezocht wordt
	 * @return terug	Een ArrayList met alle facturen die tussen de ingevoerde datums zitten
	 */
	public ArrayList<Factuur> getBetaaldeFacturenTussen(java.util.Date d1, java.util.Date d2){
		ArrayList<Factuur> terug = new ArrayList<Factuur>();
		try{
			java.sql.Date start = new java.sql.Date(d1.getTime());
			java.sql.Date eind = new java.sql.Date(d2.getTime());
			String sql = "SELECT * FROM Factuur WHERE isBetaald='t' AND (betaalDatum BETWEEN '" + start + "' AND '" + eind + "')";
			System.out.println(sql);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int factuurid = rs.getInt("factuurid");
				int klusid = rs.getInt("klusid");
				int kP = rs.getInt("kortingspercentage");
				String bw = rs.getString("betalingswijze");
				java.sql.Date dat = rs.getDate("aanmaakDatum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				java.sql.Date dat2 = rs.getDate("betaalDatum");
				java.util.Date datum2 = new java.util.Date(dat2.getTime());
				Klus deKlus = null;
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
				f.setID(factuurid);
				f.setKortingsPercentage(kP);
				f.betaal(bw, datum2);
				terug.add(f);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij niet-betaalde facturen ophalen " + ex);
		}
		return terug;
	}
	
	/**
	 * Methode getFactuurVanKlus.
	 * Deze methode geeft de Factuur van de Klus met ingvoerde KlusID.
	 * @param klusid Het KlusID van de gezochte klus
	 * @return terug	Het Factuur-object van de gezochte klus. Als deze niet gevonden is wordt null gereturned
	 */
	public Factuur getFactuurVanKlus(int klusid){
		Factuur terug = null;
		try{
			String sql = "SELECT * FROM Factuur WHERE klusid=" + klusid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int factuurid = rs.getInt("factuurid");
				java.sql.Date dat = rs.getDate("aanmaakDatum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				Klus deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
				f.setID(factuurid);
				java.sql.Date dat2 = rs.getDate("betaalDatum");
				if(dat2 != null){
					java.util.Date betaalDatum = new java.util.Date(dat2.getTime());
					String betalingswijze = rs.getString("betalingswijze");
					int kP = rs.getInt("kortingspercentage");
					f.setKortingsPercentage(kP);
					f.betaal(betalingswijze, betaalDatum);
				}
				terug = f;
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij factuur van klus zoeken " + ex);
		}
		return terug;		
	}
	
	//zoek factuur (per id)
	/**
	 * Methode zoekFactuur.
	 * Deze methode zoekt de factuur van de ingevoerde FactuurID.
	 * @param factuurid	FactuurID van de gezochte factuur
	 * @return terug	Het Factuur-object van de gezochte FactuurID. Als deze niet is gevonden wordt null gereturned
	 */
	public Factuur zoekFactuur(int factuurid){
		Factuur terug = null;
		try{
			String sql = "SELECT * FROM Factuur WHERE factuurid=" + factuurid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int klusid = rs.getInt("klusid");
				java.sql.Date dat = rs.getDate("aanmaakDatum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				Klus deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
				f.setID(factuurid);
				java.sql.Date dat2 = rs.getDate("betaalDatum");
				if(dat2 != null){
					java.util.Date betaalDatum = new java.util.Date(dat2.getTime());
					String betalingswijze = rs.getString("betalingswijze");
					int kP = rs.getInt("kortingspercentage");
					f.setKortingsPercentage(kP);
					f.betaal(betalingswijze, betaalDatum);
				}
				terug = f;
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij factuur zoeken " + ex);
		}
		return terug;
	}
	
	//nieuwe factuur (per Klus)
	/**
	 * Methode nieuweFactuur.
	 * Deze methode maakt een nieuwe Factuur aan.
	 * Alle gegevens om dit te doen staan in het Klus-object.
	 * @param k De klus waarvan het nieuwe Factuur-object is
	 * @return terug Het nieuwe Factuur-object wordt gereturned. Als dit niet is gelukt wordt null gereturned.
	 */
	public Factuur nieuweFactuur(Klus k){
		Factuur terug = null;
		java.util.Date vandaag = new java.util.Date();
		java.sql.Date datum = new java.sql.Date(vandaag.getTime());
		try{
			String sql = "INSERT INTO Factuur (aanmaakDatum, klusid, isBetaald) VALUES ('" + datum + "', '" + k.getID() + "', 'f');";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			String sql2 = "SELECT MAX(factuurid) FROM Factuur";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int factuurid = 0;
			while(rs.next()){
				factuurid = rs.getInt(1);			
			}
			stmt2.close();
			terug = zoekFactuur(factuurid);
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuwe factuur " + ex);
		}
		return terug;
	}
	
	//zet alle waardes van factuur in database naar die van ingevoerde factuur-object
	/**
	 * Methode updateFactuur.
	 * Deze methode werkt een ingevoerde factuur bij.
	 * @param f Het ingevoerde Factuur-object dat bijgewerkt moet worden
	 * @return boolean	Er wordt aangegeven of het bijwerken is gelukt
	 */
	public boolean updateFactuur(Factuur f){
		try{
			String sql = "";
			java.util.Date aanmaak = f.getAanmaakDatum();
			java.sql.Date aD = new java.sql.Date(aanmaak.getTime());
			String isBetaald = "f";
			if(f.getIsBetaald()){
				isBetaald = "t";
				java.util.Date betaal = f.getBetaalDatum();
				java.sql.Date bD = new java.sql.Date(betaal.getTime());
				sql = "UPDATE Factuur SET aanmaakDatum='" + aD + "',  betaalDatum='" + bD + 
						"', betalingswijze='" + f.getBetaalwijze() + "', kortingspercentage=" + f.getKorting() + 
						", isBetaald='" + isBetaald + "' WHERE factuurid = " + f.getID();
			}
			else{
				sql = "UPDATE Factuur SET aanmaakDatum='" + aD + "', betalingswijze='" + "', kortingspercentage=" + 
						f.getKorting() + ", isBetaald='" + isBetaald + "' WHERE factuurid = " + f.getID();
			}
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij factuur updaten" + ex);
		}
		return false;
	}
}
