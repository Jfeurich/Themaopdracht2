package voorraadbeheer;

import domeinklassen.*;

import java.util.*;

public class Main
{
	public static void main(String[] arg)
	{
		ArrayList<Product> deVoorraad = new ArrayList<Product>();
		ArrayList<Bestelling> deBestellingen = new ArrayList<Bestelling>();
		HoofdFrame hf = new HoofdFrame(deVoorraad, deBestellingen);
		hf.setVisible(true);
	}
}