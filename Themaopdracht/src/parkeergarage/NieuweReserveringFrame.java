package parkeergarage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import domeinklassen.Reservering;

public class NieuweReserveringFrame extends JFrame implements ActionListener {
	private Bedrijf bedrijf;
	private HoofdFrame hoofdFrame;
	private JButton bevestigKnop, annuleerKnop;
	private JTextField kentekenVeld;
	private JLabel kentekenLabel, leegLabel, infoLabel;
	private int deParkeerplek;
	private Date begDat, eindDat;
	private Reservering r;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	public NieuweReserveringFrame(Bedrijf b, HoofdFrame hf, Date bD, Date eD, int dP) {
		bedrijf = b;
		hoofdFrame = hf;
		begDat = bD;
		eindDat = eD;
		deParkeerplek = dP;
		r = new Reservering(begDat, eindDat, deParkeerplek);
		//Label met alle info
		infoLabel = new JLabel("Plek: " + deParkeerplek + "; van " + df.format(begDat) + " tot " + df.format(eindDat));
		add(infoLabel, BorderLayout.NORTH);
		//Panel waarin alle gegevens ingevuld kunnen worden
		JPanel hulpPaneel = new JPanel();
		hulpPaneel.setLayout(new GridLayout(2, 2));
		hulpPaneel.setBorder(new EmptyBorder(10, 10, 10, 10));
		kentekenLabel = new JLabel("Kenteken: ");
		hulpPaneel.add(kentekenLabel);
		kentekenVeld = new JTextField(10);
		hulpPaneel.add(kentekenVeld);
		leegLabel = new JLabel("");
		hulpPaneel.add(leegLabel);
		bevestigKnop = new JButton("Bevestig");
		bevestigKnop.addActionListener(this);
		hulpPaneel.add(bevestigKnop);
		add(hulpPaneel, BorderLayout.CENTER);
		//De annuleerknop
		annuleerKnop = new JButton("Annuleer");
		annuleerKnop.addActionListener(this);
		add(annuleerKnop, BorderLayout.SOUTH);
		pack(); setTitle("Nieuwe reservering");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == bevestigKnop) {
			Auto deAuto = null;
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
				r.voegAutoToe(deAuto);
				bedrijf.voegReserveringToe(r);
				JOptionPane.showMessageDialog(null, "Reservering succesvol gemaakt!", "Gelukt!", JOptionPane.PLAIN_MESSAGE);
				hoofdFrame.setVisible(true);
				hoofdFrame.clearData();
				this.dispose();
			}
		}
		else if (event.getSource() == annuleerKnop) {
			hoofdFrame.setVisible(true);
			hoofdFrame.clearData();
			this.dispose();
		}
	}
}