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

public class NieuweBestellingFrame extends JFrame implements ActionListener {
	private ArrayList<Product> deVoorraad;
	private ArrayList<Bestelling> deBestellingen;
	private ArrayList<BesteldProduct> deBesteldeProducten;
	private Bestelling deBestelling;
	private BestellingFrame bf;
	private JButton b1, b2, b3, b4;
	private JLabel nummerLabel, huidigeLabel, totaalLabel, aNLabel, aantalLabel, leegLabel1;
	private JTextField nummerVeld, aNVeld, aantalVeld;
	public NieuweBestellingFrame(ArrayList<Product> dV, ArrayList<Bestelling> dB, BestellingFrame bestellingFrame) {
		deVoorraad = dV;
		deBestellingen = dB;
		bf = bestellingFrame;
		deBestelling = null;
		deBesteldeProducten = new ArrayList<BesteldProduct>();
		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(6, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		nummerLabel = new JLabel("Bestelnummer:"); hulp.add(nummerLabel);
		nummerVeld = new JTextField(10); hulp.add(nummerVeld);
		leegLabel1 = new JLabel(""); hulp.add(leegLabel1);
		b1 = new JButton("Maak bestelling"); hulp.add(b1);
		b1.addActionListener(this);
		huidigeLabel = new JLabel(""); hulp.add(huidigeLabel);
		totaalLabel = new JLabel(""); hulp.add(totaalLabel);
		aNLabel = new JLabel("Artikelnummer:"); hulp.add(aNLabel);
		aNVeld = new JTextField(10); hulp.add(aNVeld);
		aNVeld.setEditable(false);
		aantalLabel = new JLabel("Aantal:"); hulp.add(aantalLabel);
		aantalVeld = new JTextField(10); hulp.add(aantalVeld);
		aantalVeld.setEditable(false);
		b2 = new JButton("Voeg product toe"); hulp.add(b2);
		b2.setEnabled(false);
		b2.addActionListener(this);
		b3 = new JButton("Bevestig bestelling"); hulp.add(b3);
		b3.setEnabled(false);
		b3.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b4 = new JButton("Annuleer"); add(b4, BorderLayout.SOUTH);
		b4.addActionListener(this);
		pack(); setTitle("Nieuwe bestelling");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent event) {
		//Knop om een bestelling te maken
		if (event.getSource() == b1) {
			if(!nummerVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
				JOptionPane.showMessageDialog(null, "Geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//check of de bestelling al bestaat
				boolean b = false;
				for(Bestelling be: deBestellingen){
					if(be.getBestelNummer() == Integer.parseInt(nummerVeld.getText())){
						b = true;
					}
				}
				if(b == true){
					JOptionPane.showMessageDialog(null, "Er bestaat al een bestelling met dit nummer", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//een nieuwe bestelling wordt gemaakt
					deBestelling = new Bestelling(Integer.parseInt(nummerVeld.getText()), deBesteldeProducten);
					nummerVeld.setEditable(false);
					b1.setEnabled(false);
					aNVeld.setEditable(true);
					aantalVeld.setEditable(true);
					b2.setEnabled(true);
					b3.setEnabled(true);
					huidigeLabel.setText("Bestelnr:" + deBestelling.getBestelNummer());
					totaalLabel.setText("Aantal producten:" + deBestelling.getTotaal());
					JOptionPane.showMessageDialog(null, "Nieuwe bestelling gemaakt!", "Gelukt!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		else if (event.getSource() == b2) {
			//check of er al een bestelling is
			if(deBestelling == null){
				JOptionPane.showMessageDialog(null, "Nog geen bestelling gemaakt!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				if(!aNVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					JOptionPane.showMessageDialog(null, "Artikelnummer is geen getal!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else if(!aantalVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					JOptionPane.showMessageDialog(null, "Aantal is geen getal!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//check of het product bestaat
					Product pr = null;
					for(Product p: deVoorraad){
						if(p.getArtikelNr() == Integer.parseInt(aNVeld.getText())){
							pr = p;
						}
					}
					if(pr == null){
						JOptionPane.showMessageDialog(null, "Product is niet gevonden!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					else{
						//Er wordt een besteldproduct gemaakt, deze wordt aan de bestelling toegevoegd
						BesteldProduct bp = new BesteldProduct(pr, Integer.parseInt(aantalVeld.getText()));
						deBesteldeProducten.add(bp);
						aNVeld.setText("");
						aantalVeld.setText("");
						totaalLabel.setText("Aantal producten:" + deBestelling.getTotaal());
						JOptionPane.showMessageDialog(null, "Product is succesvol toegevoegd", "Gelukt!", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
		else if (event.getSource() == b3) {
			//check of er al een bestelling is
			if(deBestelling == null){
				JOptionPane.showMessageDialog(null, "Nog geen bestelling gemaakt!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//check dat er minimaal één product is toegevoegd aan de bestelling
				if(deBestelling.getTotaal() == 0){
					JOptionPane.showMessageDialog(null, "Nog geen producten toegevoegd!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//de bestelling wordt toegevoegd aan de bestellingen, hierdoor wordt de bestelling dus opgeslagen
					deBestellingen.add(deBestelling);
					JOptionPane.showMessageDialog(null, "Bestelling succesvol gemaakt!", "Gelukt!", JOptionPane.PLAIN_MESSAGE);
					bf.setVisible(true);
					this.dispose();
				}
			}
		}
		else if (event.getSource() == b4) {
			bf.setVisible(true);
			this.dispose();
		}
	}
}