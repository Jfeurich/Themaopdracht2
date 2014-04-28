package administratie;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import domeinklassen.Bedrijf;
import domeinklassen.Klant;

public class NieuweKlantFrame extends JFrame implements ActionListener {
	private HoofdFrame hoofdFrame;
	private Bedrijf bedrijf;

	private JLabel lab1, lab2, lab3, lab4, lab5, lab6;
	private JTextField tf1, tf2, tf3, tf4, tf5;
	private JButton b1, b2;

	public NieuweKlantFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;

		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(6, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		lab1 = new JLabel("Naam:"); hulp.add(lab1);
		tf1 = new JTextField(10); hulp.add(tf1);
		lab2 = new JLabel("Adres:"); hulp.add(lab2);
		tf2 = new JTextField(10); hulp.add(tf2);
		lab6 = new JLabel("Plaats:"); hulp.add(lab6);
		tf5 = new JTextField(10); hulp.add(tf5);
		lab3 = new JLabel("Telefoonnummer:"); hulp.add(lab3);
		tf3 = new JTextField(10); hulp.add(tf3);
		lab4 = new JLabel("Rekeningnummer:"); hulp.add(lab4);
		tf4 = new JTextField(10); hulp.add(tf4);
		lab5 = new JLabel(""); hulp.add(lab5);
		b1 = new JButton("Voeg toe"); hulp.add(b1);
		b1.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b2 = new JButton("Annuleren"); add(b2, BorderLayout.SOUTH);
		b2.addActionListener(this);

		pack(); setTitle("Nieuwe Klant");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == b1) {
			if(tf1.getText() != null && tf2.getText() != null && tf3.getText() != null && tf4.getText() != null && tf5.getText() != null && !tf1.getText().equals("") && !tf2.getText().equals("") && !tf3.getText().equals("") && !tf4.getText().equals("") && !tf5.getText().equals("")) {
				if(!tf3.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					JOptionPane.showMessageDialog(null, "TelefoonNummer is geen getal!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else {
				//check of deze klant al bestaat
					boolean b = false;
					for(Klant k2 : bedrijf.getAlleKlanten() ){
						if(k2.getNaam().equals(tf1.getText())) {
							b = true;
						}
					}
					if(b == true){
						JOptionPane.showMessageDialog(null, "Deze klant bestaat al", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					else{
						Klant k = new Klant(1, tf1.getText(), tf2.getText(), tf5.getText(), tf4.getText(), Integer.parseInt(tf3.getText()));
						bedrijf.voegKlantToe(k);
						JOptionPane.showMessageDialog(null, "Klant is succesvol toegevoegd! \n" + k, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
						tf1.setText("");
						tf2.setText("");
						tf3.setText("");
						tf4.setText("");
						tf5.setText("");
					}
				}
			}
			else{
				String s = "De volgende velden zijn niet ingevuld:";
				if(tf1.getText() == null || tf1.getText().equals("")){
					s += "\nNaam";
				}
				if(tf2.getText() == null || tf2.getText().equals("")){
					s += "\nAdres";
				}
				if(tf5.getText() == null || tf5.getText().equals("")){
					s += "\nPlaats";
				}
				if(tf3.getText() == null || tf3.getText().equals("")){
					s += "\nTelefoonNummer";
				}
				if(tf4.getText() == null || tf4.getText().equals("")){
					s += "\nRekeningNummer";
				}
				JOptionPane.showMessageDialog(null, s, "Error!", JOptionPane.PLAIN_MESSAGE);
			}
		}
		else if (event.getSource() == b2) {
			hoofdFrame.setVisible(true);
			this.dispose();
		}
	}
}