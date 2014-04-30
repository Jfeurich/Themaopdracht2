package facturatie;

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

import domeinklassen.Bedrijf;
import domeinklassen.Factuur;
import domeinklassen.Klus;

public class BTWOverzichtFrame extends JFrame implements ActionListener {
	private Bedrijf bedrijf;
	private HoofdFrame hoofdFrame;
	private JLabel bLabel, eLabel, leegLabel;
	private JTextField bdVeld, bmVeld, bjVeld, edVeld, emVeld, ejVeld;
	private JButton toonKnop, annuleerKnop;
	private Date begDat, eindDat;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	public BTWOverzichtFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;
		//Hulppaneel met daarop alle velden, knoppen en labels
		JPanel hulpPaneel = new JPanel();
		hulpPaneel.setLayout(new GridLayout(2, 5));
		hulpPaneel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bLabel = new JLabel("Begindatum");
		hulpPaneel.add(bLabel);
		bdVeld = new JTextField(2);
		hulpPaneel.add(bdVeld);
		bmVeld = new JTextField(2);
		hulpPaneel.add(bmVeld);
		bjVeld = new JTextField(4);
		hulpPaneel.add(bjVeld);
		leegLabel = new JLabel("");
		hulpPaneel.add(leegLabel);
		eLabel = new JLabel("Einddatum");
		hulpPaneel.add(eLabel);
		edVeld = new JTextField(2);
		hulpPaneel.add(edVeld);
		emVeld = new JTextField(2);
		hulpPaneel.add(emVeld);
		ejVeld = new JTextField(4);
		hulpPaneel.add(ejVeld);
		toonKnop = new JButton("Bekijk");
		hulpPaneel.add(toonKnop);
		toonKnop.addActionListener(this);
		add(hulpPaneel, BorderLayout.CENTER);
		//De annuleerknop
		annuleerKnop = new JButton("Annuleer");
		add(annuleerKnop, BorderLayout.SOUTH);
		annuleerKnop.addActionListener(this);
		pack(); setTitle("BTW overzicht");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == toonKnop){
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
				try{
					begDat = bedrijf.maakDatum(Integer.parseInt(bdVeld.getText()),Integer.parseInt(bmVeld.getText()),Integer.parseInt(bjVeld.getText()));
					eindDat = bedrijf.maakDatum(Integer.parseInt(edVeld.getText()),Integer.parseInt(emVeld.getText()),Integer.parseInt(ejVeld.getText()));
					//check of de begindatum niet na de einddatum is
					if(begDat.after(eindDat)){
						JOptionPane.showMessageDialog(null, "De begindatum is na de einddatum!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					else{
						String s ="BTW per factuur over de periode: " + df.format(begDat) + " tot " + df.format(eindDat);
						for(Klus k: bedrijf.getKlussen()){
							if(k.getFactuur() != null){
								Factuur f = k.getFactuur();
								if(f.getAanmaakDatum().after(begDat) && f.getAanmaakDatum().before(eindDat)){
									s += "\nDatum: " + df.format(f.getAanmaakDatum()) + "; Kenteken: " + k.getAuto().getKenteken() + "; Totaalbedrag zonder BTW: " + f.getTotaal() + "; BTW: " + f.getBTW(bedrijf.getBTW());
								}
							}
						}
						JOptionPane.showMessageDialog(null, s, "Overzicht", JOptionPane.PLAIN_MESSAGE);
					}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Error: " + e, "Error!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		else if(event.getSource() == annuleerKnop){
			hoofdFrame.setVisible(true);
			this.dispose();
		}
	}
}