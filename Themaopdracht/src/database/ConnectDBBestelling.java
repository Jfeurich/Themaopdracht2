package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;
import domeinklassen.Product;

public class ConnectDBBestelling extends ConnectDB{
	//maak connectie
	public ConnectDBBestelling(){
		super();
	}
	public Bestelling nieuwBestelling(Bestelling deBestelling){
		Bestelling terug = null;
		try{			
			Connection con = DriverManager.getConnection(databaseURL, "root", "");
			//maak een bestelling aan
			Calendar now = Calendar.getInstance();
			String sql1 = "INSERT INTO Bestelling (datum) VALUES (now);";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql1);
			stmt.close();
			//de zojuist gemaakt bestelling zoeken
			String sql2 = "SELECT MAX(bestellingid) as bestellingid FROM BESTELLING";
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery(sql2);
			rs.next();
			int bestellingid = rs.getInt("bestellingid");			
			stmt2.close();
			//de producten aan de bestelling toevoegen
			String sql3 = "INSERT INTO BesteldProduct (hoeveelheid, productid, bestellingid) VALUES ( ?, ?, " + bestellingid + ")";
			PreparedStatement pstmt = con.prepareStatement(sql3);
			for(BesteldProduct bp: deBestelling.getBesteldeProducten()){
				pstmt.setInt(1, bp.getHoeveelheid());
				pstmt.setInt(2, bp.getProduct().getArtikelNr());
				pstmt.executeUpdate();
			}
			pstmt.close();
			con.close();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return terug;
	}
}
