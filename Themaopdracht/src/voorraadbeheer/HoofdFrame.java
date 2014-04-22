package voorraadbeheer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import domeinklassen.Bestelling;
import domeinklassen.Product;

public class HoofdFrame extends JFrame implements ActionListener {
	private JButton b1, b2, b3;
	private ArrayList<Product> deVoorraad;
	private ArrayList<Bestelling> deBestellingen;
	public HoofdFrame(ArrayList<Product> dV, ArrayList<Bestelling> dB) {
		deVoorraad = dV;
		deBestellingen = dB;
		setLayout(new FlowLayout());
		b1 = new JButton("Voorraad"); add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Producten die besteld moeten worden"); add(b2);
		b2.addActionListener(this);
		b3 = new JButton("Bestelling"); add(b3);
		b3.addActionListener(this);
		pack(); setTitle("Menu");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		//menu met de knoppen product, producten onder minimum voorraad en bestelling
		if (event.getSource() == b1) {
			ProductFrame pf = new ProductFrame(deVoorraad, this);
			this.setVisible(false);
			pf.setVisible(true);
		}
		else if (event.getSource() == b2) {
			String s = "Producten die onder de minimum voorraad zitten: ";
			for(Product p: deVoorraad){
				if(p.getAantal() < p.getMinimumAanwezig()){
					s += "\n" + p;
				}
			}
			JOptionPane.showMessageDialog(null, s, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
		}
		else if (event.getSource() == b3) {
			BestellingFrame bf = new BestellingFrame(deVoorraad, deBestellingen, this);
			this.setVisible(false);
			bf.setVisible(true);
		}
	}
}