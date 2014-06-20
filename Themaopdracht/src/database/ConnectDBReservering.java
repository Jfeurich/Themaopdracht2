package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import domeinklassen.Auto;
import domeinklassen.Reservering;

/**	
*	Dit is klasse ConnectDBReservering
*	Deze klasse is verantwoordelijk voor het communiceren met en beheren van de tabel Reservering in de database
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/

public class ConnectDBReservering{
	
	/**
	*   Variabele con, type Connection
	*	Hier wordt de verbinding met de database opgeslagen
	*	Deze fungeert als brug tussen de java code en de MySQL database
	**/
	private Connection con;
	
	/**
	*	Constructor ConnectDBReservering
	*	Dit is de constructor voor ConnectDBReservering
	*	@param c	Connection met de database
	**/
	public ConnectDBReservering(Connection c){
		con = c;
	}
	
	/**
	*	Methode getReserveringenTussen
	*	Haalt alle reserveringen tussen de gegeven data uit de database
	*	@param	begin java.util.Date Geeft de begindatum waar op moet worden gezocht.
	*	@param	eind	java.util.Date Geeft de einddatum waar op moet worden gezocht.
	*	@return ArrayList<Reservering>
	**/
	//zoek naar alle reserveringen tussen begindatum en einddatum
	public ArrayList<Reservering> getReserveringenTussen(java.util.Date begin, java.util.Date eind){
		ArrayList<Reservering> terug = new ArrayList<Reservering>();
		try{
			java.sql.Date beginDat = new java.sql.Date(begin.getTime());
			java.sql.Date eindDat = new java.sql.Date(eind.getTime());
			String sql = "SELECT * FROM Reservering WHERE (beginDat BETWEEN'" + beginDat + "' AND '"
					+ eindDat + "') OR (eindDat BETWEEN '" + beginDat + "' AND '" + beginDat + "')";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("reserveringid");
				int dP = rs.getInt("deParkeerplek");
				java.sql.Date bdat = rs.getDate("beginDat");
				java.util.Date bD = new Date(bdat.getTime());
				java.sql.Date edat = rs.getDate("eindDat");
				java.util.Date eD = new Date(edat.getTime());
				int autoid = rs.getInt("autoid");
				String isGeweest = rs.getString("isGeweest");
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto a = autoconn.zoekAuto(autoid);
				Reservering nieuw = new Reservering(a, id, bD, eD, dP);
				if(isGeweest.equals("t")){
					nieuw.setGeweest(true);
				}
				terug.add(nieuw);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij reserveringen tussen data ophalen " + ex);
		}
		return terug;
	}
	
	/**
	*	Methode zoekReservering
	*	Zoekt de regel in de database met het gegeven reserveringid, maakt aan de hand hiervan een Reservering-object, en geeft dit terug.
	*	@param	id	int Geeft het reserveringid waar op moet worden gezocht.
	*	@return Reservering
	**/	
	//zoek reservering op id
	public Reservering zoekReservering(int id){
		Reservering terug = null;
		try{
			String sql = "SELECT * FROM Reservering WHERE reserveringid=" + id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int dP = rs.getInt("deParkeerplek");
				java.sql.Date bdat = rs.getDate("beginDat");
				java.util.Date bD = new Date(bdat.getTime());
				java.sql.Date edat = rs.getDate("eindDat");
				java.util.Date eD = new Date(edat.getTime());
				int autoid = rs.getInt("autoid");
				String isGeweest = rs.getString("isGeweest");
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto a = autoconn.zoekAuto(autoid);
				terug = new Reservering(a, id, bD, eD, dP);
				if(isGeweest.equals("t")){
					terug.setGeweest(true);
				}
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij reservering zoeken " + ex);
		}
		return terug;		
	}
	
	/**
	*	Methode zoekReserveringenVanAuto
	*	Zoekt de regels in de database met het gegeven autoid, maakt aan de hand hiervan Reservering-objecten, en geeft een ArrayList hiervan terug.
	*	@param	id	int Geeft het reserveringid waar op moet worden gezocht.
	*	@return ArrayList<Reservering>
	**/		
	//zoek reserveringen per auto
	public ArrayList<Reservering> zoekReserveringenVanAuto(int autoid){
		ArrayList<Reservering> terug = new ArrayList<Reservering>();
		try{
			String sql = "SELECT * FROM Reservering WHERE autoid=" + autoid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("reserveringid");
				int dP = rs.getInt("deParkeerplek");
				java.sql.Date bdat = rs.getDate("beginDat");
				java.util.Date bD = new Date(bdat.getTime());
				java.sql.Date edat = rs.getDate("eindDat");
				java.util.Date eD = new Date(edat.getTime());
				String isGeweest = rs.getString("isGeweest");
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto a = autoconn.zoekAuto(autoid);
				Reservering nieuw = new Reservering(a, id, bD, eD, dP);
				if(isGeweest.equals("t")){
					nieuw.setGeweest(true);
				}
				terug.add(nieuw);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij reservering zoeken " + ex);
		}
		return terug;		
	}

	/**
	*	Methode nieuweReservering
	*	Maakt een nieuwe regel in tabel Reservering met de ingevoerde parameters, zoekt de hoogste Primary Key in Reservering, en roept hiermee zoekReservering aan.
	*	@param	a	Auto De auto waar de resrevering voor wordt gemaakt.
	*	@param	dP	int	De parkeerplek waar de reservering voor wordt gemaakt.
	*	@param	bD	java.util.Date	De begindatum van de reservering.
	*	@param	eD	java.util.Date	De einddatum van de reservering.
	*	@return Reservering
	**/		
	//maak nieuwe Reservering. id wordt automatisch toegewezen. geeft Reservering-object terug zodat je het id weet.
	public Reservering nieuweReservering(Auto a, int dP, java.util.Date bD, java.util.Date eD){
		Reservering terug = null;
		try{
			java.sql.Date beginDat = new java.sql.Date(bD.getTime());
			java.sql.Date eindDat = new java.sql.Date(eD.getTime());
			String sql = "INSERT INTO Reservering (beginDat, eindDat, autoid, deParkeerplek, isGeweest) VALUES ('" + beginDat + "', '" + 
			eindDat + "', " + a.getID() + ", " + dP + ", 'f');";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			String sql2 = "SELECT MAX(reserveringid) FROM Reservering";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int id = 0;
			while(rs.next()){
				id = rs.getInt(1);		
			}
			stmt2.close();
			terug = zoekReservering(id);
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuwe reservering" + ex);
		}
		return terug;
	}

	/**
	*	Methode updateReservering
	*	Wijzigt alle gegevens in de tabelrij die hoort bij de gegeven Reservering naar die van de Reservering
	*	@param	r	Reservering	De reservering waarvoor de tabelrij moet worden geupdate.
	*	@return boolean
	**/		
	//update begindatum, eindddatum, en parkeerplaats
	public boolean updateReservering(Reservering r){
		try{
			String isGeweest = "f";
			if(r.isGeweest()){
				isGeweest = "t";
			}
			java.util.Date bdat = r.getBegDat();
			java.util.Date edat = r.getEindDat();
			java.sql.Date beginDat = new java.sql.Date(bdat.getTime());
			java.sql.Date eindDat = new java.sql.Date(edat.getTime());
			String sql = "UPDATE Reservering SET beginDat='" + beginDat + "',  eindDat='" + eindDat + "', deParkeerplek=" 
					+ r.getDeParkeerplek() + ", isGeweest='" + isGeweest + "' WHERE reserveringid=" + r.getID();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij reservering updaten " + ex);
		}
		return false;
	}
	
	/**
	*	Methode verwijderReservering
	*	Verwijdert de tabelrij met het gegeven reserveringid
	*	@param	id	int	Het reserveringid van de tabelrij die verwijderd moet worden.
	*	@return boolean
	**/	
	//verwijderd tabelrij met ingevoerd id
	public boolean verwijderReservering(int id){
		try{
			String sql = "DELETE FROM Reservering WHERE reserveringid=" + id;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij reservering verwijderen " + ex);
		}
		return false;
	}

	/**
	*	Methode getAankomendeReservering
	*	Roept methode zoekReserveringenVanAuto voor iedere auto in de gegeven ArrayList
	*	@param	autos	ArrayList<Auto> De lijst van de auto's waar reserveringen voor moet worden gezocht
	*	@return ArrayList<Reservering>
	**/
	public ArrayList<Reservering> getAankomendeReservering(ArrayList<Auto> autos){
		ArrayList<Reservering> deReserveringen = new ArrayList<Reservering>();
		for(Auto a: autos){
			ArrayList<Reservering> res = zoekReserveringenVanAuto(a.getID());
			for(Reservering r: res){
				deReserveringen.add(r);
			}
		}
		return deReserveringen;
	}
}
