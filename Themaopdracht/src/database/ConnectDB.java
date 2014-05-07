package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	
	protected final static String DB_DRIV = "com.mysql.jdbc.Driver";
	protected String databaseURL = "jdbc:mysql://localhost:3306/ThemaopdrachtDB";
	
	public ConnectDB() {
		try{
			Class.forName(DB_DRIV).newInstance();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	public Connection maakVerbinding(){
		Connection con = null;
		try{
			con = DriverManager.getConnection(databaseURL, "root", "");
		}
		catch(Exception ex){
			System.out.println("Error bij verbinding met database! " + ex);
		}
		return con;
	}
	
	public void sluitVerbinding(Connection con){
		try{
			con.close();
		}
		catch(Exception ex){
			System.out.println("Error bij sluiten van database! " + ex);
		}
	}
}
