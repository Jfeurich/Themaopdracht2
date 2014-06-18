package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Auto;
import domeinklassen.Klant;

public class ConnectDBAuto{
	
	private Connection con = null;
	//maak connectie
	public ConnectDBAuto(Connection c){
		con = c;
	}
	
	//alle autos in het systeem
	public ArrayList<Auto> getAutos(){
		ArrayList<Auto> terug = new ArrayList<Auto>();
		try{
			String sql = "SELECT * FROM Auto WHERE actief='t'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant eigenaar = klantconn.zoekEigenaar(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(id);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAutoObject(a);
				terug.add(a);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij ophalen autos" + ex);
		}
		return terug;
	}

	//zoek naar alle autos van een bepaalde klant (per klantid)
	public ArrayList<Auto> getAutosVan(int zoekid){
		ArrayList<Auto> terug = new ArrayList<Auto>();
		try{
			String sql = "SELECT * FROM Auto WHERE actief='t' AND klantid=" + zoekid + ";";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant eigenaar = klantconn.zoekEigenaar(zoekid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(id);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAutoObject(a);
				terug.add(a);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij ophalen autos van klant" + ex);
		}
		return terug;
	}

	//zoek naar alle autos van een bepaalde klant (per klant-object)
	public ArrayList<Auto> getAutosVan(Klant k){
		ArrayList<Auto> terug = new ArrayList<Auto>();
		try{
			String sql = "SELECT * FROM Auto WHERE actief='t' AND klantid=" + k.getKlantnummer() + ";";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				Auto a = new Auto(ken, mk, tp, k);
				a.setID(id);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAutoObject(a);
				terug.add(a);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij ophalen autos van klant" + ex);
		}
		return terug;
	}
	
	//zoek auto op id
	public Auto zoekAuto(int autoid){
		Auto terug = null;
		try{
			String sql = "SELECT * FROM Auto WHERE autoid=" + autoid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant eigenaar = klantconn.zoekEigenaar(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(autoid);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAutoObject(a);
				String actief = rs.getString("actief");
				if(actief.equals("f")){
					a.setActief(false);
				}
				terug = a;
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij zoeken naar auto" + ex);
		}
		return terug;		
	}
	
	//zoek auto op kenteken
	public ArrayList<Auto> zoekAutoKenteken(String kenteken){
		ArrayList<Auto> terug = new ArrayList<Auto>();
		try{
			String sql = "SELECT * FROM Auto WHERE kenteken LIKE'%" + kenteken + "%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int autoid = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant eigenaar = klantconn.zoekEigenaar(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(autoid);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAutoObject(a);
				String actief = rs.getString("actief");
				if(actief.equals("f")){
					a.setActief(false);
				}
				terug.add(a);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij zoeken naar auto" + ex);
		}
		return terug;		
	}
	
	//zoek auto op type
	public ArrayList<Auto> zoekAutoType(String type){
		ArrayList<Auto> terug = new ArrayList<Auto>();
		try{
			String sql = "SELECT * FROM Auto WHERE type LIKE '%" + type + "%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int autoid = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant eigenaar = klantconn.zoekEigenaar(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(autoid);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAutoObject(a);
				String actief = rs.getString("actief");
				if(actief.equals("f")){
					a.setActief(false);
				}
				terug.add(a);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij zoeken naar auto" + ex);
		}
		return terug;		
	}
	
	//zoek auto's op merk
	public ArrayList<Auto> zoekAutoMerk(String merk){
		ArrayList<Auto> terug = new ArrayList<Auto>();
		try{
			String sql = "SELECT * FROM Auto WHERE merk LIKE'%" + merk + "%'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int autoid = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant eigenaar = klantconn.zoekEigenaar(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(autoid);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAutoObject(a);
				String actief = rs.getString("actief");
				if(actief.equals("f")){
					a.setActief(false);
				}
				terug.add(a);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij zoeken naar auto" + ex);
		}
		return terug;		
	}
	
	//zoek auto op id
	public Auto zoekAutoZonderKlussen(int autoid){
		Auto terug = null;
		try{
			String sql = "SELECT * FROM Auto WHERE autoid=" + autoid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant eigenaar = klantconn.zoekEigenaar(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(autoid);
				terug = a;
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij zoeken naar auto" + ex);
		}
		return terug;		
	}
	
	//maak nieuwe auto. id wordt automatisch toegewezen. geeft auto-object terug zodat je het id weet.
	public Auto nieuweAuto(String kenteken, String merk, String type, Klant eigenaar){
		Auto terug = null;
		try{
			String sql = "INSERT INTO Auto (kenteken, merk, type, klantid, actief) VALUES ('" + kenteken + "', '" + merk + "', '" + type + "', " + eigenaar.getKlantnummer() + ", 't')";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			String sql2 = "SELECT MAX(autoid) FROM Auto";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int autoid = 0;
			while(rs.next()){
				autoid = rs.getInt(1);		
			}
			stmt2.close();
			terug = zoekAuto(autoid);
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuwe auto maken" + ex);
		}
		return terug;
	}

	//zet alle waardes van auto in database naar waardes van ingevoerd auto-object. met uitzondering van id. 
	public boolean updateAuto(Auto a){
		try{
			String sql = "UPDATE Auto SET kenteken='" + a.getKenteken() + "',  merk='" + a.getMerk() + 
					"', type='" + a.getType() + "' WHERE autoid = " + a.getID();
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);	
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij auto updaten" + ex);
		}
		return false;
	}
	
	//zet auto op non-actief
	public boolean verwijderAuto(int autoid){
		try{
			String sql = "UPDATE Auto SET actief='f' WHERE autoid=" + autoid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij auto verwijderen " + ex);
		}
		return false;
	}
	
	//zet autos van een klant op non-actief
	public boolean verwijderAutosVan(int klantid){
		try{
			String sql = "UPDATE Auto SET actief='f' WHERE klantid=" + klantid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij auto verwijderen " + ex);
		}
		return false;
	}
	
	//zet auto op actief
	public boolean activeerAuto(int autoid){
		try{
			String sql = "UPDATE Auto SET actief='t' WHERE autoid=" + autoid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij auto verwijderen " + ex);
		}
		return false;
	}
	
	//zet autos van een klant op actief
	public boolean activeerAutosVan(int klantid){
		try{
			String sql = "UPDATE Auto SET actief='t' WHERE klantid=" + klantid;
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		}
		catch(Exception ex){
			System.out.println("Probleem bij auto verwijderen " + ex);
		}
		return false;
	}
}