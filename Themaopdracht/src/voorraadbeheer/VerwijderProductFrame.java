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

import domeinklassen.Product;

public class VerwijderProductFrame extends JFrame implements ActionListener{
	private ArrayList<Product> deVoorraad;
	private ProductFrame pf;
	private Product hetProduct = null;
	private JLabel l1, l2, l3, leegLabel;
	private JTextField invoerVeld;
	private JButton b1, b2, b3, b4;
	public VerwijderProductFrame(ArrayList<Product> dV, ProductFrame productFrame) {
		deVoorraad = dV;
		pf = productFrame;
		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(4, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		l1 = new JLabel("Voer de naam of artikelnummer in"); hulp.add(l1);
		invoerVeld = new JTextField(10); hulp.add(invoerVeld);
		b1 = new JButton("Zoek op naam"); hulp.add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Zoek op artikelnummer"); hulp.add(b2);
		b2.addActionListener(this);
		l2 = new JLabel(""); hulp.add(l2);
		l3 = new JLabel(""); hulp.add(l3);
		leegLabel = new JLabel(""); hulp.add(leegLabel);
		b3 = new JButton("Verwijder product"); hulp.add(b3);
		b3.setEnabled(false);
		b3.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b4 = new JButton("Sluit"); add(b4, BorderLayout.SOUTH);
		b4.addActionListener(this);
		pack(); setTitle("Verwijder product");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == b1) {
			if(invoerVeld.getText() == null || invoerVeld.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//er wordt gezocht op naam of het product bestaat
				for(Product p: deVoorraad){
					if(p.getNaam().equalsIgnoreCase(invoerVeld.getText())){
						hetProduct = p;
					}
				}
				if(hetProduct == null){
					JOptionPane.showMessageDialog(null, "Geen artikel gevonden met deze naam", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//de toString van het product wordt getoond
					l2.setText("Artikelnummer: " + hetProduct.getArtikelNr());
					l3.setText("Naam: " + hetProduct.getNaam());
					b1.setEnabled(false);
					b2.setEnabled(false);
					b3.setEnabled(true);
					invoerVeld.setEditable(false);
					JOptionPane.showMessageDialog(null, "Product gevonden! \n" + hetProduct, "Error!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		else if (event.getSource() == b2) {
			if(!invoerVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
				JOptionPane.showMessageDialog(null, "Geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//er wordt gezocht op artikelnummer of het product bestaat
				for(Product p: deVoorraad){
					if(p.getArtikelNr() == Integer.parseInt(invoerVeld.getText())){
						hetProduct = p;
					}
				}
				if(hetProduct == null){
					JOptionPane.showMessageDialog(null, "Geen artikel gevonden met dit artikelnummer", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//de toString van het product wordt getoond
					l2.setText("Artikelnummer: " + hetProduct.getArtikelNr());
					l3.setText("Naam: " + hetProduct.getNaam());
					b1.setEnabled(false);
					b2.setEnabled(false);
					invoerVeld.setEditable(false);
					b3.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Product gevonden! \n" + hetProduct, "Product gevonden!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		else if (event.getSource() == b3) {
			//check of er al een product is geselecteerd
			if(hetProduct == null){
				JOptionPane.showMessageDialog(null, "Er is nog geen product gevonden!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//het product wordt uit de arraylist gehaald, waarna die door de garbage collecter opgehaald wordt
				deVoorraad.remove(hetProduct);
				l2.setText("");
				l3.setText("");
				JOptionPane.showMessageDialog(null, "Product verwijderd! \n" + hetProduct, "Product verwijderd!", JOptionPane.PLAIN_MESSAGE);
				pf.setVisible(true);
				this.dispose();
			}
		}
		else if (event.getSource() == b4) {
			pf.setVisible(true);
			this.dispose();
		}
	}
}