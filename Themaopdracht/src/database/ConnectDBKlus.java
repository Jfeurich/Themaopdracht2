package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Auto;
import domeinklassen.GebruiktProduct;
import domeinklassen.Klus;
import domeinklassen.Onderhoudsbeurt;
import domeinklassen.Reparatie;

public class ConnectDBKlus{
	
	private Connection con;
	//maak connectie
	public ConnectDBKlus(Connection c){
		con = c;
	}
	
	//alle klussen (onderhoudsbeurten EN reparaties) in het systeem
	public ArrayList<Klus> getKlussen(){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			String sql = "SELECT * FROM Klus";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				String type = rs.getString("soort");
				int id = rs.getInt("klusid");
				int manuren = rs.getInt("manuren");
				String status = rs.getString("status");
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto deAuto = autoconn.zoekAuto(rs.getInt("autoid"));
				ConnectDBGebruiktProduct gp = new ConnectDBGebruiktProduct(con);
				ArrayList<GebruiktProduct> deProducten = gp.getProductenVanKlus(id);
				Onderhoudsbeurt o = null;
				Reparatie r = null;
				if(type.equals("onderhoudsbeurt")){
					o = new Onderhoudsbeurt(dat, bes, deAuto);
					o.setID(id);
					o.setGebruikteProducten(deProducten);
					o.addManuren(manuren);
					o.setStatus(status);
					terug.add(o);
				}
				else if(type.equals("reparatie")){
					r = new Reparatie(dat, bes, deAuto);
					r.setID(id);
					r.setGebruikteProducten(deProducten);
					r.addManuren(manuren);
					r.setStatus(status);
					terug.add(r);
				}
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij klussen ophalen " + ex);
		}
		return terug;
	}
	
	//alle onderhoudsbeurten in het systeem
	public ArrayList<Onderhoudsbeurt> getOnderhoudsbeurten(){
		ArrayList<Onderhoudsbeurt> terug = new ArrayList<Onderhoudsbeurt>();
		try{
			String sql = "SELECT * FROM Klus WHERE soort='onderhoudsbeurt'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				int id = rs.getInt("klusid");
				int manuren = rs.getInt("manuren");
				String status = rs.getString("status");
				Onderhoudsbeurt o = new Onderhoudsbeurt(dat, bes);
				o.setID(id);
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto deAuto = autoconn.zoekAuto(rs.getInt("autoid"));
				o.setDeAuto(deAuto);
				ConnectDBGebruiktProduct gpconn = new ConnectDBGebruiktProduct(con);
				o.setGebruikteProducten(gpconn.getProductenVanKlus(id));
				o.setStatus(status);
				o.addManuren(manuren);
				terug.add(o);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij onderhoudsbeurten ophalen " + ex);
		}
		return terug;
	}

	//alle reparaties in het systeem
	public ArrayList<Reparatie> getReparaties(){
		ArrayList<Reparatie> terug = new ArrayList<Reparatie>();
		try{
			String sql = "SELECT * FROM Klus WHERE soort='reparatie'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				int id = rs.getInt("klusid");
				int manuren = rs.getInt("manuren");
				String status = rs.getString("status");
				Reparatie r = new Reparatie(dat, bes);
				r.setID(id);
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto deAuto = autoconn.zoekAuto(rs.getInt("autoid"));
				r.setDeAuto(deAuto);
				ConnectDBGebruiktProduct gpconn = new ConnectDBGebruiktProduct(con);
				r.setGebruikteProducten(gpconn.getProductenVanKlus(id));
				r.setStatus(status);
				r.addManuren(manuren);
				terug.add(r);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij reparaties ophalen " + ex);
		}
		return terug;
	}
	
	//geeft alle klussen voor het ingevoerde auto-object
	public ArrayList<Klus> getKlussenVoorAuto(int autoid){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			String sql = "SELECT * FROM Klus WHERE autoid=" + autoid + ";";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				String type = rs.getString("soort");
				int id = rs.getInt("klusid");
				int manuren = rs.getInt("manuren");
				String status = rs.getString("status");
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto deAuto = autoconn.zoekAuto(autoid);
				ConnectDBGebruiktProduct gpconn = new ConnectDBGebruiktProduct(con);
				if(type.equals("onderhoudsbeurt")){
					Klus o = new Onderhoudsbeurt(dat, bes);
					o.setID(id);
					o.setGebruikteProducten(gpconn.getProductenVanKlus(id));
					o.setDeAuto(deAuto);
					o.addManuren(manuren);
					o.setStatus(status);
					terug.add(o);
				}
				else if(type.equals("reparatie")){
					Klus r = new Reparatie(dat, bes);
					r.setID(id);
					r.setGebruikteProducten(gpconn.getProductenVanKlus(id));
					r.setDeAuto(deAuto);
					r.addManuren(manuren);
					r.setStatus(status);
					terug.add(r);
				}
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij klussen van auto ophalen " + ex);
		}
		return terug;
	}
	
	//zoek op id en krijg Onderhoudsbeurt OF Reparatie terug.
	public Klus zoekKlus(int klusid){
		Klus terug = null;
		try{
			String sql = "SELECT * FROM Klus WHERE klusid=" + klusid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();   
		    java.sql.Date datum = rs.getDate("datum");
		    java.util.Date dat = new java.util.Date(datum.getTime());
			String bes = rs.getString("beschrijving");
			String type = rs.getString("soort");
			int autoid = rs.getInt("autoid");
			int manuren = rs.getInt("manuren");
			String status = rs.getString("status");
			if(type.equals("onderhoudsbeurt")){
				terug = new Onderhoudsbeurt(dat, bes);
				terug.setID(klusid);
			}
			else if(type.equals("reparatie")){
				terug = new Reparatie(dat, bes);
				terug.setID(klusid);
			}
			terug.addManuren(manuren);
			terug.setStatus(status);
			stmt.close();
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			Auto deAuto = autoconn.zoekAuto(autoid);
			terug.setDeAuto(deAuto);
			ConnectDBGebruiktProduct gpconn = new ConnectDBGebruiktProduct(con);
			terug.setGebruikteProducten(gpconn.getProductenVanKlus(klusid));
		}
		catch(Exception ex){
			System.out.println("Probleem bij klus zoeken " + ex);
		}
		return terug;		
	}
	
	//nieuwe klus (paramters datum, beschrijving, type, autoid). id wordt automatisch toegewezen. geeft klus-object terug zodat je het id weet.
	public Klus nieuweKlus(java.util.Date datum, String bes, String tp, int autoid){
		Klus terug = null;
		try{	
		    java.sql.Date dat = new java.sql.Date(datum.getTime());		
			//maak nieuwe klus met gegeven waarden
			String sql = "INSERT INTO Klus (datum, beschrijving, soort, autoid, status, manuren) VALUES ('" + dat + "', '" + bes + "', '" + tp + "', " + autoid + ", 'Nog niet begonnen', 0);";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//zoek meest recente product (hoogste artikelnr) momenteel in database (dat is degene die we net toe hebben gevoegd)
			String sql2 = "SELECT MAX(klusid) FROM Klus";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int klusid = 0;
			while(rs.next()){
				klusid = rs.getInt(1);	
			}
			stmt2.close();
			//zoek product op basis van gevonden artikelnummer
			Klus k = zoekKlus(klusid);
			terug = k;		
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuwe klus " + ex);
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
			String sql = "UPDATE Klus SET datum='" + dat + "',  beschrijving='" + k.getBeschrijving() + "', soort='" + soort + 
					"', status='" + k.getStatus() + "', manuren=" + k.getManuren() + " WHERE klusid = " + k.getID();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij klus updaten" + ex);
		}
		return false;
	}
	
	//delete tabelrij met ingevoerd klusid
	public boolean verwijderKlus(int klusid){
		try{
			//verwijder eerst de gebruikte producten
			ConnectDBGebruiktProduct gpconn = new ConnectDBGebruiktProduct(con);
			ArrayList<GebruiktProduct> deProducten = gpconn.getProductenVanKlus(klusid);
			for(GebruiktProduct gp : deProducten){
				gpconn.verwijderGebruiktProduct(gp.getID());
			}
			//en de (eventuele) factuur
			ConnectDBFactuur fconn = new ConnectDBFactuur(con);
			fconn.verwijderFactuur(fconn.getFactuurVanKlus(klusid));
			//en vervolgens de klus zelf
			String sql = "DELETE FROM Klus WHERE klusid=" + klusid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij klus verwijderen " + ex);
		}
		return false;
	}
}
