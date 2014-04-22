package facturatie;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domeinklassen.Bedrijf;
import domeinklassen.Factuur;
import domeinklassen.Klus;

public class NieuweFactuurFrame extends JFrame implements ActionListener {
	private Bedrijf bedrijf;
	private HoofdFrame hoofdFrame;
	private JLabel kentekenLabel, leegLabel;
	private JButton maakKnop, annuleerKnop;
	private JComboBox box;
	private Date vandaag = new Date();
	private String geselecteerde;

	public NieuweFactuurFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;
		//Panel met daarop de combobox en knoppen
		JPanel hulpPaneel = new JPanel();
		hulpPaneel.setLayout(new GridLayout(2, 2));
		hulpPaneel.setBorder(new EmptyBorder(10, 10, 10, 10));
		kentekenLabel = new JLabel("Alle klussen die klaar zijn zonder factuur:");
		hulpPaneel.add(kentekenLabel);
		box = new JComboBox();
		//alle ketekens van de klussen zonder factuur worden getoond in de combobox
		for(Klus k : bedrijf.getKlussenZF()){
			box.addItem(k.getAuto().getKenteken());
		}
		hulpPaneel.add(box);
		box.addActionListener(this);
		leegLabel = new JLabel("");
		hulpPaneel.add(leegLabel);
		maakKnop = new JButton("Maak de factuur");
		hulpPaneel.add(maakKnop);
		maakKnop.addActionListener(this);
		add(hulpPaneel, BorderLayout.CENTER);
		//Annuleer knop
		annuleerKnop = new JButton("Annuleer");
		add(annuleerKnop, BorderLayout.SOUTH);
		annuleerKnop.addActionListener(this);
		pack(); setTitle("Nieuwe factuur");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		onSelectedItemChanged();
	}

	public void onSelectedItemChanged() {
		Object obj = box.getSelectedItem();
		geselecteerde = (String)obj;
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == maakKnop){
			//zoekt de klus bij het kenteken en maakt een factuur aan bij deze klus
			for(Klus k : bedrijf.getKlussenZF()){
				if(k.getAuto().getKenteken().equalsIgnoreCase(geselecteerde)){
					Factuur f = new Factuur(vandaag, k);
					//de factuur wordt getoond, daarna wordt het frame gesloten
					JOptionPane.showMessageDialog(null, "Factuur aangemaakt: \n" + f.toString(bedrijf.getBTW()), "Voorbeeld!", JOptionPane.PLAIN_MESSAGE);
					hoofdFrame.setVisible(true);
					this.dispose();
				}
			}
		}
		else if(event.getSource() == annuleerKnop){
			hoofdFrame.setVisible(true);
			this.dispose();
		}
		else if (event.getSource() == box) {
			onSelectedItemChanged();
		}
	}
}