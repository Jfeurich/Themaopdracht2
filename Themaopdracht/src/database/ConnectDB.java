package database;

public abstract class ConnectDB {
	
	protected final static String DB_DRIV = "com.mysql.jdbc.Driver";
	protected String databaseURL = "jdbc:mysql://localhost:3306/practicum1db";
	
	public ConnectDB() {
		try{
			Class.forName(DB_DRIV).newInstance();
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	//standaard methodes (kan ze niet abstract maken omdat het verschillende soorten objecten zijn :S
	//ArrayList<Class> getAlles
	//Class zoekClass
	//Class nieuweClass
	//boolean updateClass
	//boolean verwijderClass
}
