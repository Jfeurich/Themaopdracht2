package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.Klus;

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
			String sql = "SELECT * FROM Auto";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				int klantid = rs.getInt("klantid");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant eigenaar = klantconn.zoekKlant(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(id);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAuto(a);
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
			String sql = "SELECT * FROM Auto WHERE klantid=" + zoekid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int id = rs.getInt("autoid");
				String ken = rs.getString("kenteken");
				String mk = rs.getString("merk");
				String tp = rs.getString("type");
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				Klant eigenaar = klantconn.zoekKlant(zoekid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(id);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAuto(a);
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
			String sql = "SELECT * FROM Auto WHERE klantid=" + k.getKlantnummer();
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
				klusconn.getKlussenVoorAuto(a);
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
				Klant eigenaar = klantconn.zoekKlant(klantid);
				Auto a = new Auto(ken, mk, tp, eigenaar);
				a.setID(autoid);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.getKlussenVoorAuto(a);
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
			String sql = "INSERT INTO Auto (kenteken, merk, type, klantid) VALUES ('" + kenteken + "', '" + merk + "', '" + type + "', " + eigenaar.getKlantnummer() + ")";
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
	
	//verwijderd tabelrij met ingevoerd autoid
	public boolean verwijderAuto(int autoid){
		try{
			//verwijder eerst alle klussen van deze auto
			ConnectDBKlus kconn = new ConnectDBKlus(con);
			ArrayList<Klus> deKlussen = kconn.getKlussenVoorAuto(autoid);
			for(Klus k : deKlussen){
				kconn.verwijderKlus(k.getID());
			}
			//en vervolgens de auto zelf
			String sql = "DELETE FROM Auto WHERE autoid=" + autoid;
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