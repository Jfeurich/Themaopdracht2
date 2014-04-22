package facturatie;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import domeinklassen.Bedrijf;

public class HoofdFrame extends JFrame implements ActionListener {
	private JButton b1, b2, b3, b4, b5;
	private Bedrijf bedrijf;
	public HoofdFrame(Bedrijf b) {
		bedrijf = b;
		setLayout(new FlowLayout());
		b1 = new JButton("Nieuwe factuur"); add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Factuur betalen"); add(b2);
		b2.addActionListener(this);
		b3 = new JButton("Overzicht facturen"); add(b3);
		b3.addActionListener(this);
		b4 = new JButton("Kortingspercentage invoeren"); add(b4);
		b4.addActionListener(this);
		b5 = new JButton("Overzicht BTW per periode"); add(b5);
		b5.addActionListener(this);
		pack(); setTitle("Menu");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == b1) {
			NieuweFactuurFrame nff = new NieuweFactuurFrame(bedrijf, this);
			this.setVisible(false);
			nff.setVisible(true);
		}
		else if (event.getSource() == b2) {
			FactuurBetalenFrame fbf = new FactuurBetalenFrame(bedrijf, this);
			this.setVisible(false);
			fbf.setVisible(true);
		}
		else if (event.getSource() == b3) {
			OverzichtFactuurFrame off = new OverzichtFactuurFrame(bedrijf, this);
			this.setVisible(false);
			off.setVisible(true);
		}
		else if (event.getSource() == b4) {
			FactuurWijzigenFrame fwf = new FactuurWijzigenFrame(bedrijf, this);
			this.setVisible(false);
			fwf.setVisible(true);
		}
		else if (event.getSource() == b5) {
			BTWOverzichtFrame bof = new BTWOverzichtFrame(bedrijf, this);
			this.setVisible(false);
			bof.setVisible(true);
		}
	}
}