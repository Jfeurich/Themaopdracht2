package database;
	
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Klant;
import domeinklassen.User;

/**	
*	Dit is klasse ConnectDBUser
*	Deze klasse is verantwoordelijk voor het communiceren met en beheren van de tabel User in de database
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/

public class ConnectDBUser{
	
	/**
	*   Variabele con, type Connection
	*	Hier wordt de verbinding met de database opgeslagen
	*	Deze fungeert als brug tussen de java code en de MySQL database
	**/
	private Connection con;
	
	/**
	*	Constructor ConnectDBUser 
	*	Dit is de constructor voor ConnectDBUser
	*	@param c	Connection met de database
	**/
	public ConnectDBUser(Connection c){
		con = c;
	}
	
	/**
	*	Methode getUsers
	*	Haalt alle actieve Users uit de database
	*	@return ArrayList<User>
	**/
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
	
	/**
	*	Methode getUsersNonActief
	*	Haalt alle niet-actieve Users uit de database
	*	@return ArrayList<User>
	**/
	//alle non-actieve users in het systeem
	public ArrayList<User> getUsersNonActief(){
		ArrayList<User> terug = new ArrayList<User>();
		try{
			String sql = "SELECT * FROM User WHERE actief='f'";
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

	
	/**
	*	Methode zoekUserVanKlant
	*	Zoekt de User waarbij het klantnummer overeen komt met het klantnummer van de gegeven Klant
	*	@param	k	Klant waar de User bij moet worden gezocht
	*	@return User
	**/
	//zoek user bij klant
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
		
	/**
	*	Methode zoekUser
	*	Zoekt de User waarbij het klantnummer overeen komt met gegeven nummer
	*	@param	id	int waar de User bij moet worden gezocht
	*	@return User
	**/	
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

	/**
	*	Methode nieuweUserNietKlant
	*	Voegt een regel toe aan tabel User met de gegeven parameters, haalt de hoogste Primary Key uit User, roept zoekUser aan met die Primary Key en geeft de resulterende User terug
	*	@param	tp	int	geeft aan van welk type de gebruikersaccount is
	*	@param	unm	String de gebruikersnaam van de user
	*	@param	pw	String het wachtwoord van de user
	*	@param	email	String de emailaccount van de user
	*	@param	naam	String de naam van de user
	*	@return User
	**/	
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
	/**
	*	Methode nieuweUserIsKlant
	*	Voegt een regel toe aan tabel User met de gegeven parameters, haalt de hoogste Primary Key uit User, roept zoekUser aan met die Primary Key en geeft de resulterende User terug
	*	@param	deKlant	Klant	Het Klant-object waar een gebruikersaccount voor moet worden gemaakt. Hier kunnen gegevens uit worden gehaald zoals de naam van de Klant.
	*	@param	unm	String 	De gebruikersnaam van de user
	*	@param	pw	String 	Het wachtwoord van de user
	*	@param	email	String 	De emailaccount van de user
	*	@return User
	**/	
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
	
	/**
	*	Methode getUser
	*	Zoekt in de tabel User naar de gegeven naam, maakt van de resulterende regel een User, en geeft deze terug
	*	@param	unm	String	De gebruikersnaam waar op moet worden gezocht
	*	@return User
	**/	
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
	
	/**
	*	Methode checkUser
	*	Gebruikt voor bij aanmaken van nieuwe Users, deze methode controleert dat de gewenste gebruikersnaam en het emailadres nog niet in de database voorkomen bij een bestaande User.
	*	@param	unm	String de gebruikersnaam van de user
	*	@param	em	String de emailaccount van de user
	*	@return User
	**/	
	public User checkUser(String unm, String em){
		User terug = null;
		try{
			String sql = "SELECT * FROM User WHERE gebruikersnaam='" + unm + "' OR email LIKE '%" + em + "%'";
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
	
	/**
	*	Methode updateUser
	*	Wijzigt de regel in tabel User naar de gegevens van het ingevoerde User-object
	*	@param	u	User Hier kunnen de gegevens uit worden gehaald die worden gewijzigd.
	*	@return boolean
	**/	
	//stel nieuw wachtwoord of emailadres in. 
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
	
	/**
	*	Methode verwijderUser
	*	Wijzigt de regel in tabel User en zet actief op 'f'
	*	@param	id	int Het userid van de tabel-regel die moet worden gewijzigd
	*	@return boolean
	**/	
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
	
	/**
	*	Methode verwijderUserIsKlant
	*	Wijzigt de regel in tabel User en zet actief op 'f' en roept de methode verwijderKlant aan van ConnectDBKlant
	*	@param	id	int Het userid van de tabel-regel die moet worden gewijzigd in User
	*	@param	klantid	int Het klantid van de tabel-regel die moet worden gewijzigd in Klant
	*	@return boolean
	**/		
	//zet user op non-actief
	public boolean verwijderUserIsKlant(int id, int klantid){
		try{
			ConnectDBKlant kcon = new ConnectDBKlant(con);
			kcon.verwijderKlant(klantid);
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
	
	/**
	*	Methode activeerUser
	*	Wijzigt de regel in tabel User en zet actief op 't'
	*	@param	id	int Het userid van de tabel-regel die moet worden gewijzigd
	*	@return boolean
	**/		
	//zet user op actief
	public boolean activeerUser(int id){
		try{
			String sql = "UPDATE User SET actief='t' WHERE userid=" + id;
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
	
	/**
	*	Methode activeerUserIsKlant
	*	Wijzigt de regel in tabel User en zet actief op 't' en roept de methode activeerKlant aan van ConnectDBKlant
	*	@param	id	int Het userid van de tabel-regel die moet worden gewijzigd in User
	*	@param	klantid	int Het klantid van de tabel-regel die moet worden gewijzigd in Klant
	**/		//zet user op actief
	public boolean activeerUserIsKlant(int id, int klantid){
		try{
			ConnectDBKlant kcon = new ConnectDBKlant(con);
			kcon.activeerKlant(klantid);
			String sql = "UPDATE User SET actief='t' WHERE userid=" + id;
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
