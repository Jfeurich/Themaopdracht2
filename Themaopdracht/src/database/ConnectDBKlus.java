package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Auto;
import domeinklassen.Klus;
import domeinklassen.Onderhoudsbeurt;
import domeinklassen.Reparatie;

public class ConnectDBKlus extends ConnectDB{
	
	//maak connectie
	public ConnectDBKlus(){
		super();
	}
	
	//alle klussen (onderhoudsbeurten EN reparaties) in het systeem
	public ArrayList<Klus> getKlussen(){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Klus";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				String type = rs.getString("soort");
				ConnectDBAuto autoconn = new ConnectDBAuto();
				Auto deAuto = autoconn.zoekAuto(rs.getInt("autoid"));
				Onderhoudsbeurt o = null;
				Reparatie r = null;
				if(type.equals("onderhoudsbeurt")){
					o = new Onderhoudsbeurt(dat, bes, deAuto);
					try{
						int manuren = rs.getInt("manuren");
						o.addManuren(manuren);
					}
					catch(Exception e){
						System.out.println(e);
					}
					try{
						String status = rs.getString("status");
						o.setStatus(status);
					}
					catch(Exception e){
						System.out.println(e);
					}
					terug.add(o);
				}
				else if(type.equals("reparatie")){
					r = new Reparatie(dat, bes, deAuto);
					try{
						int manuren = rs.getInt("manuren");
						r.addManuren(manuren);
					}
					catch(Exception e){
						System.out.println(e);
					}
					try{
						String status = rs.getString("status");
						r.setStatus(status);
					}
					catch(Exception e){
						System.out.println(e);
					}
					terug.add(r);
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
	
	//alle onderhoudsbeurten in het systeem
	public ArrayList<Onderhoudsbeurt> getOnderhoudsbeurten(){
		ArrayList<Onderhoudsbeurt> terug = new ArrayList<Onderhoudsbeurt>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Klus WHERE soort='onderhoudsbeurt'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				ConnectDBAuto autoconn = new ConnectDBAuto();
				Auto deAuto = autoconn.zoekAuto(rs.getInt("autoid"));
				Onderhoudsbeurt o = new Onderhoudsbeurt(dat, bes, deAuto);
				try{
					int manuren = rs.getInt("manuren");
					o.addManuren(manuren);
				}
				catch(Exception e){
					System.out.println(e);
				}
				try{
					String status = rs.getString("status");
					o.setStatus(status);
				}
				catch(Exception e){
					System.out.println(e);
				}
				terug.add(o);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}

	//alle reparaties in het systeem
	public ArrayList<Reparatie> getReparaties(){
		ArrayList<Reparatie> terug = new ArrayList<Reparatie>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Klus WHERE soort='reparatie'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				ConnectDBAuto autoconn = new ConnectDBAuto();
				Auto deAuto = autoconn.zoekAuto(rs.getInt("autoid"));
				Reparatie r = new Reparatie(dat, bes, deAuto);
				try{
					int manuren = rs.getInt("manuren");
					r.addManuren(manuren);
				}
				catch(Exception e){
					System.out.println(e);
				}
				try{
					String status = rs.getString("status");
					r.setStatus(status);
				}
				catch(Exception e){
					System.out.println(e);
				}
				terug.add(r);
			}
			stmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//geeft alle klussen voor het ingevoerde auto-object
	public ArrayList<Klus> getKlussenVoorAuto(Auto a){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Klus WHERE autoid='" + a.getID() + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				String type = rs.getString("soort");
				ConnectDBAuto autoconn = new ConnectDBAuto();
				Auto deAuto = autoconn.zoekAuto(rs.getInt("autoid"));
				Onderhoudsbeurt o = null;
				Reparatie r = null;
				if(type.equals("onderhoudsbeurt")){
					o = new Onderhoudsbeurt(dat, bes, deAuto);
					try{
						int manuren = rs.getInt("manuren");
						o.addManuren(manuren);
					}
					catch(Exception e){
						System.out.println(e);
					}
					try{
						String status = rs.getString("status");
						o.setStatus(status);
					}
					catch(Exception e){
						System.out.println(e);
					}
					terug.add(o);
				}
				else if(type.equals("reparatie")){
					r = new Reparatie(dat, bes, deAuto);
					try{
						int manuren = rs.getInt("manuren");
						r.addManuren(manuren);
					}
					catch(Exception e){
						System.out.println(e);
					}
					try{
						String status = rs.getString("status");
						r.setStatus(status);
					}
					catch(Exception e){
						System.out.println(e);
					}
					terug.add(r);
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
	
	//zoek op id en krijg Onderhoudsbeurt OF Reparatie terug.
	public Klus zoekKlus(int klusid){
		Klus terug = null;
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "SELECT * FROM Klus WHERE klusid=" + klusid;
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
					terug.setID(klusid);
				}
				else if(type.equals("reparatie")){
					terug = new Reparatie(dat, bes, deAuto);
					terug.setID(klusid);
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
	
	//nieuwe klus (paramters datum, beschrijving, type, autoid). id wordt automatisch toegewezen. geeft klus-object terug zodat je het id weet.
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
			while(rs.next()){
				int klusid = rs.getInt(1);	
				//zoek product op basis van gevonden artikelnummer
				Klus k = zoekKlus(klusid);
				terug = k;		
			}
			stmt2.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
	//update klus in database naar alle waarden van het ingevoerde klus-object (uiteraard NIET het id)
	public boolean updateKlus(Klus k){
		try{
			java.util.Date datum = k.getDatum();
		    java.sql.Date dat = new java.sql.Date(datum.getTime());	
		    String soort = "";
		    if(k instanceof Onderhoudsbeurt){
		    	soort = "onderhoudsbeurt";
		    }
		    else if(k instanceof Reparatie){
		    	soort = "reparatie";
		    }
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "UPDATE Klus SET datum='" + dat + "',  beschrijving='" + k.getBeschrijving() + "', soort='" + soort + 
					"', status='" + k.getStatus() + "', manuren=" + k.getManuren() + " WHERE klusid = " + k.getID();
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
	
	//delete tabelrij met ingevoerd klusid
	public boolean verwijderKlus(int klusid){
		try{
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			String sql = "DELETE FROM Klus WHERE klusid=" + klusid;
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
