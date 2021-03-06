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

/**	
*	Dit is klasse ConnectDBKlus
*	Deze klasse is verantwoordelijk voor het communiceren met en beheren van de tabel Klus in de database
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/

public class ConnectDBKlus{
	
	/**
	*   Variabele con, type Connection
	*	Hier wordt de verbinding met de database opgeslagen
	*	Deze fungeert als brug tussen de java code en de MySQL database
	**/
	private Connection con;
	
	/**
	*	Constructor ConnectDBKlus
	*	Dit is de constructor voor ConnectDBKlus
	*	@param c	Connection met de database
	**/
	public ConnectDBKlus(Connection c){
		con = c;
	}
	
	/**
	*	Methode getKlussenbydatum
	*	Haalt alle klussen op die gepland staan voor de komende maand
	*	@return ArrayList<Klus>
	**/
	//toegevoegd voor overzicht werkplaatsplanning (alle klussen in de komende maand)
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
	
	/**
	*	Methode getKlussenTussenData
	*	Haalt alle klussen op die gepland staan tussen de gegeven data
	*	@param	dat1	java.util.Date Geeft de begindatum waar op moet worden gezocht.
	*	@param	dat2	java.util.Date Geeft de einddatum waar op moet worden gezocht.
	*	@return ArrayList<Klus>
	**/
	//zoek klussen tussen 2 data
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

	/**
	*	Methode getKlussenMetStatus
	*	Haalt alle klussen waarvan de status (deels) overeen komt met de gegeven tekst
	*	@param	status	String Geeft de tekst waar op moet worden gezocht.
	*	@return ArrayList<Klus>
	**/
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
	
	/**
	*	Methode getKlussenMetBeschrijving
	*	Haalt alle klussen waarvan de beschrijving (deels) overeen komt met de gegeven tekst
	*	@param	bes	String Geeft de tekst waar op moet worden gezocht.
	*	@return ArrayList<Klus>
	**/
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
	
	/**
	*	Methode getKlussenVoorAuto
	*	Haalt alle klussen waarvan het autoid overeen komt de gegeven int
	*	@param	autoid	int Geeft het autoid waar op moet worden gezocht.
	*	@return ArrayList<Klus>
	**/
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
	
	/**
	*	Methode getKlussenVoorAutoObject
	*	Haalt alle klussen waarvan het autoid overeen komt met het id van de gegeven Auto
	*	@param	deAuto	Auto Geeft het auto waar op moet worden gezocht.
	*	@return ArrayList<Klus>
	**/
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
	
	/**
	*	Methode getVoltooideKlussenVoorAuto
	*	Haalt alle klussen waarvan het autoid overeen komt met het gegeven nummer waarvan de status 'voltooid' is en waarvan nog geen factuur is gemaakt.
	*	@param	autoid	int Geeft het autoid waar op moet worden gezocht.
	*	@return ArrayList<Klus>
	**/
	//geeft alle klussen bij het ingevoerde id
	public ArrayList<Klus> getVoltooideKlussenVoorAuto(int autoid){
		ArrayList<Klus> terug = new ArrayList<Klus>();
		try{
			String sql = "SELECT * FROM Klus WHERE actief='t' AND status='voltooid' AND autoid=" + autoid + 
					" AND klusid NOT IN(SELECT klusid FROM Factuur);";
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

	/**
	*	Methode zoekKlus
	*	Zoekt de regel waarvan het klusid overeen komt met het gegeven nummer, maakt een Klus-object aan de hand van die regel, en geeft deze Klus terug
	*	@param	klusid	int Geeft het klusid waar op moet worden gezocht.
	*	@return Klus
	**/
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

	/**
	*	Methode zoekKlus
	*	Voegt een regel toe aan Klus met de ingevoerde gegevens, zoekt de hoogste Primary Key in klus, en roept zoekKlus aan op dit nummer.
	*	@param	datum	java.util.Date	De datum van de klus.
	*	@param	bes	String	De beschrijving van de klus.
	*	@param	tp	String	Het type van de klus (onderhoud of reparatie)
	*	@param	autoid	int	Het id van de auto waar de klus aan uit is gevoerd.
	*	@return Klus
	**/
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

	/**
	*	Methode updateKlus
	*	Zoekt de regel in de database waarvan het klusid overeen komt met het id van de gegeven Klus en update die regel naar alle gegevens van de Klus.
	*	@param	k	Klus	De klus waarop de update uit moet worden gevoerd.
	*	@return boolean
	**/
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

	/**
	*	Methode verwijderKlus
	*	Zoekt de regel in de database waarvan het klusid overeen komt met het gegeven nummer en zet actief op 'f'.
	*	@param	klusid	int	Het klusid van de klus die op non-actief moet worden gezet.
	*	@return boolean
	**/
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
