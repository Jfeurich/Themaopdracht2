package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import domeinklassen.GebruiktProduct;
import domeinklassen.Product;
/**	
*	Dit is klasse ConnectDBGebruiktProduct.
*	Deze klasse haalt alle database informatie op in relatie met het object Auto.
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class ConnectDBGebruiktProduct{
	/**
	 * Variabele con, type Connection.
	 * Deze variabele wordt aangeroepen als er een SQL statement uigevoerd moet worden.
	 */
	private Connection con = null;
	/**
	 * Constructor ConnectDBGebruiktProduct
	 * Dit is de constructor van de ConnectDBGebruiktProduct klasse.
	 * @param c		de connectie met de database wordt opgeslagen in de klasse
	 */
	public ConnectDBGebruiktProduct(Connection c){
		con = c;
	}
	/**
	 * Methode getProductenVanKlus.
	 * Deze methode haalt alle gebruikteproducten van de ingeoverde KlusID op.
	 * @param klusid	KlusID van de gezochte klus
	 * @return terug	Een ArrayList met alle gebruikte producten van de gezochte KlusID
	 */
	public ArrayList<GebruiktProduct> getProductenVanKlus(int klusid){
		ArrayList<GebruiktProduct> terug = new ArrayList<GebruiktProduct>();
		try{
			String sql = "SELECT * FROM GebruiktProduct WHERE klusid=" + klusid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				int gpid = rs.getInt("gebruiktproductid");
				int pid = rs.getInt("productid");
				int a = rs.getInt("aantal");
				GebruiktProduct gp = new GebruiktProduct(gpid, a);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				gp.setHetProduct(pconn.zoekProduct(pid));
				terug.add(gp);
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij producten van klus ophalen " + ex);
		}
		return terug;
	}	
	
	/**
	 * Methode zoekGebruiktProduct.
	 * Deze methode zoekt een GebruiktProduct-object op basis van het GebruiktProductID.
	 * @param gpid	GebruiktProductID van het gezochte gebruikte product
	 * @return terug	Het gezochte GebruiktProduct-object. Als deze niet is gevonden wordt null gereturned
	 */
	public GebruiktProduct zoekGebruiktProduct(int gpid){
		GebruiktProduct terug = null;
		int pid = 0;
		try{
			String sql = "SELECT * FROM GebruiktProduct WHERE gebruiktproductid=" + gpid;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {   // rs.next() geeft false als er niets meer is 
				pid = rs.getInt("productid");
				int a = rs.getInt("aantal");
				terug = new GebruiktProduct(gpid, a);
				ConnectDBProduct pconn = new ConnectDBProduct(con);
				terug.setHetProduct(pconn.zoekProduct(pid));
			}
			stmt.close();
		}
		catch(Exception ex){
			System.out.println("Probleem bij gebruiktproduct zoeken " + ex);
		}
		return terug;
	}
	
	/**
	 * Methode nieuwGebruiktProduct.
	 * Deze methode maakt een nieuw GebruiktProduct-object aan.
	 * @param klusid	KlusID van het nieuwe gebruikte product
	 * @param productid	ProductID wat aangeeft welk product het nieuwe gebruikte product is
	 * @param aantal	Aantal van het nieuwe gebruikte product
	 * @return terug	Het nieuwe GebruiktProduct-object wordt gereturned. Als het niet is gelukt om het nieuwe GebruiktProduct-object aan temaken wordt null gereturned
	 */
	public GebruiktProduct nieuwGebruiktProduct(int klusid, int productid, int aantal){
		GebruiktProduct terug = null;
		try{			
			//maak nieuw product met gegeven waarden
			String sql = "INSERT INTO GebruiktProduct (klusid, productid, aantal) VALUES (" + klusid + ", " + productid + ", " + aantal + ");";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			String sql2 = "SELECT MAX(gebruiktproductid) FROM GebruiktProduct";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			int gpid = 0;
			while(rs.next()){
				gpid = rs.getInt(1);		
			}
			stmt2.close();
			ConnectDBProduct pcon = new ConnectDBProduct(con);
			Product p = pcon.zoekProduct(productid);
			p.setAantal(p.getAantal()-aantal);
			pcon.updateProduct(p);
			terug = zoekGebruiktProduct(gpid);	
		}
		catch(Exception ex){
			System.out.println("Probleem bij nieuw gebruiktproduct" + ex);
		}
		return terug;
	}
}
