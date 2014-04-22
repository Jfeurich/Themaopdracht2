package parkeergarage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import domeinklassen.Bedrijf;
import domeinklassen.Reservering;

public class HoofdFrame extends JFrame implements ActionListener {
	private Bedrijf bedrijf;
	private JButton parkeerPlekken[] = new JButton[40];
	private JButton wijzigenKnop, overzichtKnop, toonKnop;
	private JLabel begDatLabel, eindDatLabel;
	private JTextField begDatDagVeld, begDatMaandVeld, begDatJaarVeld, eindDatDagVeld, eindDatMaandVeld, eindDatJaarVeld;
	private Date begDat, eindDat;
	private Timer t;
	public HoofdFrame(Bedrijf b) {
		bedrijf = b;
		//Panel om de datum in te voeren
		JPanel datumsPaneel = new JPanel();
		datumsPaneel.setLayout(new GridLayout(1, 8));
		datumsPaneel.setBorder(new EmptyBorder(10, 10 , 10, 10));
		//Begindatum velden
		begDatLabel = new JLabel("Begin datum:");
		datumsPaneel.add(begDatLabel);
		begDatDagVeld = new  JTextField(10);
		datumsPaneel.add(begDatDagVeld);
		begDatDagVeld.addActionListener(this);
		begDatMaandVeld = new  JTextField(10);
		datumsPaneel.add(begDatMaandVeld);
		begDatMaandVeld.addActionListener(this);
		begDatJaarVeld = new  JTextField(10);
		datumsPaneel.add(begDatJaarVeld);
		begDatJaarVeld.addActionListener(this);
		//Einddatum velden
		eindDatLabel = new JLabel("Eind datum:");
		datumsPaneel.add(eindDatLabel);
		eindDatDagVeld = new JTextField(10);
		datumsPaneel.add(eindDatDagVeld);
		eindDatDagVeld.addActionListener(this);
		eindDatMaandVeld = new JTextField(10);
		datumsPaneel.add(eindDatMaandVeld);
		eindDatMaandVeld.addActionListener(this);
		eindDatJaarVeld = new JTextField(10);
		datumsPaneel.add(eindDatJaarVeld);
		eindDatJaarVeld.addActionListener(this);
		//Knop om de parkeerplekken te updaten op basis van de datum
		toonKnop = new JButton("Toon");
		datumsPaneel.add(toonKnop);
		toonKnop.addActionListener(this);
		add(datumsPaneel, BorderLayout.NORTH);
		//Panel waarop de buttons van de parkeerplekken komen
		JPanel plekkenPaneel = new JPanel();
		plekkenPaneel.setLayout(new GridLayout(8, 5));
		plekkenPaneel.setBorder(new EmptyBorder(10, 10, 10, 10));
		for(int i = 0; i < 40; i++){
			int nummer = i + 1;
			parkeerPlekken[i] = new JButton("" + nummer);
			plekkenPaneel.add(parkeerPlekken[i]);
			parkeerPlekken[i].addActionListener(this);
			parkeerPlekken[i].setBackground(Color.GREEN);
		}
		add(plekkenPaneel, BorderLayout.CENTER);
		JPanel hulpPaneel = new JPanel();
		hulpPaneel.setLayout(new GridLayout(1, 2));
		hulpPaneel.setBorder(new EmptyBorder(10, 10, 10, 10));
		wijzigenKnop = new JButton("Reservering wijzigen");
		hulpPaneel.add(wijzigenKnop);
		//Panel met de knoppen om een reservering te wijzigen of een overzicht op te vragen
		wijzigenKnop.addActionListener(this);
		overzichtKnop = new JButton("Overzicht opvragen");
		hulpPaneel.add(overzichtKnop);
		overzichtKnop.addActionListener(this);
		add(hulpPaneel, BorderLayout.SOUTH);
		pack();
		setTitle("Parkeergarage");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		t = new Timer(10, this);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == wijzigenKnop){
			WijzigReserveringFrame wrf = new WijzigReserveringFrame(bedrijf, this);
			wrf.setVisible(true);
			this.setVisible(false);
		}
		else if(event.getSource() == overzichtKnop){
			OverzichtFrame of = new OverzichtFrame(bedrijf, this);
			of.setVisible(true);
			this.setVisible(false);
		}
		else if(event.getSource() == toonKnop){
			//check of de party people er klaar voor zijn
			if(begDatDagVeld.getText().equalsIgnoreCase("party") && begDatMaandVeld.getText().equalsIgnoreCase("people") && begDatJaarVeld.getText().equalsIgnoreCase("are") && eindDatDagVeld.getText().equalsIgnoreCase("you") && eindDatMaandVeld.getText().equalsIgnoreCase("ready") && eindDatJaarVeld.getText().equalsIgnoreCase("?")){
				t.start();
			}
			else{
				t.stop();
				//Begin dag ongeldig
				if(!begDatDagVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(begDatDagVeld.getText()) < 1 || Integer.parseInt(begDatDagVeld.getText()) > 31){
					JOptionPane.showMessageDialog(null, "Begin dag is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				//Begin maand ongeldig
				else if(!begDatMaandVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(begDatMaandVeld.getText()) < 0 || Integer.parseInt(begDatMaandVeld.getText()) > 12){
					JOptionPane.showMessageDialog(null, "Begin maand is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				//Begin jaar ongeldig
				else if(!begDatJaarVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(begDatJaarVeld.getText()) <= 2013 || Integer.parseInt(begDatJaarVeld.getText()) >= 10000){
					JOptionPane.showMessageDialog(null, "Begin jaar is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				//Eind dag ongeldig
				else if(!eindDatDagVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(eindDatDagVeld.getText()) < 1 || Integer.parseInt(eindDatDagVeld.getText()) > 31){
					JOptionPane.showMessageDialog(null, "Eind dag is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				//Eind maand ongeldig
				else if(!eindDatMaandVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(eindDatMaandVeld.getText()) < 0 || Integer.parseInt(eindDatMaandVeld.getText()) > 12){
					JOptionPane.showMessageDialog(null, "Eind maand is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				//Eind jaar ongeldig
				else if(!eindDatJaarVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(eindDatJaarVeld.getText()) <= 2013 || Integer.parseInt(eindDatJaarVeld.getText()) >= 10000){
					JOptionPane.showMessageDialog(null, "Eind jaar is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
				}
				//Alle velden zijn oké
				else{
					try{
						begDat = bedrijf.maakDatum(Integer.parseInt(begDatDagVeld.getText()), Integer.parseInt(begDatMaandVeld.getText()), Integer.parseInt(begDatJaarVeld.getText()));
						eindDat = bedrijf.maakDatum(Integer.parseInt(eindDatDagVeld.getText()), Integer.parseInt(eindDatMaandVeld.getText()), Integer.parseInt(eindDatJaarVeld.getText()));
						//check of dat einddatum niet voor de begin datum is
						if(eindDat.after(begDat) || begDat.equals(eindDat)){
							//Alle knoppen worden weer gereset
							for(int i = 0; i < 40; i++){
								int nummer = i + 1;
								parkeerPlekken[i].setEnabled(true);
								parkeerPlekken[i].setText(nummer + "");
								parkeerPlekken[i].setBackground(Color.GREEN);
							}
							//Alle reserveringen worden langsgelopen of ze binnen deze periode vallen
							for(Reservering r: bedrijf.getAlleReserveringen()){
								int i = r.getDeParkeerplek() - 1;
								if(r.isTussenDatum(begDat, eindDat)){
									parkeerPlekken[i].setEnabled(false);
									parkeerPlekken[i].setText("BEZET");
									parkeerPlekken[i].setBackground(Color.RED);
								}
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "De eindatum is voor de begindatum!", "Error!", JOptionPane.PLAIN_MESSAGE);
						}
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null, "Error: " + e, "Error!", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
		//dit is niks speciaals
		else if(event.getSource() == t){
			int plek = (int) (40 * Math.random());
			int kleur = (int) (7 * Math.random());
			switch(kleur){
				case 0: parkeerPlekken[plek].setBackground(Color.BLUE); break;
				case 1: parkeerPlekken[plek].setBackground(Color.GREEN); break;
				case 2: parkeerPlekken[plek].setBackground(Color.YELLOW); break;
				case 3: parkeerPlekken[plek].setBackground(Color.RED); break;
				case 4: parkeerPlekken[plek].setBackground(Color.PINK); break;
				case 5: parkeerPlekken[plek].setBackground(Color.ORANGE); break;
				case 6: parkeerPlekken[plek].setBackground(Color.MAGENTA); break;
			}
		}
		//check of het één van de 40 parkeerplek knoppen is
		else {
			for(int i = 0; i < 40; i++){
				if (event.getSource() == parkeerPlekken[i]) {
					//Begin dag ongeldig
					if(!begDatDagVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(begDatDagVeld.getText()) < 1 || Integer.parseInt(begDatDagVeld.getText()) > 31){
						JOptionPane.showMessageDialog(null, "Begin dag is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					//Begin maand ongeldig
					else if(!begDatMaandVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(begDatMaandVeld.getText()) < 0 || Integer.parseInt(begDatMaandVeld.getText()) > 12){
						JOptionPane.showMessageDialog(null, "Begin maand is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					//Begin jaar ongeldig
					else if(!begDatJaarVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(begDatJaarVeld.getText()) <= 2013 || Integer.parseInt(begDatJaarVeld.getText()) >= 10000){
						JOptionPane.showMessageDialog(null, "Begin jaar is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					//Eind dag ongeldig
					else if(!eindDatDagVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(eindDatDagVeld.getText()) < 1 || Integer.parseInt(eindDatDagVeld.getText()) > 31){
						JOptionPane.showMessageDialog(null, "Eind dag is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					//Eind maand ongeldig
					else if(!eindDatMaandVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(eindDatMaandVeld.getText()) < 0 || Integer.parseInt(eindDatMaandVeld.getText()) > 12){
						JOptionPane.showMessageDialog(null, "Eind maand is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					//Eind jaar ongeldig
					else if(!eindDatJaarVeld.getText().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+") || Integer.parseInt(eindDatJaarVeld.getText()) <= 2013 || Integer.parseInt(eindDatJaarVeld.getText()) >= 10000){
						JOptionPane.showMessageDialog(null, "Eind jaar is ongeldig!", "Error!", JOptionPane.PLAIN_MESSAGE);
					}
					//Alle velden zijn oké
					else{
						try{
							begDat = bedrijf.maakDatum(Integer.parseInt(begDatDagVeld.getText()), Integer.parseInt(begDatMaandVeld.getText()), Integer.parseInt(begDatJaarVeld.getText()));
							eindDat = bedrijf.maakDatum(Integer.parseInt(eindDatDagVeld.getText()), Integer.parseInt(eindDatMaandVeld.getText()), Integer.parseInt(eindDatJaarVeld.getText()));
							///check of dat einddatum niet voor de begin datum is
							if(begDat.before(eindDat) || begDat.equals(eindDat)){
								int parkeerPlekNummer = i + 1;
								NieuweReserveringFrame nrf = new NieuweReserveringFrame(bedrijf, this, begDat, eindDat, parkeerPlekNummer);
								nrf.setVisible(true);
								this.setVisible(false);
							}
							else{
								JOptionPane.showMessageDialog(null, "De eindatum is voor de begindatum!", "Error!", JOptionPane.PLAIN_MESSAGE);
							}
						}
						catch(Exception e){
							JOptionPane.showMessageDialog(null, "Error: " + e, "Error!", JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			}
		}
	}
	//methode zodat als een NieuweReserveringFrame gesloten wordt er niet nog een reservering met dezelfde data op dezelfde plek gemaakt kan worden
	public void clearData(){
		begDatDagVeld.setText("");
		begDatMaandVeld.setText("");
		begDatJaarVeld.setText("");
		eindDatDagVeld.setText("");
		eindDatMaandVeld.setText("");
		eindDatJaarVeld.setText("");
		begDat = null;
		eindDat = null;
		repaintParkeerplekken();
	}
	public void repaintParkeerplekken(){
		for(int i = 0; i < 40; i++){
			int nummer = i + 1;
			parkeerPlekken[i].setEnabled(true);
			parkeerPlekken[i].setText(nummer + "");
			parkeerPlekken[i].setBackground(Color.GREEN);
		}
	}
}