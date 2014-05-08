package domeinklassen;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Bedrijf{
	private double btw;
	private String file = "bedrijf.txt";
	private ArrayList<Reservering> deReserveringen;
	private ArrayList<Klant> deKlanten;
	private ArrayList<Klus> deKlussen;
	private ArrayList<Product> deVoorraad;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	public Bedrijf(double b){
		btw = b;
		deReserveringen = new ArrayList<Reservering>();
		deKlanten = new ArrayList<Klant>();
		deKlussen = new ArrayList<Klus>();
		deVoorraad = new ArrayList<Product>();
	}

	public double getBTW(){
		return btw;
	}

	public String getFile(){
		return file;
	}

	public ArrayList<Klus> getKlussen(){
		return deKlussen;
	}
	public Klant getKlant(String nm){
		Klant k = null;
		for(Klant kl: deKlanten){
			if(kl.getNaam().equalsIgnoreCase(nm)){
				k = kl;
			}
		}
		return k;
	}
	//overbodig ?
	public boolean heeftKlant(String kN) {
		boolean b = false;
		for (Klant k : deKlanten) {
			if (k.getNaam().equals(kN)) {
				b = true;
			}
		}
		return b;
	}
	public void voegProductToe(Product newP){
		deVoorraad.add(newP);
	}
	public ArrayList<GebruiktProduct> getAlleOnderdelen(Klus k){
		return k.gebruikteProducten;
	}

	public void voegKlusToe(Klus newK){
		deKlussen.add(newK);
	}
	public void voegKlantToe(Klant newK){
		deKlanten.add(newK);
	}
	public ArrayList<Klant> getAlleKlanten(){
		return deKlanten;
	}
	public Klant vindKlant(Auto a){
		Klant k = null;
		for(Klant ka: deKlanten){
			for(Auto aa: ka.getAutos()){
				if(aa.getKenteken().equalsIgnoreCase(a.getKenteken())){
					k = ka;
				}
			}
		}
		return k;
	}
	public ArrayList<Klus> getKlussenZF(){
		ArrayList<Klus> klussenZonderFactuur = new ArrayList<Klus>();
		for (Klus k: deKlussen){
			if(k.getFactuur() == null && k.getStatus().equalsIgnoreCase("voltooid")){
				klussenZonderFactuur.add(k);
			}
		}
		return klussenZonderFactuur;
	}
	public Date maakDatum(int dag, int maand, int jaar) throws Exception{
		String datum;
		if(dag >= 1 && dag <= 9){
			datum = "0" + dag;
		}
		else{
			datum = dag + "";
		}
		datum += "-";
		if(maand >= 1 && maand <= 9){
			datum += "0" + maand;
		}
		else{
			datum += maand;
		}
		datum += "-" + jaar;
		return df.parse(datum);
	}
	public Klus heeftKlus(String kenteken){
		Klus kl = null;
		for (Klus k: deKlussen){
			if (k.getAuto().getKenteken().equalsIgnoreCase(kenteken)){
				kl = k;
			}
		}
		return kl;
	}

	public ArrayList<Reservering> getAlleReserveringen(){
		return deReserveringen;
	}
	public void voegReserveringToe(Reservering newR){
		deReserveringen.add(newR);
	}
	public Reservering getReservering(String kt){
		Reservering r = null;
		for (Reservering re: deReserveringen){
			if(re.getAuto().getKenteken().equalsIgnoreCase(kt)){
				r = re;
			}
		}
		return r;
	}

	public void slaop(){
		try{
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
		}
		catch(Exception e){
			System.out.println("Error: " + e);
		}
	}

	public ArrayList<Product> getDeVoorraad() {
		return deVoorraad;
	}
}
