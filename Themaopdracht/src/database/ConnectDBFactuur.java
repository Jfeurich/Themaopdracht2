package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import domeinklassen.Factuur;
import domeinklassen.Klant;
import domeinklassen.Klus;

public class ConnectDBFactuur extends ConnectDB {

	public ConnectDBFactuur(){
		super();
	}
	
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
				//NOT CHECKEN OF DIT KAN (2 connecties tegelijkertijd??
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
	
	public Factuur zoekFactuur(int factuurid){
		Factuur terug = null;
		return terug;
	}
	
	public Factuur nieuweFactuur(Klus k){
		java.util.Date vandaag = Calendar.getInstance().getTime();
		java.sql.Date datum = new java.sql.Date(vandaag.getTime());
		Factuur terug = null;
		return terug;
	}
	
	public boolean updateFactuur(Factuur f){
		return false;
	}
	
	public boolean verwijderFactuur(int factuurid){
		return false;
	}
}
