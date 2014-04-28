package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import domeinklassen.Auto;
import domeinklassen.Klus;
import domeinklassen.Onderhoudsbeurt;
import domeinklassen.Reparatie;

public class ConnectDBKlus {
	final static String DB_DRIV = "com.mysql.jdbc.Driver";
	private String databaseURL = "jdbc:mysql://localhost:3306/ThemaopdrachtDB";
	
	//maak connectie
	public ConnectDBKlus(){
		try{
			Class.forName(DB_DRIV).newInstance();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	public Klus zoekKlus(int zoeknummer){
		Klus terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Klus WHERE klusid=" + zoeknummer;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				String type = rs.getString("soort");
				ConnectDBAuto autoconn = new ConnectDBAuto();
				Auto deAuto = autoconn.zoekAuto(rs.getInt("autoid"));
				if(type.equals("onderhoudsbeurt")){
					terug = new Onderhoudsbeurt(dat, bes, deAuto);
				}
				else if(type.equals("reparatie")){
					terug = new Reparatie(dat, bes, deAuto);
				}
				try{
					int manuren = rs.getInt("manuren");
					terug.addManuren(manuren);
				}
				catch(Exception e){
					System.out.println(e);
				}
				try{
					String status = rs.getString("status");
					terug.setStatus(status);
				}
				catch(Exception e){
					System.out.println(e);
				}
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;		
	}
	public Klus nieuweKlus(java.util.Date datum, String bes, String tp, int autoid){
		Klus terug = null;
		try{	
		    java.sql.Date dat = new java.sql.Date(datum.getTime());		
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			//maak nieuwe klus met gegeven waarden
			String sql = "INSERT INTO Klus (datum, beschrijving, soort, autoid) VALUES ('" + dat + "', '" + bes + "', '" + tp + "', " + autoid + ");";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//zoek meest recente product (hoogste artikelnr) momenteel in database (dat is degene die we net toe hebben gevoegd)
			String sql2 = "SELECT MAX(klusid) FROM Klus";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int zoeknummer = 0;
			while(rs.next()){
				zoeknummer = rs.getInt(1);			
			}
			stmt2.close();
			//zoek product op basis van gevonden artikelnummer
			Klus k = zoekKlus(zoeknummer);
			terug = k;
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
}
