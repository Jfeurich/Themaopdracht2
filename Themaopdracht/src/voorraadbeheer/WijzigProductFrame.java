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

public class WijzigProductFrame extends JFrame implements ActionListener{
	private ArrayList<Product> deVoorraad;
	private ProductFrame pf;
	private JLabel l1, l2, l3, l4;
	private JTextField invoerVeld, aantalVeld;
	private JButton b1, b2, b3, b4, b5;
	private Product hetProduct;
	public WijzigProductFrame(ArrayList<Product> dV, ProductFrame productFrame) {
		deVoorraad = dV;
		pf = productFrame;
		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(5, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		l1 = new JLabel("Voer de naam of artikelnummer in"); hulp.add(l1);
		invoerVeld = new JTextField(10); hulp.add(invoerVeld);
		b1 = new JButton("Zoek op naam"); hulp.add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Zoek op artikelnummer"); hulp.add(b2);
		b2.addActionListener(this);
		l3 = new JLabel(""); hulp.add(l3);
		l4 = new JLabel(""); hulp.add(l4);
		l2 = new JLabel("Aantal"); hulp.add(l2);
		aantalVeld = new JTextField(10); hulp.add(aantalVeld);
		aantalVeld.setEditable(false);
		b3 = new JButton("Verander minimum aanwezig"); hulp.add(b3);
		b3.setEnabled(false);
		b3.addActionListener(this);
		b4 = new JButton("Verander voorraad"); hulp.add(b4);
		b4.setEnabled(false);
		b4.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b5 = new JButton("Annuleer"); add(b5, BorderLayout.SOUTH);
		b5.addActionListener(this);
		pack(); setTitle("Verander product");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == b1) {
			if(invoerVeld.getText() == null || invoerVeld.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//er wordt gezocht op naam naar het product
				hetProduct = null;
				for(Product p : deVoorraad){
					if(p.getNaam().equals(invoerVeld.getText())){
						hetProduct = p;
					}
				}
				if(hetProduct == null){
					JOptionPane.showMessageDialog(null, "Geen artikel gevonden met deze naam", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					l3.setText("Naam: " + hetProduct.getNaam());
					l4.setText("Artikelnummer: " + hetProduct.getArtikelNr());
					b1.setEnabled(false);
					b2.setEnabled(false);
					invoerVeld.setEditable(false);
					aantalVeld.setEditable(true);
					b3.setEnabled(true);
					b4.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Product gevonden! \n" + hetProduct, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		else if (event.getSource() == b2) {
			if(!invoerVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
				JOptionPane.showMessageDialog(null, "Geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//er wordt gezocht op artikelnummer naar het product
				hetProduct = null;
				for(Product p: deVoorraad){
					if(p.getArtikelNr() == Integer.parseInt(invoerVeld.getText())){
						hetProduct = p;
					}
				}
				if(hetProduct == null){
					JOptionPane.showMessageDialog(null, "Geen artikel gevonden met dit artikelnummer", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					l3.setText("Naam: " + hetProduct.getNaam());
					l4.setText("Artikelnummer: " + hetProduct.getArtikelNr());
					b1.setEnabled(false);
					b2.setEnabled(false);
					invoerVeld.setEditable(false);
					aantalVeld.setEditable(true);
					b3.setEnabled(true);
					b4.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Product gevonden! \n" + hetProduct, "Gevonden!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		else if (event.getSource() == b3) {
			//check of er al een product gekozen is
			if(hetProduct == null){
				JOptionPane.showMessageDialog(null, "Nog geen artikel gevonden!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				if(!aantalVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					JOptionPane.showMessageDialog(null, "Het aantal bestaat niet uit alleen getallen!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//minimum aanwezig van het product wordt aangepast
					hetProduct.setMinimumAanwezig(Integer.parseInt(aantalVeld.getText()));
					JOptionPane.showMessageDialog(null, "Het aantal is succesvol verandert! \n" + hetProduct, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
					pf.setVisible(true);
					this.dispose();
				}
			}
		}
		else if (event.getSource() == b4) {
			//check of er al een product gekozen is
			if(hetProduct == null){
				JOptionPane.showMessageDialog(null, "Nog geen artikel gevonden!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				if(!aantalVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					JOptionPane.showMessageDialog(null, "Het aantal bestaat niet uit alleen getallen!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//aantal van het product wordt aangepast
					hetProduct.setAantal(Integer.parseInt(aantalVeld.getText()));
					JOptionPane.showMessageDialog(null, "Het aantal is succesvol verandert! \n" + hetProduct, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
					pf.setVisible(true);
					this.dispose();
				}
			}
		}
		else if (event.getSource() == b5) {
			pf.setVisible(true);
			this.dispose();
		}
	}
}