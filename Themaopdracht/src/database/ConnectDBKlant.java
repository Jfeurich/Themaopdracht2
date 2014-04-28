package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Klant;
import domeinklassen.Product;

public class ConnectDBKlant {
	final static String DB_DRIV = "com.mysql.jdbc.Driver";
	private String databaseURL = "jdbc:mysql://localhost:3306/ThemaopdrachtDB";
	
	//maak connectie
	public ConnectDBKlant(){
		try{
			Class.forName(DB_DRIV).newInstance();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}

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
}
