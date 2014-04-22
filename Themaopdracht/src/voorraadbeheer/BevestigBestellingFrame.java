package voorraadbeheer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;
import domeinklassen.Product;

public class BevestigBestellingFrame extends JFrame implements ActionListener {
	private ArrayList<Product> deVoorraad;
	private ArrayList<Bestelling> deBestellingen;
	private Bestelling deBestelling;
	private BestellingFrame bf;
	private JButton b1, b2;
	private JLabel nummerLabel, leegLabel1;
	private JTextField nummerVeld;
	public BevestigBestellingFrame(ArrayList<Product> dV, ArrayList<Bestelling> dB, BestellingFrame bestellingFrame) {
		deVoorraad = dV;
		deBestellingen = dB;
		bf = bestellingFrame;
		deBestelling = null;
		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(2, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		nummerLabel = new JLabel("Bestelnummer:"); hulp.add(nummerLabel);
		nummerVeld = new JTextField(10); hulp.add(nummerVeld);
		leegLabel1 = new JLabel(""); hulp.add(leegLabel1);
		b1 = new JButton("Bevestig bestelling"); hulp.add(b1);
		b1.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b2 = new JButton("Sluit"); add(b2, BorderLayout.SOUTH);
		b2.addActionListener(this);
		pack(); setTitle("Beheer bestelling");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == b1) {
			if(!nummerVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
				JOptionPane.showMessageDialog(null, "Geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//de bestelling wordt gezocht
				Bestelling b = null;
				for(Bestelling be: deBestellingen){
					if(be.getBestelNummer() == Integer.parseInt(nummerVeld.getText())){
						b = be;
					}
				}
				if(b == null){
					JOptionPane.showMessageDialog(null, "Bestelling niet gevonden!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//check of de bestelling nog niet geleverd is
					if(b.getIsGeleverd() == true){
						JOptionPane.showMessageDialog(null, "Bestelling is al geleverd!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					else{
						//de voorraad wordt geupdate aan de hand van de bestelling
						deBestelling = b;
						ArrayList<BesteldProduct> deBesteldeProducten = deBestelling.getBesteldeProducten();
						for(BesteldProduct bp: deBesteldeProducten){
							for(Product p: deVoorraad){
								if(bp.getProduct() == p){
									p.voegAantalToe(bp.getHoeveelheid());
								}
							}
						}
						deBestelling.setIsGeleverd(true);
						JOptionPane.showMessageDialog(null, "Voorraad is bijgewerkt!", "Gelukt!", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
		else if (event.getSource() == b2) {
			bf.setVisible(true);
			this.dispose();
		}
	}
}