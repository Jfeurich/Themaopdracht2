package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

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
			String sql = "SELECT * FROM Klus WHERE actief='t' ORDER BY autoid";
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
				Auto deAuto = autoconn.zoekAutoZonderKlussen(rs.getInt("autoid"));
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
	//toegevoegd voor overzicht werkplaatsplanning
	public ArrayList<Klus> getKlussenbydatum(){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			Calendar nu = Calendar.getInstance();
			nu.add(Calendar.MONTH, 1);
			java.util.Date zoekdat = nu.getTime();
			java.sql.Date start = new java.sql.Date(new java.util.Date().getTime());
			java.sql.Date eind = new java.sql.Date(zoekdat.getTime());
			String sql = "SELECT * FROM Klus WHERE actief='t' AND datum BETWEEN '" + start + "' AND '" + eind + "'";
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
				Auto deAuto = autoconn.zoekAutoZonderKlussen(rs.getInt("autoid"));
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
	//zoek klussen met status...
	public ArrayList<Klus> getKlussenTussenData(java.util.Date dat1, java.util.Date dat2){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			java.sql.Date beginDat = new java.sql.Date(dat1.getTime());
			java.sql.Date eindDat = new java.sql.Date(dat2.getTime());
			String sql = "SELECT * FROM Klus WHERE actief='t' AND datum BETWEEN'" + beginDat + "' AND '"+ eindDat + "'";
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
				Auto deAuto = autoconn.zoekAutoZonderKlussen(rs.getInt("autoid"));
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

	//zoek klussen met status...
	public ArrayList<Klus> getKlussenMetStatus(String status){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			String sql = "SELECT * FROM Klus WHERE actief='t' AND status LIKE'%" + status + "%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String bes = rs.getString("beschrijving");
				String type = rs.getString("soort");
				int id = rs.getInt("klusid");
				int manuren = rs.getInt("manuren");
				status = rs.getString("status");
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto deAuto = autoconn.zoekAutoZonderKlussen(rs.getInt("autoid"));
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
	//zoek klussen met beschrijving...
	public ArrayList<Klus> getKlussenMetBeschrijving(String bes){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			String sql = "SELECT * FROM Klus WHERE actief='t' AND beschrijving LIKE'%" + bes + "%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
			    java.sql.Date datum = rs.getDate("datum");
			    java.util.Date dat = new java.util.Date(datum.getTime());
				String type = rs.getString("soort");
				int id = rs.getInt("klusid");
				int manuren = rs.getInt("manuren");
				String status = rs.getString("status");
				bes = rs.getString("beschrijving");
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto deAuto = autoconn.zoekAutoZonderKlussen(rs.getInt("autoid"));
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
	//geeft alle klussen bij het ingevoerde id
	public ArrayList<Klus> getKlussenVoorAuto(int autoid){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			String sql = "SELECT * FROM Klus WHERE actief='t' AND autoid=" + autoid;
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
				Auto deAuto = autoconn.zoekAutoZonderKlussen(autoid);
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
	//geeft alle klussen bij het ingevoerde Auto-object
	public ArrayList<Klus> getKlussenVoorAutoObject(Auto deAuto){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			String sql = "SELECT * FROM Klus WHERE actief='t' AND autoid=" + deAuto.getID();
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
				ConnectDBGebruiktProduct gpconn = new ConnectDBGebruiktProduct(con);
				if(type.equals("onderhoudsbeurt")){
					Klus o = new Onderhoudsbeurt(dat, bes);
					o.setID(id);
					o.setGebruikteProducten(gpconn.getProductenVanKlus(id));
					o.setDeAuto(deAuto);
					o.addManuren(manuren);
					o.setStatus(status);
					terug.add(o);
					deAuto.voegKlusToe(o);
				}
				else if(type.equals("reparatie")){
					Klus r = new Reparatie(dat, bes);
					r.setID(id);
					r.setGebruikteProducten(gpconn.getProductenVanKlus(id));
					r.setDeAuto(deAuto);
					r.addManuren(manuren);
					r.setStatus(status);
					terug.add(r);
					deAuto.voegKlusToe(r);
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
			Auto deAuto = autoconn.zoekAutoZonderKlussen(autoid);
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
			String sql = "INSERT INTO Klus (datum, beschrijving, soort, autoid, status, manuren, actief) VALUES ('" + dat + "', '" + bes + "', '" + tp + "', " + autoid + ", 'Nog niet begonnen', 0, 't');";
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
		    String soort = "reparatie";
		    if(k instanceof Onderhoudsbeurt){
		    	soort = "onderhoudsbeurt";
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
	
	//zet klus op non-actief
	public boolean verwijderKlus(int klusid){
		try{
			String sql = "UPDATE Klus SET actief='f' WHERE klusid=" + klusid;
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
