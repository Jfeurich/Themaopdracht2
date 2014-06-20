package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;
/**	
*	Dit is klasse ConnectDBBesteldProduct
*	Deze klasse haalt alle database informatie op in relatie met het object BesteldProduct
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class ConnectDBBesteldProduct{
	/**
	 * Variabele con, type Connection
	 * Deze variabele wordt aangeroepen als er een SQL statement uigevoerd moet worden
	 */
	private Connection con = null;
	/**
	 * Constructor ConnectDBBesteldProduct.
	 * Dit is de constructor van de ConnectDBBesteldProduct klasse.
	 * @param c		de connectie met de database wordt opgeslagen in de klasse
	 */
	public ConnectDBBesteldProduct(Connection c){
		con = c;;
	}
	
	//alle producten van een bestelling
	/**
	 * Methode getProductenVanBestelling.
	 * Deze methode haalt alle producten op van de ingevoerde bestelling.
	 * De producten worden in het ingevoerde Bestelling-object gezet.
	 * Hierdoor is er geen return statement.
	 * @param b	Het bestelling-object waarop gezocht moet worden
	 */
	public void getProductenVanBestelling(Bestelling b){
		ArrayList<BesteldProduct> terug = new ArrayList<BesteldProduct>();
		try{
			String sql = "SELECT * FROM BesteldProduct WHERE bestellingid=" + b.getBestelNummer();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int bpid = rs.getInt("besteldproductid");
				int pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				BesteldProduct bp = new BesteldProduct(bpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				bp.setProduct(pconn.zoekProduct(pid));
				terug.add(bp);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij producten van bestelling ophalen " + ex);
		}
		b.setBesteldeProducten(terug);
	}
	
	//zoek specifiek besteld product
	/**
	 * Methode zoekBesteldProduct.
	 * Deze methode zoekt alle het bestelde product bij het bestelde product ID
	 * @param bpid	Het besteldproduct ID van het gezocht BesteldProduct
	 * @return	terug	Het gezocht BesteldProduct-object. Als deze niet gevonden is, wordt er null teruggegeven
	 */
	public BesteldProduct zoekBesteldProduct(int bpid){
		BesteldProduct terug = null;
		int pid = 0;
		try{
			String sql = "SELECT * FROM BesteldProduct WHERE besteldproductid=" + bpid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				pid = rs.getInt("productid");
				int h = rs.getInt("hoeveelheid");
				terug = new BesteldProduct(bpid, h);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				terug.setProduct(pconn.zoekProduct(pid));
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij besteldproduct zoeken " + ex);
		}
		return terug;
	}
	
	//maak besteldproduct aan
	/**
	 * Methode nieuwBesteldProduct.
	 * Deze methode voegt een nieuw BesteldProduct toe.
	 * @param bestellingid	BestellingID waaraan het product moet worden toegevoegd
	 * @param productid	ProductID van het bestelde product
	 * @param hoeveelheid	Hoeveelheid van het bestelde product
	 * @return terug	Het nieuwe bestelde product wordt gereturned
	 */
	public BesteldProduct nieuwBesteldProduct(int bestellingid, int productid, int hoeveelheid){
		BesteldProduct terug = null;
		try{			
			//maak nieuw product met gegeven waarden
			String sql = "INSERT INTO BesteldProduct (bestellingid, productid, hoeveelheid) VALUES (" + bestellingid + ", " + productid + ", " + hoeveelheid + ");";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			//zoek meest recente product (hoogste id) momenteel in database (dat is degene die we net toe hebben gevoegd)
			String sql2 = "SELECT MAX(besteldproductid) FROM BesteldProduct";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int bpid = 0;
			while(rs.next()){
				bpid = rs.getInt(1);		
			}
			stmt2.close();
			//zoek product op basis van gevonden klantnummer
			terug = zoekBesteldProduct(bpid);	
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuw besteldproduct" + ex);
		}
		return terug;
	}
}
