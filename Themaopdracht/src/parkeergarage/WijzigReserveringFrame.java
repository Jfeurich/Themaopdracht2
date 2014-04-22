package parkeergarage;

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
import domeinklassen.Reservering;

public class WijzigReserveringFrame extends JFrame implements ActionListener {
	private Bedrijf bedrijf;
	private HoofdFrame hoofdFrame;
	private JButton bevestigKnop, wijzigKnop,annuleerKnop;
	private JTextField kentekenVeld, bdVeld, bmVeld, bjVeld, edVeld, emVeld, ejVeld;
	private JLabel kentekenLabel, infoLabel, bLabel, eLabel, leegLabel;
	private JComboBox box;
	private Date begDat, eindDat;
	private Auto deAuto;
	private Reservering deReservering;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	public WijzigReserveringFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;
		//Panel waarin alle gegevens ingevuld kunnen worden
		JPanel hulpPaneel = new JPanel();
		hulpPaneel.setLayout(new GridLayout(3, 2));
		hulpPaneel.setBorder(new EmptyBorder(10, 10, 10, 10));
		kentekenLabel = new JLabel("Kenteken: ");
		hulpPaneel.add(kentekenLabel);
		kentekenVeld = new JTextField(10);
		hulpPaneel.add(kentekenVeld);
		box = new JComboBox();
		hulpPaneel.add(box);
		box.addActionListener(this);
		bevestigKnop = new JButton("Zoek auto");
		bevestigKnop.addActionListener(this);
		hulpPaneel.add(bevestigKnop);
		infoLabel = new JLabel("Kies een reservering");
		hulpPaneel.add(infoLabel);
		add(hulpPaneel, BorderLayout.NORTH);
		//Paneel met daarop de textvelden om nieuwe datums in te voeren
		JPanel datumPaneel = new JPanel();
		datumPaneel.setLayout(new GridLayout(2, 5));
		datumPaneel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bLabel = new JLabel("Begindatum");
		datumPaneel.add(bLabel);
		bdVeld = new JTextField(2);
		datumPaneel.add(bdVeld);
		bmVeld = new JTextField(2);
		datumPaneel.add(bmVeld);
		bjVeld = new JTextField(4);
		datumPaneel.add(bjVeld);
		leegLabel = new JLabel("");
		datumPaneel.add(leegLabel);
		eLabel = new JLabel("Einddatum");
		datumPaneel.add(eLabel);
		edVeld = new JTextField(2);
		datumPaneel.add(edVeld);
		emVeld = new JTextField(2);
		datumPaneel.add(emVeld);
		ejVeld = new JTextField(4);
		datumPaneel.add(ejVeld);
		wijzigKnop = new JButton("Wijzig reservering");
		datumPaneel.add(wijzigKnop);
		wijzigKnop.setEnabled(false);
		wijzigKnop.addActionListener(this);
		add(datumPaneel, BorderLayout.CENTER);
		//De annuleerknop
		annuleerKnop = new JButton("Annuleer");
		annuleerKnop.addActionListener(this);
		add(annuleerKnop, BorderLayout.SOUTH);
		pack(); setTitle("Wijzig reservering");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void onSelectedItemChanged() {
		Object obj = box.getSelectedItem();
		String geselecteerde = (String)obj;
		//De reservering wordt bij de begin datum gezocht
		try{
			for(Reservering r : bedrijf.getAlleReserveringen()){
				if(r.getAuto().getKenteken().equalsIgnoreCase(deAuto.getKenteken())){
					if(r.getBegDat().equals(df.parse(geselecteerde))){
						deReservering = r;
					}
				}
			}
			infoLabel.setText(deReservering.toString());
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error: " + e, "Error!", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == bevestigKnop) {
			for(Klant k : bedrijf.getAlleKlanten()){
				for(Auto a : k.getAutos()){
					if(a.getKenteken().equalsIgnoreCase(kentekenVeld.getText())){
						deAuto = a;
					}
				}
			}
			if(deAuto == null){
				JOptionPane.showMessageDialog(null, "Geen auto gevonden met dit kenteken!", "Error!", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				bevestigKnop.setEnabled(false);
				kentekenVeld.setEditable(false);
				wijzigKnop.setEnabled(true);
				for(Reservering r : bedrijf.getAlleReserveringen()){
					if(r.getAuto().getKenteken().equalsIgnoreCase(deAuto.getKenteken())){
						box.addItem(df.format(r.getBegDat()));
					}
				}
				onSelectedItemChanged();
			}
		}
		else if(event.getSource() == wijzigKnop) {
			try{
				if(!bdVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(bdVeld.getText()) <= 0 || Integer.parseInt(bdVeld.getText()) > 31){
					JOptionPane.showMessageDialog(null, "Begindag is geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				if(!bmVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(bmVeld.getText()) <= 0 || Integer.parseInt(bmVeld.getText()) > 12){
					JOptionPane.showMessageDialog(null, "Beginmaand is geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				if(!bjVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(bjVeld.getText()) <= 2013 || Integer.parseInt(bjVeld.getText()) > 5000){
					JOptionPane.showMessageDialog(null, "Beginjaar is geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				if(!edVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(edVeld.getText()) <= 0 || Integer.parseInt(edVeld.getText()) > 31){
					JOptionPane.showMessageDialog(null, "Einddag is geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				if(!emVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(emVeld.getText()) <= 0 || Integer.parseInt(emVeld.getText()) > 12){
					JOptionPane.showMessageDialog(null, "Eindmaand is geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				if(!ejVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(ejVeld.getText()) <= 2013 || Integer.parseInt(ejVeld.getText()) > 5000){
					JOptionPane.showMessageDialog(null, "Eindjaar is geen geldige waarde!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					begDat = bedrijf.maakDatum(Integer.parseInt(bdVeld.getText()),Integer.parseInt(bmVeld.getText()),Integer.parseInt(bjVeld.getText()));
					eindDat = bedrijf.maakDatum(Integer.parseInt(edVeld.getText()),Integer.parseInt(emVeld.getText()),Integer.parseInt(ejVeld.getText()));
					//check of de begindatum niet na de einddatum is
					if(begDat.after(eindDat)){
						JOptionPane.showMessageDialog(null, "De begindatum is na de einddatum!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					else{
						boolean b = false;
						for(Reservering r : bedrijf.getAlleReserveringen()){
							//check of de reservering niet hetzelfde is als de geselecteerde
							if(!r.getAuto().getKenteken().equalsIgnoreCase(deReservering.getAuto().getKenteken())){
								//check of de reservering tussen de ingevoerde datum zit
								//check of de reservering op dezelfde parkeerplek is
								if(r.isTussenDatum(begDat, eindDat) && r.getDeParkeerplek() == deReservering.getDeParkeerplek()){
									b = true;
								}
							}
						}
						if(b == true){
							JOptionPane.showMessageDialog(null, "Er is al een reservering op deze plek!", "Error!", JOptionPane.PLAIN_MESSAGE);
						}
						else{
							deReservering.setData(begDat, eindDat);
							JOptionPane.showMessageDialog(null, "Reservering gewijzigd: " + deReservering, "Reservering gewijzigd!", JOptionPane.PLAIN_MESSAGE);
							hoofdFrame.setVisible(true);
							hoofdFrame.clearData();
							this.dispose();
						}
					}
				}
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "Error: " + e, "Error!", JOptionPane.PLAIN_MESSAGE);
			}
		}
		else if (event.getSource() == annuleerKnop) {
			hoofdFrame.setVisible(true);
			hoofdFrame.clearData();
			this.dispose();
		}
	}
}