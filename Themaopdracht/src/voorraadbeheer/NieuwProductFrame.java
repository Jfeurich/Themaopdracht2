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

public class NieuwProductFrame extends JFrame implements ActionListener {
	private ArrayList<Product> deVoorraad;
	private ProductFrame pf;
	private JButton b1, b2;
	private JLabel naamLabel, aNLabel, mALabel, eenheidLabel, leegLabel;
	private JTextField naamVeld, aNVeld, mAVeld, eenheidVeld;
	public NieuwProductFrame(ArrayList<Product> dV, ProductFrame productFrame) {
		deVoorraad = dV;
		pf = productFrame;
		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(5, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		naamLabel = new JLabel("Naam:"); hulp.add(naamLabel);
		naamVeld = new JTextField(10); hulp.add(naamVeld);
		aNLabel = new JLabel("Artikelnummer:"); hulp.add(aNLabel);
		aNVeld = new JTextField(10); hulp.add(aNVeld);
		mALabel = new JLabel("Minimum aanwezig:"); hulp.add(mALabel);
		mAVeld = new JTextField(10); hulp.add(mAVeld);
		eenheidLabel = new JLabel("Eenheid:"); hulp.add(eenheidLabel);
		eenheidVeld = new JTextField(10); hulp.add(eenheidVeld);
		leegLabel = new JLabel(""); hulp.add(leegLabel);
		b1 = new JButton("Voeg toe"); hulp.add(b1);
		b1.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b2 = new JButton("Sluit"); add(b2, BorderLayout.SOUTH);
		b2.addActionListener(this);
		pack(); setTitle("Nieuw product");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == b1) {
			if(naamVeld.getText() != null && aNVeld.getText() != null &&  mAVeld.getText() != null && eenheidVeld.getText() != null && !naamVeld.getText().equals("") && !aNVeld.getText().equals("") && !mAVeld.getText().equals("") && !eenheidVeld.getText().equals("")){
				if(!mAVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					JOptionPane.showMessageDialog(null, "Minimum aanwezig is geen getal!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else if(!aNVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					JOptionPane.showMessageDialog(null, "Artikel nummer is geen getal!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					//check of er al een product bestaat met hetzelfde artikelnummer
					boolean b = false;
					for(Product p: deVoorraad){
						if(p.getArtikelNr() == Integer.parseInt(aNVeld.getText())){
							b = true;
						}
					}
					if(b == true){
						JOptionPane.showMessageDialog(null, "Er bestaat al een artikel met dit artikelnummer", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					else{
						Product pr = new Product(naamVeld.getText(), Integer.parseInt(aNVeld.getText()), Integer.parseInt(mAVeld.getText()), eenheidVeld.getText());
						deVoorraad.add(pr);
						JOptionPane.showMessageDialog(null, "Product succesvol toegevoegd! \n"+ pr, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
						naamVeld.setText("");
						aNVeld.setText("");
						mAVeld.setText("");
						eenheidVeld.setText("");
					}
				}
			}
			else{
				String s = "De volgende velden zijn niet ingevuld:";
				if(naamVeld.getText() == null || naamVeld.getText().equals("")){
					s += "\nNaam";
				}
				if(aNVeld.getText() == null || aNVeld.getText().equals("")){
					s += "\nArtikelnummer";
				}
				if(mAVeld.getText() == null || mAVeld.getText().equals("")){
					s += "\nMinimum aanwezig";
				}
				if(eenheidVeld.getText() == null || eenheidVeld.getText().equals("")){
					s += "\nEenheid";
				}
				JOptionPane.showMessageDialog(null, s, "Error!", JOptionPane.PLAIN_MESSAGE);
			}

		}
		else if (event.getSource() == b2) {
			pf.setVisible(true);
			this.dispose();
		}
	}
}