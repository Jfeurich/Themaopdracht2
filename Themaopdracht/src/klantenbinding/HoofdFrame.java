package klantenbinding;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import domeinklassen.Auto;
import domeinklassen.Bedrijf;
import domeinklassen.Klant;

public class HoofdFrame extends JFrame implements ActionListener{
	//menu van de applet
	private Bedrijf hetBedrijf;
	private JButton[] knop = new JButton[3];
	private ArrayList<Klant> klanten = new ArrayList<Klant>();
	private ArrayList<Auto> autos = new ArrayList<Auto>();

	public HoofdFrame(Bedrijf b){
		hetBedrijf = b;
		klanten = b.getAlleKlanten();
		for(Klant k : klanten){
			ArrayList<Auto> autosperklant = new ArrayList<Auto>(k.getAutos());
			if(autosperklant.size() > 0){
				for(Auto a : autosperklant){
					autos.add(a);
				}
			}
		}
		setLayout(new GridLayout(3, 1, 5, 5));

		for(int i = 0; i < knop.length; i++){
			knop[i] = new JButton("");
			knop[i].addActionListener(this);
			add(knop[i]);
			switch(i){
				case 0: knop[i].setText("Overzicht herinneringsbrieven"); break;
				case 1: knop[i].setText("Zoeken herinneringsbrief op auto"); break;
				case 2: knop[i].setText("Zoeken herinneringsbrief op klant"); break;
			}
		}

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		for(int i = 0; i < knop.length; i++){
			if(e.getSource() == knop[i]){
				switch(i){
					case 0: HerinneringsbriefFrame hbf = new HerinneringsbriefFrame(this, klanten); hbf.setVisible(true); this.setVisible(false); break;
					case 1: ZoekFrame af = new AutoFrame(this, autos, klanten); af.setVisible(true); this.setVisible(false); break;
					case 2: ZoekFrame kf = new KlantFrame(this, autos, klanten); kf.setVisible(true); this.setVisible(false); break;
				}
			}
		}
	}
}