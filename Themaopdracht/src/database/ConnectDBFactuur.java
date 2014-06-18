package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Factuur;
import domeinklassen.Klus;

public class ConnectDBFactuur{

	private Connection con = null;
	
	public ConnectDBFactuur(Connection c){
		con = c;
	}
	
	//alle facturen die nog niet zijn betaald
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
				java.util.Date datum = new java.util.Date(dat.getTime());
				Klus deKlus = null;
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				deKlus = klusconn.zoekKlus(klusid);
				Factuur f = new Factuur(datum, deKlus);
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
