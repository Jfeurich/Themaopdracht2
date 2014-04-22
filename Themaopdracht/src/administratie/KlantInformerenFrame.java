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
import domeinklassen.Klus;

public class KlantInformerenFrame extends JFrame implements ActionListener {
	private HoofdFrame hoofdFrame;
	private Bedrijf bedrijf;
	private Klant k;

	private JLabel lab1;
	private JTextField tf1;
	private JButton b1, b2;

	public KlantInformerenFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;

		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(5, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		lab1 = new JLabel("Kenteken:"); hulp.add(lab1);
		tf1 = new JTextField(10); hulp.add(tf1);
		b1 = new JButton("Zoeken"); hulp.add(b1);
		b1.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b2 = new JButton("Annuleren"); add(b2, BorderLayout.SOUTH);
		b2.addActionListener(this);

		pack(); setTitle("KlantInformeren");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		Klus deKlus = null;
		if (event.getSource() == b1) {
			if(tf1.getText() != null && !tf1.getText().equals("")) {
				boolean b = false;
				for(Klant k : bedrijf.getAlleKlanten()){
					for(Auto a : k.getAutos()){
						if(a.getKenteken().equals(tf1.getText())){
							b = true;
						}
					}
				}
				if(b == false){
					System.out.println("dfd");
					JOptionPane.showMessageDialog(null, "Kenteken " + tf1.getText() + " bestaat niet", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					for(Klus k : bedrijf.getKlussen()){
						if(k.equals(bedrijf.heeftKlus(tf1.getText()))){
							deKlus = k;
						}
					}
					JOptionPane.showMessageDialog(null, "De status is: " + deKlus.getStatus(), "Status!", JOptionPane.PLAIN_MESSAGE);
				}
			}

			else{
				String s = "Het volgende veld is niet ingevuld: ";
				if(tf1.getText() == null || tf1.getText().equals("")){
					s += "\nKenteken";
				}
				JOptionPane.showMessageDialog(null, s, "Error!", JOptionPane.PLAIN_MESSAGE);
			}
		}

		//annuleren
		else if (event.getSource() == b2) {
			hoofdFrame.setVisible(true);
			this.dispose();
		}

	}
}