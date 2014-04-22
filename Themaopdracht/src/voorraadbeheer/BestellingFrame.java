package voorraadbeheer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import domeinklassen.Bestelling;
import domeinklassen.Product;

public class BestellingFrame extends JFrame implements ActionListener {
	private ArrayList<Product> deVoorraad;
	private ArrayList<Bestelling> deBestellingen;
	private HoofdFrame hf;
	private JButton b1, b2, b3, b4, b5;
	private JLabel l1, l2, l3;
	private JTextField tf1, tf2, tf3;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private Date huidigeTijd = new Date();
	public BestellingFrame(ArrayList<Product> dV, ArrayList<Bestelling> dB, HoofdFrame hoofdFrame) {
		hf = hoofdFrame;
		deVoorraad = dV;
		deBestellingen = dB;
		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(4, 1));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		b1 = new JButton("Nieuwe bestelling"); hulp.add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Wijzig bestelling"); hulp.add(b2);
		b2.addActionListener(this);
		b3 = new JButton("Bestelling geleverd"); hulp.add(b3);
		b3.addActionListener(this);
		b4 = new JButton("Eerstvolgende levering"); hulp.add(b4);
		b4.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b5 = new JButton("Sluit"); add(b5, BorderLayout.SOUTH);
		b5.addActionListener(this);
		pack(); setTitle("Bestelling");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent event) {
		//menu  met alle knoppen die horen bij bestelling
		if (event.getSource() == b1) {
			NieuweBestellingFrame nbf = new NieuweBestellingFrame(deVoorraad, deBestellingen, this);
			nbf.setVisible(true);
			this.setVisible(false);
		}
		else if (event.getSource() == b2) {
			WijzigBestellingFrame wbf = new WijzigBestellingFrame(deVoorraad, deBestellingen, this);
			wbf.setVisible(true);
			this.setVisible(false);
		}
		else if (event.getSource() == b3) {
			BevestigBestellingFrame bevbf = new BevestigBestellingFrame(deVoorraad, deBestellingen, this);
			bevbf.setVisible(true);
			this.setVisible(false);
		}
		else if (event.getSource() == b4) {
			//laat de eerstvolgende bestelling zien, gesorteerd op datum
			Bestelling deBestelling = null;
			for(Bestelling b: deBestellingen){
				if(b.getIsGeleverd() == false && b.getVerwachteDatum() != null){
					if(deBestelling == null){
						deBestelling = b;
					}
					if(deBestelling.getVerwachteDatum().after(b.getVerwachteDatum()) && b.getVerwachteDatum().after(huidigeTijd)){
						deBestelling = b;
					}
				}
			}
			if(deBestelling == null){
				JOptionPane.showMessageDialog(null, "Geen aankomende bestellingen", "Gelukt!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "Eerstvolgende levering: \n" + deBestelling, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
			}
		}
		else if (event.getSource() == b5) {
			hf.setVisible(true);
			this.dispose();
		}
	}
}