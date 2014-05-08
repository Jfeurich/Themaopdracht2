package database;
	
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Klant;
import domeinklassen.User;
	
public class ConnectDBUser{
	
	private Connection con;
	
	public ConnectDBUser(Connection c){
		con = c;
	}
	
	//alle users in het systeem
	public ArrayList<User> getUsers(){
		ArrayList<User> terug = new ArrayList<User>();
		try{
			String sql = "SELECT * FROM User";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("userid");
				String unm = rs.getString("gebruikersnaam");
				String ww = rs.getString("wachtwoord");
				int type = rs.getInt("type");
				int klantid = rs.getInt("klantid");
				User u = new User(id, type, unm, ww);
				if(type == 3){ //als het de account van een klant is, stel de klant dan in
					ConnectDBKlant klantconn = new ConnectDBKlant(con);
					Klant deKlant = klantconn.zoekKlant(klantid);
					u.setDeKlant(deKlant);
				}
				terug.add(u);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij users ophalen " + ex);
		}
		return terug;
	}
	
	//zoek naar alle users van een bepaald type (0) administratie, 1) monteur, 2) jopie, en 3) klanten)
	public ArrayList<User> getUsersVanType(int nummer){
		ArrayList<User> terug = new ArrayList<User>();
		try{
			String sql = "SELECT * FROM User WHERE type=" + nummer;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("userid");
				String unm = rs.getString("gebruikersnaam");
				String ww = rs.getString("wachtwoord");
				int klantid = rs.getInt("klantid");
				User u = new User(id, nummer, unm, ww);
				if(nummer == 3){ //als het de account van een klant is, stel de klant dan in
					ConnectDBKlant klantconn = new ConnectDBKlant(con);
					Klant deKlant = klantconn.zoekKlant(klantid);
					u.setDeKlant(deKlant);
				}
				terug.add(u);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij users ophalen per type " + ex);
		}
		return terug;
	}
	
	//zoek user op id
	public User zoekUser(int id){
		User terug = null;
		try{
			String sql = "SELECT * FROM User WHERE userid=" + id;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String unm = rs.getString("gebruikersnaam");
				String ww = rs.getString("wachtwoord");
				int type = rs.getInt("type");
				int klantid = rs.getInt("klantid");
				User u = new User(id, type, unm, ww);
				if(type == 3){ //als het de account van een klant is, stel de klant dan in
					ConnectDBKlant klantconn = new ConnectDBKlant(con);
					Klant deKlant = klantconn.zoekKlant(klantid);
					u.setDeKlant(deKlant);
				}
				terug = u;
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij user zoeken " + ex);
		}
		return terug;		
	}
	
	//maak nieuwe User van types 0-2. id wordt automatisch toegewezen. geeft User-object terug zodat je het id weet.
	public User nieuweUserNietKlant(int tp, String unm, String pw){
		User terug = null;
		try{
			String sql = "INSERT INTO User (gebruikersnaam, wachtwoord, type) VALUES ('" + unm + "', '" + pw + "', " + tp + ");";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			String sql2 = "SELECT MAX(userid) FROM User";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int id = 0;
			while(rs.next()){
				id = rs.getInt(1);		
			}
			stmt2.close();
			terug = zoekUser(id);
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuwe user(NIET klant) " + ex);
		}
		return terug;
	}
	
	//maak nieuwe User van type 3. id wordt automatisch toegewezen. geeft User-object terug zodat je het id weet.
	public User nieuweUserIsKlant(Klant deKlant, String unm, String pw){
		User terug = null;
		try{
			String sql = "INSERT INTO User (gebruikersnaam, wachtwoord, type, klantid) VALUES ('" + unm + "', '" + pw + "', 3, " + deKlant.getKlantnummer() + ");";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			String sql2 = "SELECT MAX(userid) FROM User";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int id = 0;
			while(rs.next()){
				id = rs.getInt(1);		
			}
			stmt2.close();
			terug = zoekUser(id);
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuwe user(WEL klant)" + ex);
		}
		return terug;
	}
	
	//stel nieuw wachtwoord in. 
	public boolean updateUser(User u){
		try{
			String sql = "UPDATE User SET wachtwoord='" + u.getWachtwoord() + "' WHERE userid= " + u.getID();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij user updaten " + ex);
		}
		return false;
	}
	
	//verwijderd tabelrij met ingevoerd id
	public boolean verwijderUser(int id){
		try{
			String sql = "DELETE FROM User WHERE userid=" + id;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij user verwijderen " + ex);
		}
		return false;
	}
}
