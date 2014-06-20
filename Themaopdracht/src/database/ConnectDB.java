/**	
*	Dit is klasse ConnectDB
*	Deze klasse maakt de connectie met de database.
*	Een variabele van deze klasse wordt aangeroepen door alle andere ConnectDB... klassen
*	Deze klasse fungeert als brug tussen de javacode en MySQL database
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/

package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	/**
	 * Variabele DB_Driv, type String
	 * Deze variabele geeft de driver van de database weer 
	 */
	protected final static String DB_DRIV = "com.mysql.jdbc.Driver";
	/**
	 * Variabele databaseURL, type String
	 * Deze variabele geeft de url naar de database weer
	 */
	protected String databaseURL = "jdbc:mysql://localhost:3306/ThemaopdrachtDB";
	
	/**
	 * Constructor ConnectDB
	 * Dit is de constructor voor ConnectDB
	 * Hierin wordt de driver van de database gemaakt
	 */
	public ConnectDB() {
		try{
			Class.forName(DB_DRIV).newInstance();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	/**
	 * Methode maakVerbinding
	 * Deze methode wordt aangeroepen als er connectie wordt gelegd met de database
	 * @return con	De gemaakte connectie zodat hierop SQL statements uitgevoerd kunnen worden
	 */
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
	
	/**
	 * Methode sluitVerbinding
	 * Deze methode sluit de verbinding met de database.
	 * Deze methode wordt aangeroepen als een sessie van een user wordt beëindigt
	 * Hierdoor verdwijnen connecties die niet gebruikt worden
	 * @param con	De connectie die gesloten moetn worden
	 */
	public void sluitVerbinding(Connection con){
		try{
			con.close();
		}
		catch(Exception ex){
			System.out.println("Error bij sluiten van database! " + ex);
		}
	}
}
