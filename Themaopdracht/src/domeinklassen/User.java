package domeinklassen;

/**	
*	Dit is klasse User
*	Deze klasse zorgt ervoor dat de gebruikers accounts hebben om op de
*	website in te loggen. Deze klasse koppelt User-objecten aan Klant-objecten.
*	@author Team3SoftwareDevelopment
*	@version 1.0
**/
public class User {
	/**
	*	De naam van de gebruiker
	**/
	private String gebruikersnaam;
	/**
	*	Het wachtwoord van de gebruiker
	**/
	private String wachtwoord;
	/**
	*	De email van de gebruiker
	**/
	private String email;
	/**
	*	Het gekoppelde Klant-Object als dat aanwezig is
	**/
	private Klant deKlant;
	/**
	*	Het ID van de gebruiker in de database
	**/
	private int id;
	/**
	*	Het type van de gebruiker
	**/
	private int type;
	
	/**
	*	Constructor voor User.
	*	@param id	Dit is het ID van de user in de database
	*	@param tp 	Dit is het type can de  user 
	*	types: 0) administratie, 1) monteur, 2) jopie, en 3) klanten
	*	@param 		gebruikersnaam Dit is de gebruikersnaam van de User
	*	@param 		wachtwoord Dit is het wachtwoord van de User
	*	@param 		email Dit is het emailadres van de User
	**/	
	public User(int id, int tp, String unm, String pw, String email){
		this.id = id;
		type = tp;
		gebruikersnaam = unm;
		wachtwoord = pw;
		this.email = email;
	}
	/**
	*	Deze methode returnt de ID van een Useraccount. 
	*	@return integer ID
	**/	
	public int getID(){
		return id;
	}
	/**
	*	Deze methode returnt het type van een Useraccount. 
	*	@return integer type
	**/	
	public int getType() {
		return type;
	}
	/**
	*	Deze methode returnt het emailadres van een Useraccount. 
	*	@return integer type
	**/	
	public String getEmail(){
		return email;
	}
	/**
	*	Deze methode verandert het emailadres van een Useraccount.
	*	@param String email 
	**/	
	public void setEmail(String mail){
		email = mail;
	}
	
	/**
	*	Deze methode returnt de gebruikersnaam van een Useraccount. 
	*	@return String gebruikersnaam;
	**/	
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}
	/**
	*	Deze methode returnt het wachtwoord van een Useraccount. 
	*	@return String wachtwoord
	**/	
	public String getWachtwoord() {
		return wachtwoord;
	}
	/**
	*	Deze methode verandert het wachtwoord van een Useraccount. 
	*	@param String wachtwoord
	**/	
	public void setWachtwoord(String password) {
		this.wachtwoord = password;
	}
	/**
	*	Deze methode returnt de Klant gekoppeld aan een Useraccount. 
	*	@return Klant deKlant
	**/	
	public Klant getDeKlant() {
		return deKlant;
	}
	/**
	*	Deze methode Koppelt een Klant Object aan een User Object
	*	@param Klant de Klant
	**/	
	public void setDeKlant(Klant deKlant) {
		this.deKlant = deKlant;
		deKlant.setUser(this);
	}
	/**
	*	Deze methode Returnt alle gegevens van een useraccount geformatteerd tot een String. 
	*	@return String s 
	**/	
	@Override
	public String toString() {
		String s = "User username=" + gebruikersnaam + ", password=" + wachtwoord + "\n";
		switch(type){
			case 0: s += "Dit is een administratieve account"; break;
			case 1: s += "Deze account is van een monteur"; break;
			case 2: s += "Deze account is van een garagebeheerder"; break;
			case 3: s += "Account is van klant:\n" + deKlant.toString(); break;
		}
		return s;
	}
	
}
