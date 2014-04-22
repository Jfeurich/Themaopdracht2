package administratie;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import domeinklassen.Bedrijf;
import domeinklassen.Klant;
import domeinklassen.Klus;

public class HoofdFrame extends JFrame implements ActionListener {
	private JButton b1, b2, b3, b4;
	private Bedrijf bedrijf;
	private ArrayList<Klant> deKlanten;
	private ArrayList<Klus> deKlussen;
	public HoofdFrame(Bedrijf b) {
		bedrijf = b;
		setLayout(new FlowLayout());
		b1 = new JButton("Nieuwe Klant"); add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Nieuwe Klus"); add(b2);
		b2.addActionListener(this);
		b3 = new JButton("Nieuwe Auto"); add(b3);
		b3.addActionListener(this);
		b4 = new JButton("Klant informeren"); add(b4);
		b4.addActionListener(this);
		pack(); setTitle("Menu");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		//menu met de knoppen product, producten onder minimum voorraad en bestelling
		if (event.getSource() == b1) {
			NieuweKlantFrame nklaf = new NieuweKlantFrame(bedrijf, this);
			this.setVisible(false);
			nklaf.setVisible(true);
		}
		else if (event.getSource() == b2) {
			NieuweKlusFrame nkluf = new NieuweKlusFrame(bedrijf, this);
			this.setVisible(false);
			nkluf.setVisible(true);
		}
		else if (event.getSource() == b3) {
			NieuweAutoFrame naf = new NieuweAutoFrame(bedrijf, this);
			this.setVisible(false);
			naf.setVisible(true);
		}
		else if (event.getSource() == b4) {
			KlantInformerenFrame kif = new KlantInformerenFrame(bedrijf, this);
			this.setVisible(false);
			kif.setVisible(true);
		}
	}
}