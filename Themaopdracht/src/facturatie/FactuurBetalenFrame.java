package facturatie;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domeinklassen.Bedrijf;
import domeinklassen.Klus;

public class FactuurBetalenFrame extends JFrame implements ActionListener {
	private Bedrijf bedrijf;
	private HoofdFrame hoofdFrame;
	private JComboBox factuurBox, betaalwijzeBox;
	private JButton betaalKnop, annuleerKnop;
	private Date vandaag = new Date();
	private String geselecteerdeFactuur, geselecteerdeBetaalwijze;
	public FactuurBetalenFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;
		//Paneel met daarop de boxen en knoppen om te betalen
		JPanel hulpPaneel = new JPanel();
		hulpPaneel.setLayout(new GridLayout(1, 3));
		hulpPaneel.setBorder(new EmptyBorder(10, 10, 10, 10));
		factuurBox = new JComboBox();
		for(Klus k : bedrijf.getKlussen()){
			if(k.getFactuur() != null){
				if(k.getFactuur().getIsBetaald() == false){
					factuurBox.addItem(k.getAuto().getKenteken());
				}
			}
		}
		hulpPaneel.add(factuurBox);
		factuurBox.addActionListener(this);
		betaalwijzeBox = new JComboBox();
		betaalwijzeBox.addItem("Giro");
		betaalwijzeBox.addItem("Pin");
		betaalwijzeBox.addItem("Contant");
		hulpPaneel.add(betaalwijzeBox);
		betaalwijzeBox.addActionListener(this);
		betaalKnop = new JButton("Betaal factuur");
		hulpPaneel.add(betaalKnop);
		betaalKnop.addActionListener(this);
		add(hulpPaneel, BorderLayout.CENTER);
		//De annuleerknop
		annuleerKnop = new JButton("Annuleer");
		add(annuleerKnop, BorderLayout.SOUTH);
		annuleerKnop.addActionListener(this);
		pack(); setTitle("Factuur betalen");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		onSelectedItemChangedFactuur();
		onSelectedItemChangedBetaalwijze();
	}

	public void onSelectedItemChangedFactuur() {
		Object obj = factuurBox.getSelectedItem();
		geselecteerdeFactuur = (String)obj;
	}

	public void onSelectedItemChangedBetaalwijze() {
		Object obj = betaalwijzeBox.getSelectedItem();
		geselecteerdeBetaalwijze = (String)obj;
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == factuurBox){
			onSelectedItemChangedFactuur();
		}
		else if(event.getSource() == betaalwijzeBox){
			onSelectedItemChangedBetaalwijze();
		}
		else if(event.getSource() == betaalKnop){
			if(geselecteerdeFactuur != null){
				for(Klus k : bedrijf.getKlussen()){
					if(k.getFactuur() != null){
						if(k.getFactuur().getIsBetaald() == false && k.getAuto().getKenteken().equalsIgnoreCase(geselecteerdeFactuur)){
							k.getFactuur().betaal(geselecteerdeBetaalwijze, vandaag);
							JOptionPane.showMessageDialog(null, "Factuur is betaald: \n" + k.getFactuur().toString(bedrijf.getBTW()), "Factuur betaald!", JOptionPane.PLAIN_MESSAGE);
							hoofdFrame.setVisible(true);
							this.dispose();
						}
					}
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Geen factuur gevonden", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
		}
		else if(event.getSource() == annuleerKnop){
			hoofdFrame.setVisible(true);
			this.dispose();
		}
	}
}