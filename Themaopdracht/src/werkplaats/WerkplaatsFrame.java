package werkplaats;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import domeinklassen.Bedrijf;
import domeinklassen.Klus;

public class WerkplaatsFrame extends JFrame implements ActionListener{
	private HoofdFrame hf;
	private JButton b1, b2, b3;
	private Bedrijf bedrijf;
	private HoofdFrame hoofdframe;
	private Klus deKlus;
	public WerkplaatsFrame(HoofdFrame hf,Bedrijf b,Klus k){
		bedrijf = b;
		hoofdframe = hf;
		deKlus = k;
		setLayout(new FlowLayout());
		b1 = new JButton("Werkzaamheden wijzigen"); add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Status wijzigen"); add(b2);
		b2.addActionListener(this);
		b3 = new JButton("Sluit"); add(b3);
		b3.addActionListener(this);
		pack(); setTitle("Werkplaats");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == b1) {
			WerkzaamhedenWerkplaatsFrame wwf = new WerkzaamhedenWerkplaatsFrame(this,bedrijf,deKlus);
			wwf.setVisible(true);
			this.setVisible(false);
		}
		else if (e.getSource() == b2) {
			StatusWerkplaatsFrame swf = new StatusWerkplaatsFrame(this,bedrijf,deKlus);
			swf.setVisible(true);
			this.setVisible(false);
		}
		else if (e.getSource() == b3) {
			hoofdframe.setVisible(true);
			this.dispose();
		}
	}
}