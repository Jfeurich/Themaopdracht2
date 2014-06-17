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
			String sql = "SELECT * FROM User WHERE actief='t'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("userid");
				String unm = rs.getString("gebruikersnaam");
				String ww = rs.getString("wachtwoord");
				String email = rs.getString("email");
				int type = rs.getInt("type");
				int klantid = rs.getInt("klantid");
				User u = new User(id, type, unm, ww, email);
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
			String sql = "SELECT * FROM User WHERE actief='t' AND type=" + nummer;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("userid");
				String unm = rs.getString("gebruikersnaam");
				String ww = rs.getString("wachtwoord");
				String email = rs.getString("email");
				int klantid = rs.getInt("klantid");
				User u = new User(id, nummer, unm, ww, email);
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
	public User zoekUserVanKlant(Klant k){
		User terug = null;
		try{
			String sql = "SELECT * FROM User WHERE klantid=" + k.getKlantnummer();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String unm = rs.getString("gebruikersnaam");
				String ww = rs.getString("wachtwoord");
				String email = rs.getString("email");
				int type = rs.getInt("type");
				int id = rs.getInt("userid");
				User u = new User(id, type, unm, ww, email);
				u.setDeKlant(k);
				terug = u;
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij user zoeken " + ex);
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
				String email = rs.getString("email");
				int type = rs.getInt("type");
				int klantid = rs.getInt("klantid");
				User u = new User(id, type, unm, ww, email);
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
			System.out.println("Probleem bij user zoeken op id " + ex);
		}
		return terug;		
	}

		
	//maak nieuwe User van types 0-2. id wordt automatisch toegewezen. geeft User-object terug zodat je het id weet.
	public User nieuweUserNietKlant(int tp, String unm, String pw, String email, String naam){
		User terug = null;
		try{
			String sql = "INSERT INTO User (gebruikersnaam, wachtwoord, type, email, naam, actief) VALUES ('" + unm.toLowerCase() + "', '" + pw + "', " + tp + ", '" + email + "', '" + naam + "', 't');";
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
	public User nieuweUserIsKlant(Klant deKlant, String unm, String pw, String email){
		User terug = null;
		try{
			String sql = "INSERT INTO User (gebruikersnaam, wachtwoord, email, type, klantid, actief) VALUES ('" + unm.toLowerCase() + "', '" + pw + "', '" + email + "', 3, " + deKlant.getKlantnummer() + ", 't');";
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
	
	public User getUser(String unm){
		User terug = null;
		try{
			String sql = "SELECT * FROM User WHERE actief='t' AND gebruikersnaam='" + unm.toLowerCase() + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("userid");
				String ww = rs.getString("wachtwoord");
				String email = rs.getString("email");
				int type = rs.getInt("type");
				int klantid = rs.getInt("klantid");
				User u = new User(id, type, unm, ww, email);
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
			System.out.println("Probleem bij user zoeken op naam " + ex);
		}
		return terug;		
	}
	
	public User checkUser(String unm, String em){
		User terug = null;
		try{
			String sql = "SELECT * FROM User WHERE gebruikersnaam='" + unm + "' OR email LIKE %'" + em + "'%";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("userid");
				String ww = rs.getString("wachtwoord");
				String email = rs.getString("email");
				int type = rs.getInt("type");
				int klantid = rs.getInt("klantid");
				User u = new User(id, type, unm, ww, email);
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
			System.out.println("Probleem bij user zoeken op naam " + ex);
		}
		return terug;		
	}
	
	//stel nieuw wachtwoord in. 
	public boolean updateUser(User u){
		try{
			String sql = "UPDATE User SET wachtwoord='" + u.getWachtwoord() + "', email='" + u.getEmail() + "' WHERE userid= " + u.getID();
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
	
	//zet user op non-actief
	public boolean verwijderUser(int id){
		try{
			String sql = "UPDATE User SET actief='f' WHERE userid=" + id;
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
	
	//zet account van klantid op non-actief
	public boolean verwijderAccountVan(int klantid){
		try{
			String sql = "UPDATE User SET actief='f' WHERE klantid=" + klantid;
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
