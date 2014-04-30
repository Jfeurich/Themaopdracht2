package facturatie;

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

import domeinklassen.Bedrijf;
import domeinklassen.Factuur;
import domeinklassen.Klus;

public class OverzichtFactuurFrame extends JFrame implements ActionListener {
	private Bedrijf bedrijf;
	private HoofdFrame hoofdFrame;
	private JLabel bLabel, eLabel, leegLabel;
	private JTextField bdVeld, bmVeld, bjVeld, edVeld, emVeld, ejVeld;
	private JButton zoekKnop, toonKnop, annuleerKnop;
	private JComboBox box;
	private Date begDat = null;
	private Date eindDat = null;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private String geselecteerde;

	public OverzichtFactuurFrame(Bedrijf b, HoofdFrame hf) {
		bedrijf = b;
		hoofdFrame = hf;
		//Hulppaneel met daarop alle velden, knoppen en labels om de datums in te voeren
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
		zoekKnop = new JButton("Bekijk");
		hulpPaneel.add(zoekKnop);
		zoekKnop.addActionListener(this);
		add(hulpPaneel, BorderLayout.NORTH);
		//combobox om een factuur te selecteren
		JPanel boxPaneel = new JPanel();
		box = new JComboBox();
		boxPaneel.add(box);
		box.addActionListener(this);
		toonKnop = new JButton("Toon factuur");
		boxPaneel.add(toonKnop);
		toonKnop.addActionListener(this);
		add(boxPaneel, BorderLayout.CENTER);
		//De annuleerknop
		annuleerKnop = new JButton("Annuleer");
		add(annuleerKnop, BorderLayout.SOUTH);
		annuleerKnop.addActionListener(this);
		pack(); setTitle("Menu");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void onSelectedItemChanged() {
		Object obj = box.getSelectedItem();
		geselecteerde = (String)obj;
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == zoekKnop){
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
						JOptionPane.showMessageDialog(null, "Facturen van " + df.format(begDat) + " tot " + df.format(eindDat), "Overzicht", JOptionPane.PLAIN_MESSAGE);
						box.removeAllItems();
						for(Klus k : bedrijf.getKlussen()){
							if(k.getFactuur() != null && begDat != null && eindDat != null){
								if(k.getFactuur().getAanmaakDatum().after(begDat) && k.getFactuur().getAanmaakDatum().before(eindDat)){
									box.addItem(k.getAuto().getKenteken());
								}
							}
						}
						onSelectedItemChanged();
					}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "Error: " + e, "Error!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		else if(event.getSource() == toonKnop){
			for(Klus k : bedrijf.getKlussen()){
				if(k.getAuto().getKenteken().equalsIgnoreCase(geselecteerde) && k.getFactuur().getAanmaakDatum().after(begDat) && k.getFactuur().getAanmaakDatum().before(eindDat)){
					Factuur f = k.getFactuur();
					//de factuur wordt getoond
					JOptionPane.showMessageDialog(null, "Factuur gevonden: \n" + f.toString(bedrijf.getBTW()), "Voorbeeld!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		else if(event.getSource() == box){
			onSelectedItemChanged();
		}
		else if(event.getSource() == annuleerKnop){
			hoofdFrame.setVisible(true);
			this.dispose();
		}
	}
}