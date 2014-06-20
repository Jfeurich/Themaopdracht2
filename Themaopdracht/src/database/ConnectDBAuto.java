package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.Auto;
import domeinklassen.Klant;
/**	
*	Dit is klasse ConnectDBAuto.
*	Deze klasse haalt alle database informatie op in relatie met het object Auto.
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class ConnectDBAuto{
	/**
	 * Variabele con, type Connection.
	 * Deze variabele wordt aangeroepen als er een SQL statement uigevoerd moet worden.
	 */
	private Connection con = null;
	//maak connectie
	/**
	 * Constructor ConnectDBAuto.
	 * Dit is de constructor van de ConnectDBAuto klasse.
	 * @param c		de connectie met de database wordt opgeslagen in de klasse
	 */
	public ConnectDBAuto(Connection c){
		con = c;
	}
	
	//alle autos in het systeem
	/**
	 * Methode getAutos.
	 * Deze methode haalt alle autos uit het systeem op.
	 * @return terug	Een ArrayList met alle autos 
	 */
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

	//alle nonactieve autos in het systeem
	/**
	 * Methode getAutosNietActief.
	 * Deze methode geeft alle autos die niet actief zijn.
	 * @return terug	Een ArrayList met alle niet-actieve autos 
	 */
	public ArrayList<Auto> getAutosNietActief(){
		ArrayList<Auto> terug = new ArrayList<Auto>();
		try{
			String sql = "SELECT * FROM Auto WHERE actief='f'";
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
	/**
	 * Methode getAutosVan.
	 * Deze methode haalt alle autos van een specefieke klant op, gezocht op klantID.
	 * @param zoekid	Het id van de gezochte klant
	 * @return terug	Een ArrayList met alle autos van de gezochte klant
	 */
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
			System.out.println("Probleem bij ophalen autos van klant per ID" + ex);
		}
		return terug;
	}

	//zoek naar alle autos van een bepaalde klant (per klant-object)
	/**
	 * Methode getAutosVan.
	 *  Deze methode haalt alle autos van een specefieke klant op, gezocht op klantobject.
	 * @param k		Het klant object van de klant
	 * @return	terug	Een ArrayList met alle autos van de klant
	 */
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
			System.out.println("Probleem bij ophalen autos van klant-object" + ex);
		}
		return terug;
	}
	/**
	 * Methode zoekAuto.
	 * Deze methode zoekt een auto op basis van autoID.
	 * @param autoid	ID van de gezochte auto
	 * @return	terug	De gezochte auto, als de auto niet gevonden is wordt null terug gegeven
	 */
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
			System.out.println("Probleem bij zoeken naar auto op ID" + ex);
		}
		return terug;		
	}
	
	//zoek auto op kenteken
	/**
	 * Methode zoekAutoKenteken.
	 * Deze methode zoekt de autos op basis van kenteken.
	 * @param kenteken	Kenteken van de gezochte auto(s)
	 * @return	terug	Alle autos met kentekens die de ingevoerde waarde bevatten
	 */
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
			System.out.println("Probleem bij zoeken naar auto op kenteken" + ex);
		}
		return terug;		
	}
	
	//zoek auto op type
	/**
	 * Methode zoekAutoType.
	 * Deze methode zoekt autos op basis van het type.
	 * @param type	Type van de gezochte auto(s)
	 * @return	terug	Alle autos met types die de ingevoerde waarde bevatten
	 */
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
			System.out.println("Probleem bij zoeken naar auto op type" + ex);
		}
		return terug;		
	}
	/**
	 * Methode zoekAutoMerk.
	 * Deze methode zoekt autos op basis van het merk.
	 * @param merk	Merk van de gezochte auto(s)
	 * @return	terug	Alle autos met merken die de ingevoerde waarde bevatten
	 */	
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
			System.out.println("Probleem bij zoeken naar auto op merk" + ex);
		}
		return terug;		
	}
	/**
	 * Methode zoekAutoZonderKlussen.
	 * Deze methode zoekt de auto die geen klus heeft.
	 * @param autoid	AutoID van gezochte auto
	 * @return	terug	Het gezochte auto-object, als deze niet is gevonden wordt null teruggegeven
	 */
	//zoek auto op id (voor aanroep vanuit ConnectDBKlus)
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
			System.out.println("Probleem bij zoeken naar auto zonder klussen" + ex);
		}
		return terug;		
	}
	
	//maak nieuwe auto. id wordt automatisch toegewezen. geeft auto-object terug zodat je het id weet.
	/**
	 * Methode nieuweAuto.
	 * Deze methode voegt een nieuwe Auto toe aan de database.
	 * @param kenteken	kenteken van de nieuwe auto
	 * @param merk	merk van de nieuwe auto
	 * @param type	type  van de nieuwe auto
	 * @param eigenaar eigenaar  van de nieuwe auto
	 * @return terug	De nieuwe auto wordt als object teruggegeven
	 */
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
	
	//zet auto op non-actief
	/**
	 * Methode verwijderAuto.
	 * Deze methode zet een auto op non-actief in het systeem.
	 * Dit wordt gedaan als een auto niet gebruikt wordt.
	 * @param autoid	AutoID van de auto die op non-actief gezet moet worden
	 * @return	boolean	Er wordt aangegeven of de auto succesvol op non-actief gezet kon worden
	 */
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
	/**
	 * Methode verwijderAutosVan.
	 * Deze methode zet alle auto's van een klant op non-actief.
	 * @param klantid	KlantID van de klant waarvan alle autos op non-actief moeten
	 * @return boolean	Er wordt aangegeven of de autos van de klant succesvol op non-actief zijn gezet
	 */
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
	/**
	 * Methode activeerAuto.
	 * Deze methode wordt gebruikt om non-actieve autos weer actief te maken.
	 * @param autoid	AutoID van de auto die op actief moet worden gezet
	 * @return	boolean	Er wordt aangegeven of de auto succesvol op actief gezet is
	 */
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
	/**
	 * Methode activeerAutosVan.
	 * Deze methode zet alle auto's van een klant op actief.
	 * @param klantid	KlantID van de klant waarvan alle autos op actief moeten
	 * @return boolean	Er wordt aangegeven of de autos van de klant succesvol op actief zijn gezet
	 */
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