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

public class WijzigBestellingFrame extends JFrame implements ActionListener {
	private ArrayList<Product> deVoorraad;
	private ArrayList<Bestelling> deBestellingen;
	private ArrayList<BesteldProduct> deBesteldeProducten;
	private Bestelling deBestelling;
	private BestellingFrame bf;
	private JButton b1, b2, b3, b4, b5, b6;
	private JLabel nummerLabel, huidigeLabel, totaalLabel, aNLabel, aantalLabel, dagLabel, maandLabel, jaarLabel, leegLabel1;
	private JTextField nummerVeld, aNVeld, aantalVeld, dagVeld, maandVeld, jaarVeld;
	public WijzigBestellingFrame(ArrayList<Product> dV, ArrayList<Bestelling> dB, BestellingFrame bestellingFrame) {
		deVoorraad = dV;
		deBestellingen = dB;
		bf = bestellingFrame;
		deBestelling = null;
		deBesteldeProducten = new ArrayList<BesteldProduct>();
		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(10, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		nummerLabel = new JLabel("Bestelnummer:"); hulp.add(nummerLabel);
		nummerVeld = new JTextField(10); hulp.add(nummerVeld);
		b1 = new JButton("Alle bestellingen"); hulp.add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Vind bestelling"); hulp.add(b2);
		b2.addActionListener(this);
		huidigeLabel = new JLabel(""); hulp.add(huidigeLabel);
		totaalLabel = new JLabel(""); hulp.add(totaalLabel);
		aNLabel = new JLabel("Artikelnummer:"); hulp.add(aNLabel);
		aNVeld = new JTextField(10); hulp.add(aNVeld);
		aNVeld.setEditable(false);
		aantalLabel = new JLabel("Eenheid:"); hulp.add(aantalLabel);
		aantalVeld = new JTextField(10); hulp.add(aantalVeld);
		aantalVeld.setEditable(false);
		b3 = new JButton("Voeg product toe"); hulp.add(b3);
		b3.setEnabled(false);
		b3.addActionListener(this);
		b4 = new JButton("Bevestig bestelling"); hulp.add(b4);
		b4.setEnabled(false);
		b4.addActionListener(this);
		dagLabel = new JLabel("Dag:"); hulp.add(dagLabel);
		dagVeld = new JTextField(10); hulp.add(dagVeld);
		dagVeld.setEditable(false);
		maandLabel = new JLabel("Maand:"); hulp.add(maandLabel);
		maandVeld = new JTextField(10); hulp.add(maandVeld);
		maandVeld.setEditable(false);
		jaarLabel = new JLabel("Jaar:"); hulp.add(jaarLabel);
		jaarVeld = new JTextField(10); hulp.add(jaarVeld);
		jaarVeld.setEditable(false);
		leegLabel1 = new JLabel(""); hulp.add(leegLabel1);
		b5 = new JButton("Voeg verwachte datum toe"); hulp.add(b5);
		b5.setEnabled(false);
		b5.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b6 = new JButton("Annuleer"); add(b6, BorderLayout.SOUTH);
		b6.addActionListener(this);
		pack(); setTitle("Beheer bestelling");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == b1) {
			//Alle bestellingen worden getoond
			String s = "Alle bestellingen: ";
			for(Bestelling b: deBestellingen){
				s+= "\n" + b;
			}
			JOptionPane.showMessageDialog(null, s, "Alle bestellingen!", JOptionPane.PLAIN_MESSAGE);
		}
		else if (event.getSource() == b2) {
			if(!nummerVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
				JOptionPane.showMessageDialog(null, "Geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//de bestelling wordt gezocht op bestelnummer
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
					deBestelling = b;
					deBesteldeProducten = deBestelling.getBesteldeProducten();
					huidigeLabel.setText("Bestelnr:" + deBestelling.getBestelNummer());
					totaalLabel.setText("Aantal producten:" + deBestelling.getTotaal());
					nummerVeld.setEditable(false);
					b1.setEnabled(false);
					b2.setEnabled(false);
					b3.setEnabled(true);
					b4.setEnabled(true);
					b5.setEnabled(true);
					aNVeld.setEditable(true);
					aantalVeld.setEditable(true);
					dagVeld.setEditable(true);
					maandVeld.setEditable(true);
					jaarVeld.setEditable(true);
					JOptionPane.showMessageDialog(null, "Bestelling gevonden! \n" + deBestelling, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		else if (event.getSource() == b3) {
			//check of er al een bestelling is geselecteerd
			if(deBestelling == null){
				JOptionPane.showMessageDialog(null, "Nog geen bestelling gevonden!", "Error!", JOptionPane.PLAIN_MESSAGE);
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
						//het product wordt toegevoegd aan de bestelling
						BesteldProduct bp = new BesteldProduct(pr, Integer.parseInt(aantalVeld.getText()));
						deBesteldeProducten.add(bp);
						huidigeLabel.setText("Bestelnr:" + deBestelling.getBestelNummer());
						int totaalProducten = 0;
						for(BesteldProduct bep: deBesteldeProducten){
							totaalProducten += bep.getHoeveelheid();
						}
						totaalLabel.setText("Aantal producten:" + totaalProducten);
						aNVeld.setText("");
						aantalVeld.setText("");
						JOptionPane.showMessageDialog(null, "Product is succesvol toegevoegd", "Gelukt!", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
		else if (event.getSource() == b4) {
			//check of er al een bestelling is geselecteerd
			if(deBestelling == null){
				JOptionPane.showMessageDialog(null, "Nog geen bestelling gevonden!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//de bestelling wordt geupdate
				nummerVeld.setText("");
				aNVeld.setText("");
				aantalVeld.setText("");
				deBestelling.setBesteldeProducten(deBesteldeProducten);
				JOptionPane.showMessageDialog(null, "Bestelling succesvol verandert!", "Gelukt!", JOptionPane.PLAIN_MESSAGE);
				bf.setVisible(true);
				this.dispose();
			}
		}
		else if (event.getSource() == b5){
			//check of er al een bestelling is geselecteerd
			if(deBestelling == null){
				JOptionPane.showMessageDialog(null, "Nog geen bestelling gevonden!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				if(!dagVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || !maandVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || !jaarVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					JOptionPane.showMessageDialog(null, "Geen geldige datum! Gebruik dd-mm-yyyy!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//de verwachte datum wordt toegevoegd aan de bestelling
					if(Integer.parseInt(dagVeld.getText()) < 1 || Integer.parseInt(dagVeld.getText()) > 31 || Integer.parseInt(maandVeld.getText()) < 0 || Integer.parseInt(maandVeld.getText()) > 12 || Integer.parseInt(jaarVeld.getText()) <= 2013 || Integer.parseInt(jaarVeld.getText()) >= 10000){
						JOptionPane.showMessageDialog(null, "Geen geldige datum! Gebruik dd-mm-yyyy!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					else{
						String datum;
						if(Integer.parseInt(dagVeld.getText()) >= 1 && Integer.parseInt(dagVeld.getText()) <= 9){
							datum = "0" + dagVeld.getText();
						}
						else{
							datum = dagVeld.getText();
						}
						datum += "-";
						if(Integer.parseInt(maandVeld.getText()) >= 1 && Integer.parseInt(maandVeld.getText()) <= 9){
							datum += "0" + maandVeld.getText();
						}
						else{
							datum += dagVeld.getText();
						}
						datum += "-" + jaarVeld.getText();
						try{
							deBestelling.setVerwachteDatum(datum);
							JOptionPane.showMessageDialog(null, "Verwachte datum is succesvol bijgewerkt", "Gelukt!", JOptionPane.PLAIN_MESSAGE);
							bf.setVisible(true);
							this.dispose();
						}
						catch(Exception e){
							JOptionPane.showMessageDialog(null, "Error: " + e, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
						}

					}
				}
			}
		}
		else if (event.getSource() == b6) {
			bf.setVisible(true);
			this.dispose();
		}
	}
}