package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import domeinklassen.Auto;
import domeinklassen.Factuur;
import domeinklassen.Klus;

public class ConnectDBFactuur extends ConnectDB {

	public ConnectDBFactuur(){
		super();
	}
	
	//alle facturen in het systeem
	public ArrayList<Factuur> getFacturen(){
		ArrayList<Factuur> terug = new ArrayList<Factuur>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Factuur";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int factuurid = rs.getInt("factuurid");
				int klusid = rs.getInt("klusid");
				java.sql.Date dat = rs.getDate("aanmaakDatum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				Klus deKlus = null;
				//NOG CHECKEN OF DIT KAN (2 connecties tegelijkertijd??
				ConnectDBKlus klusconn = new ConnectDBKlus();
				deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
				f.setID(factuurid);
				//checks of er meer info in de factuur staat (betaald etc)
				terug.add(f);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//alle facturen aangemaakt tussen begindatum en einddatum
	public ArrayList<Factuur> getFacturenTussen(java.util.Date begindat, java.util.Date einddat){
		ArrayList<Factuur> terug = new ArrayList<Factuur>();
		try{
			java.sql.Date begindatum = new java.sql.Date(begindat.getTime());
			java.sql.Date einddatum = new java.sql.Date(einddat.getTime());
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Factuur WHERE (aanmaakDatum BETWEEN '" + begindatum + "' AND '" + einddatum + "')";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int factuurid = rs.getInt("factuurid");
				int klusid = rs.getInt("klusid");
				java.sql.Date dat = rs.getDate("aanmaakDatum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				Klus deKlus = null;
				//NOG CHECKEN OF DIT KAN (2 connecties tegelijkertijd??
				ConnectDBKlus klusconn = new ConnectDBKlus();
				deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
				f.setID(factuurid);
				terug.add(f);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//alle facturen die nog niet zijn betaald
	public ArrayList<Factuur> getFacturenNietBetaald(){
		ArrayList<Factuur> terug = new ArrayList<Factuur>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Factuur WHERE isBetaald='f'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int factuurid = rs.getInt("factuurid");
				int klusid = rs.getInt("klusid");
				java.sql.Date dat = rs.getDate("aanmaakDatum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				Klus deKlus = null;
				//NOG CHECKEN OF DIT KAN (2 connecties tegelijkertijd??
				ConnectDBKlus klusconn = new ConnectDBKlus();
				deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
				f.setID(factuurid);
				terug.add(f);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	public Factuur getFactuurVanKlus(int klusid){
		Factuur terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Factuur WHERE klusid=" + klusid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int factuurid = rs.getInt("factuurid");
				java.sql.Date dat = rs.getDate("aanmaakDatum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				ConnectDBKlus klusconn = new ConnectDBKlus();
				Klus deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
				f.setID(factuurid);
				//try voor extra waardes bij betaald
				terug = f;
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;		
	}
	
	//zoek factuur (per id)
	public Factuur zoekFactuur(int factuurid){
		Factuur terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Factuur WHERE factuurid=" + factuurid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int klusid = rs.getInt("klusid");
				java.sql.Date dat = rs.getDate("aanmaakDatum");
				java.util.Date datum = new java.util.Date(dat.getTime());
				//NOG CHECKEN OF DIT KAN (2 connecties tegelijkertijd??
				ConnectDBKlus klusconn = new ConnectDBKlus();
				Klus deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
				f.setID(factuurid);
				//try voor extra waardes bij betaald
				terug = f;
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//nieuwe factuur (per Klus)
	public Factuur nieuweFactuur(Klus k){
		Factuur terug = null;
		java.util.Date vandaag = Calendar.getInstance().getTime();
		java.sql.Date datum = new java.sql.Date(vandaag.getTime());
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
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
			con.close();
			terug = zoekFactuur(factuurid);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//zet alle waardes van factuur in database naar die van ingevoerde factuur-object
	public boolean updateFactuur(Factuur f){
		try{
			java.util.Date aanmaak = f.getAanmaakDatum();
			java.sql.Date aD = new java.sql.Date(aanmaak.getTime());
			java.util.Date betaal = f.getAanmaakDatum();
			java.sql.Date bD = new java.sql.Date(betaal.getTime());
			String isBetaald = "f";
			if(f.getIsBetaald()){
				isBetaald = "t";
			}
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "UPDATE Factuur SET aanmaakDatum='" + aD + "',  betaalDatum='" + bD + 
					"', betalingswijze='" + f.getBetaalwijze() + "', kortingspercentage=" + f.getKorting() + 
					", ' isBetaald='" + isBetaald + "' WHERE factuurid = " + f.getID();
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
	
	//verwijder factuur (per id, geeft false als de gekoppelde klus nog in de database staat)
	public boolean verwijderFactuur(Factuur f){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "DELETE FROM Factuur WHERE factuurid=" + f.getID();
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
