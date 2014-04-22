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

import domeinklassen.Auto;
import domeinklassen.Bedrijf;
import domeinklassen.Klant;

public class NieuweAutoFrame extends JFrame implements ActionListener {
	private HoofdFrame hoofdFrame;
	private Bedrijf bedrijf;
	private Klant k = null;

	private JLabel lab1, lab2, lab3, lab4, lab5;
	private JTextField tf1, tf2, tf3, tf4;
	private JButton b1, b2;

	public NieuweAutoFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;

		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(5, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		lab1 = new JLabel("Kenteken:"); hulp.add(lab1);
		tf1 = new JTextField(10); hulp.add(tf1);
		lab2 = new JLabel("Merk:"); hulp.add(lab2);
		tf2 = new JTextField(10); hulp.add(tf2);
		lab3 = new JLabel("Type:"); hulp.add(lab3);
		tf3 = new JTextField(10); hulp.add(tf3);
		lab4 = new JLabel("Klantnaam:"); hulp.add(lab4);
		tf4 = new JTextField(10); hulp.add(tf4);
		lab5 = new JLabel(""); hulp.add(lab5);
		b1 = new JButton("Voeg toe"); hulp.add(b1);
		b1.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b2 = new JButton("Annuleren"); add(b2, BorderLayout.SOUTH);
		b2.addActionListener(this);

		pack(); setTitle("Nieuwe Auto");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == b1) {
			if(tf1.getText() != null && tf2.getText() != null && tf3.getText() != null && tf4.getText() != null && !tf1.getText().equals("") && !tf2.getText().equals("") && !tf3.getText().equals("") && !tf4.getText().equals("")) {
			//Check of de klant bestaat
				Klant deKlant = null;
				for(Klant k : bedrijf.getAlleKlanten() ){
					if(k.getNaam().equals(tf4.getText())) {
						deKlant = k;
					}
				}
				if(deKlant == null){
					JOptionPane.showMessageDialog(null, "De klant " + tf4.getText() + " bestaat niet", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					boolean b = false;
					for(Auto a : deKlant.getAutos()){
						if(a.getKenteken().equals(tf1.getText())){
							b = true;
						}
					}
					if(b == true){
						JOptionPane.showMessageDialog(null, "Auto bestaat al.", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					else{	
						Auto a = new Auto(tf1.getText(), tf2.getText(), tf3.getText(), deKlant);
						JOptionPane.showMessageDialog(null, "Auto is succesvol toegevoegd! \n" + a, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
						tf1.setText("");
						tf2.setText("");
						tf3.setText("");
						tf4.setText("");
					}		
				}
			}
			else{
				String s = "De volgende velden zijn niet ingevuld:";
				if(tf1.getText() == null || tf1.getText().equals("")){
					s += "\nKenteken";
				}
				if(tf2.getText() == null || tf2.getText().equals("")){
					s += "\nMerk";
				}
				if(tf3.getText() == null || tf3.getText().equals("")){
					s += "\nType";
				}
				if(tf4.getText() == null || tf4.getText().equals("")){
					s += "\nKlantNaam";
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