package domeinklassen;

public class User {
	private String gebruikersnaam, wachtwoord, email;
	private Klant deKlant;
	private int id, type;
	
	//types: 0) administratie, 1) monteur, 2) jopie, en 3) klanten
	
	public User(int id, int tp, String unm, String pw, String email){
		type = tp;
		gebruikersnaam = unm;
		wachtwoord = pw;
		this.email = email;
	}
	
	public int getID(){
		return id;
	}

	public int getType() {
		return type;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String mail){
		email = mail;
	}
	

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public void setGebruikersnaam(String username) {
		this.gebruikersnaam = username;
	}

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String password) {
		this.wachtwoord = password;
	}

	public Klant getDeKlant() {
		return deKlant;
	}

	public void setDeKlant(Klant deKlant) {
		this.deKlant = deKlant;
	}

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
