package werkplaats;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domeinklassen.Bedrijf;
import domeinklassen.Klus;

// Dit is het startframe waaruit alles geopend wordt.
// Voer een kenteken in en hij zoekt automatisch de openstaande klus op.

public class HoofdFrame extends JFrame implements ActionListener{
	private JButton b1,b2;
	private JTextField tf1;
	private Bedrijf bedrijf;
	private Klus deKlus;
	public HoofdFrame(Bedrijf b){
		bedrijf = b;
		setLayout(new FlowLayout());
		tf1 = new JTextField(7); add(tf1);
		b1 = new JButton("Voer kenteken in"); add(b1);
		b1.addActionListener(this);
		pack(); setTitle("Hoofdframe");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == b1) {
			deKlus = null;
			for(Klus k: bedrijf.getKlussen()){
				if(k.getAuto().getKenteken().equalsIgnoreCase(tf1.getText()) && k.getStatus() !="Voltooid"){
					deKlus = k;
				}
			}
			if(deKlus == null){
				JOptionPane.showMessageDialog(null, "Geen klus gevonden", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				tf1.setText("");
				WerkplaatsFrame wf = new WerkplaatsFrame(this,bedrijf,deKlus);
				this.setVisible(false);
				wf.setVisible(true);
			}
		}
	}
}
