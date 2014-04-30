package facturatie;

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
import domeinklassen.Factuur;
import domeinklassen.Klus;

public class FactuurWijzigenFrame extends JFrame implements ActionListener {
	private Bedrijf bedrijf;
	private HoofdFrame hoofdFrame;
	private JTextField kentekenVeld, percentageVeld;
	private JLabel kentekenLabel, percentageLabel;
	private JButton kentekenKnop, percentageKnop, annuleerKnop;
	private Factuur f = null;
	public FactuurWijzigenFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;
		//Panel met daarop de knoppen, buttons en labels
		//Kenteken
		JPanel hulpPaneel = new JPanel();
		hulpPaneel.setLayout(new GridLayout(2, 3));
		hulpPaneel.setBorder(new EmptyBorder(10, 10, 10, 10));
		kentekenLabel = new JLabel("Voer het kenteken in");
		hulpPaneel.add(kentekenLabel);
		kentekenVeld = new JTextField(10);
		hulpPaneel.add(kentekenVeld);
		kentekenKnop = new JButton("Zoek factuur");
		hulpPaneel.add(kentekenKnop);
		kentekenKnop.addActionListener(this);
		//Percentage
		percentageLabel = new JLabel("Voer het kortingspercentage in");
		hulpPaneel.add(percentageLabel);
		percentageVeld = new JTextField(10);
		hulpPaneel.add(percentageVeld);
		percentageVeld.setEditable(false);
		percentageKnop = new JButton("Bevestig");
		hulpPaneel.add(percentageKnop);
		percentageKnop.addActionListener(this);
		percentageKnop.setEnabled(false);
		add(hulpPaneel, BorderLayout.CENTER);
		//Annuleerknop
		annuleerKnop = new JButton("Annuleer");
		add(annuleerKnop, BorderLayout.SOUTH);
		annuleerKnop.addActionListener(this);
		pack(); setTitle("Kortingspercentage invoeren");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == kentekenKnop){
			String invoer = kentekenVeld.getText();
			for(Klus k: bedrijf.getKlussen()){
				//Is het kenteken hetzelfde als het ingevoerde kenteken
				if(k.getAuto().getKenteken().equalsIgnoreCase(invoer)){
					//check of er een factuur is van deze klus
					if(k.getFactuur() != null){
						//check of de factuur nog niet betaald is van deze klus
						if(!k.getFactuur().getIsBetaald()){
							//als er nog geen factuur is, wordt deze factuur de geselecteerde
							if(f == null){
								f = k.getFactuur();
							}
							else{
								//check zodat de meest recente factuur van deze auto wordt geselecteerd
								if(f.getAanmaakDatum().before(k.getFactuur().getAanmaakDatum())){
									f = k.getFactuur();
								}
							}
						}
					}
				}
			}
			if(f == null){
				JOptionPane.showMessageDialog(null, "Geen factuur gevonden met dit kenteken", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "Factuur gevonden: \n" + f.toString(bedrijf.getBTW()), "Gevonden!", JOptionPane.PLAIN_MESSAGE);
				kentekenVeld.setEditable(false);
				kentekenKnop.setEnabled(false);
				percentageVeld.setEditable(true);
				percentageKnop.setEnabled(true);
			}
		}
		else if(event.getSource() == percentageKnop){
			//check of het ingevoerde percentage wel een getal is
			if(!percentageVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
				JOptionPane.showMessageDialog(null, "Percentage is geen getal!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				//check of het ingevoerde percentage tussen de 0 en 100 ligt
				int percentage = Integer.parseInt(percentageVeld.getText());
				if(percentage < 0 || percentage> 100){
					JOptionPane.showMessageDialog(null, "Percentage heeft geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					f.setKortingsPercentage(percentage);
					JOptionPane.showMessageDialog(null, "Kortingspercentage veranderd: \n" + f.toString(bedrijf.getBTW()), "Gelukt!", JOptionPane.PLAIN_MESSAGE);
					hoofdFrame.setVisible(true);
					this.dispose();
				}
			}
		}
		else if(event.getSource() == annuleerKnop){
			hoofdFrame.setVisible(true);
			this.dispose();
		}
	}
}