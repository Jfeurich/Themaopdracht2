package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Auto;
import domeinklassen.Herinneringsbrief;
import domeinklassen.Klant;
import domeinklassen.Reservering;

public class ConnectDBReservering{
	
	private Connection con;
	//maak connectie
	public ConnectDBReservering(Connection c){
		con = c;
	}
	
	//alle reserveringen in het systeem
	public ArrayList<Reservering> getReserveringen(){
		ArrayList<Reservering> terug = new ArrayList<Reservering>();
		try{
			String sql = "SELECT * FROM Reservering";
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
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto a = autoconn.zoekAuto(autoid);
				Reservering r = new Reservering(a, id, bD, eD, dP);
				terug.add(r);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}

	//zoek naar alle reserveringen tussen begindatum en einddatum
	public ArrayList<Reservering> getReserveringTussen(java.util.Date begin, java.util.Date eind){
		ArrayList<Reservering> terug = new ArrayList<Reservering>();
		try{
			java.sql.Date beginDat = new java.sql.Date(begin.getTime());
			java.sql.Date eindDat = new java.sql.Date(eind.getTime());
			String sql = "SELECT * FROM Reservering WHERE '" + eindDat + "'>beginDat OR '" + beginDat + "'<eindDat";
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
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto a = autoconn.zoekAuto(autoid);
				Reservering r = new Reservering(a, id, bD, eD, dP);
				terug.add(r);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
	
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
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto a = autoconn.zoekAuto(autoid);
				terug = new Reservering(a, id, bD, eD, dP);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;		
	}
	
	//maak nieuwe Reservering. id wordt automatisch toegewezen. geeft Reservering-object terug zodat je het id weet.
	public Reservering nieuweReservering(Auto a, int dP, java.util.Date bD, java.util.Date eD){
		Reservering terug = null;
		try{
			java.sql.Date beginDat = new java.sql.Date(bD.getTime());
			java.sql.Date eindDat = new java.sql.Date(eD.getTime());
			String sql = "INSERT INTO Reservering (beginDat, eindDat, autoid, deParkeerplek) VALUES ('" + beginDat + "', '" + 
			eindDat + "', " + a.getID() + ", " + dP + ");";
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
			System.out.println(ex);
		}
		return terug;
	}

	//update begindatum, eindddatum, en parkeerplaats
	public boolean updateReservering(Reservering r){
		try{
			java.util.Date bdat = r.getBegDat();
			java.util.Date edat = r.getEindDat();
			java.sql.Date beginDat = new java.sql.Date(bdat.getTime());
			java.sql.Date eindDat = new java.sql.Date(edat.getTime());
			String sql = "UPDATE Reservering SET beginDat='" + beginDat + "',  eindDat='" + eindDat + 
					"', deParkeerplek=" + r.getDeParkeerplek() + " WHERE reserveringid=" + r.getID();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return false;
	}
	
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
			System.out.println(ex);
		}
		return false;
	}
}
