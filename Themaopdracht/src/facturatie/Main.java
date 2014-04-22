package facturatie;

import domeinklassen.*;

public class Main {

	public static void main(String[] args) throws Exception{
		Bedrijf b = new Bedrijf(19.0);
		Klant k = new Klant("Piet snot", "Ergensweg 10", "Duckstad", "01234567", 12345678);
		Klant k2 = new Klant("Kees Keesma", "Nergensweg 11", "Eiburg", "12345678", 23456789);
		Klant k3 = new Klant("Harry van Harrystein","Harryweg","Harryville","77777777",77777777);
		b.voegKlantToe(k);
		Auto a1 = new Auto("11QQ11", "Fiat", "Panda", k);
		Auto a2 = new Auto("22ww22", "Fiat", "Punto", k);
		Auto a3 = new Auto("33ee33","Ferrari","Testarossa",k2);
		Auto a4 = new Auto("ww33ww","Lamborghini","Murcielago SV670LP",k3);
		Klus rep1 = new Reparatie("01-01-2014", "Uitlaat is kapot", a1);
		b.voegKlusToe(rep1);
		rep1.setStatus("onvoltooid");rep1.addManuren(10);
		Klus rep2 = new Reparatie("02-01-2014","Auto is te rood",a3);
		b.voegKlusToe(rep2);
		rep2.setStatus("Voltooid");rep2.addManuren(50);
		Klus ond1 = new Onderhoudsbeurt("02-01-2014", "APK", a2);
		ond1.setStatus("onvoltooid");b.voegKlusToe(ond1);
		Klus ond2 = new Onderhoudsbeurt("03-01-2014","100.000KM",a4);
		ond2.setStatus("Voltooid");b.voegKlusToe(ond2);
		Product p1 = new Product("moer",1,1,"los",10.00);b.voegProductToe(p1);
		Product p2 = new Product("bout",1,1,"los",50.00);b.voegProductToe(p2);
		Product p3 = new Product("Schroef",1,1,"tiental",25.00);b.voegProductToe(p3);
		HoofdFrame hf = new HoofdFrame(b);
		hf.setVisible(true);
	}

}
