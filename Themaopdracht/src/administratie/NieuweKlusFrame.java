package administratie;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import domeinklassen.Auto;
import domeinklassen.Bedrijf;
import domeinklassen.Klant;
import domeinklassen.Klus;
import domeinklassen.Reparatie;

public class NieuweKlusFrame extends JFrame implements ActionListener {
	private HoofdFrame hoofdFrame;
	private Bedrijf bedrijf;

	private JLabel lab1, lab2, lab3, lab4, lab5, lab6, lab7, lab8, lab9;
	private JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7;
	private JButton b1, b2, b3;
	private JComboBox box1;
	private String geselecteerde;
	private Date datum;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	public NieuweKlusFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;

		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(9, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		lab1 = new JLabel("KlantNaam:"); hulp.add(lab1);
		tf1 = new JTextField(10); hulp.add(tf1);
		b1 = new JButton("Zoek"); hulp.add(b1);
		b1.addActionListener(this);
		box1 = new JComboBox(); hulp.add(box1);
		box1.addActionListener(this);
		lab3 = new JLabel("Datum:"); hulp.add(lab3);
		lab9 = new JLabel(""); hulp.add(lab9);
		lab6 = new JLabel("Dag:"); hulp.add(lab6);
		tf5 = new JTextField(10); hulp.add(tf5); tf5.setEnabled(false);
		lab7 = new JLabel("Maand:"); hulp.add(lab7);
		tf6 = new JTextField(10); hulp.add(tf6); tf6.setEnabled(false);
		lab8 = new JLabel("Jaar:"); hulp.add(lab8);
		tf7 = new JTextField(10); hulp.add(tf7); tf7.setEnabled(false);
		lab4 = new JLabel("Omschrijving:"); hulp.add(lab4);
		tf4 = new JTextField(10); hulp.add(tf4);
		tf4.setEnabled(false);
		lab5 = new JLabel(""); hulp.add(lab5);
		b2 = new JButton("Voeg toe"); hulp.add(b2);
		b2.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b3 = new JButton("Annuleren"); add(b3, BorderLayout.SOUTH);
		b3.addActionListener(this);

		pack(); setTitle("Nieuwe Klus");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void onSelectedItemChanged() {
		Object obj = box1.getSelectedItem();
		geselecteerde = (String)obj;
	}

	public Auto getAuto(String kt){
		String kenteken = kt;
		Klant k = bedrijf.getKlant(tf1.getText());
		Auto deAuto = null;
		for (Auto a : k.getAutos()){
			if (kenteken == a.getKenteken()){
				deAuto = a;
			}
		}
		return deAuto;
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == b1) {
			if(tf1.getText() != null && !tf1.getText().equals("")) {
					//Combobox vullen met de auto's van de klant
				if (bedrijf.getKlant(tf1.getText()) != null){							// dit is net aangepast
					Klant k = bedrijf.getKlant(tf1.getText());
					for (Auto a : k.getAutos()){
						box1.addItem(a.getKenteken());
					}
					onSelectedItemChanged();
				}
				else {
					JOptionPane.showMessageDialog(null, "De klant " + tf1.getText() + " bestaat niet", "Error!", JOptionPane.PLAIN_MESSAGE);
				}

			}
			else {
				String s = "Het volgende veld is niet ingevuld:";
				if(tf1.getText() == null || tf1.getText().equals("")){
					s += "\nKlantNaam";
				}
			}
			tf5.setEnabled(true);
			tf6.setEnabled(true);
			tf7.setEnabled(true);
			tf4.setEnabled(true);
		}
		//Nieuwe reparatie toevoegen en opslaan in de array klussen van auto
		if (event.getSource() == b2) {
			if(tf5.getText() != null && !tf5.getText().equals("") && tf6.getText() != null && !tf6.getText().equals("") && tf7.getText() != null && !tf7.getText().equals("") && tf4.getText() != null && !tf4.getText().equals("")) {
				if(!tf5.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(tf5.getText()) <= 0 || Integer.parseInt(tf5.getText()) > 31){
					JOptionPane.showMessageDialog(null, "Dag is geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				if(!tf6.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(tf6.getText()) <= 0 || Integer.parseInt(tf6.getText()) > 12){
					JOptionPane.showMessageDialog(null, "Beginmaand is geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				if(!tf7.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(tf7.getText()) <= 2013 || Integer.parseInt(tf7.getText()) > 5000){
					JOptionPane.showMessageDialog(null, "Beginjaar is geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					try{
						datum = bedrijf.maakDatum(Integer.parseInt(tf5.getText()),Integer.parseInt(tf6.getText()),Integer.parseInt(tf7.getText()));
						Klus r = new Reparatie(datum , tf4.getText(), getAuto((String)box1.getSelectedItem()));  //geselecteerde auto uit de box
						getAuto((String)box1.getSelectedItem()).voegKlusToe(r);
						JOptionPane.showMessageDialog(null, "Klus is succesvol toegevoegd! \n" + r, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
						tf3.setText("");
						tf4.setText("");
					}

					catch(Exception e){
					//	JOptionPane.showMessageDialog(null, "Error: " + e, "Error!", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}

			else {
				String s = "De volgende velden zijn niet ingevuld:";
				if(tf5.getText() == null || tf5.getText().equals("")){
					s += "\nDag";
				}
				if(tf6.getText() == null || tf6.getText().equals("")){
					s += "\nMaand";
				}
				if(tf7.getText() == null || tf7.getText().equals("")){
					s += "\nJaar";
				}
				if(tf4.getText() == null || tf4.getText().equals("")){
					s += "\nOmschrijving";
				}
				JOptionPane.showMessageDialog(null, s, "Error!", JOptionPane.PLAIN_MESSAGE);

			}

		}
		else if (event.getSource() == b3) {
			hoofdFrame.setVisible(true);
			this.dispose();
		}

	}
}

