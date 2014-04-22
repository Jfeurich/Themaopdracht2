package klantenbinding;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import domeinklassen.Auto;
import domeinklassen.Klant;

public class AutoFrame extends ZoekFrame{

	public AutoFrame(HoofdFrame deMainFrame, ArrayList<Auto> au, ArrayList<Klant> kl){
		super(deMainFrame, au, kl);
		info.setText("Alle auto's");
		setTitle("Zoek auto");
		// de methode om de namen van alle auto's aan de combobox toe te voegen wordt aangeroepen
		addAlleAutos(box);
		onSelectedItemChanged();
	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == zoek) {
			// de methode om de meest recente herinneringsbrief van de eigenaar van de geselecteerde auto te vinden wordt aangeroepen
			for(Auto a: autos){
				if(geselecteerde.equals(a.getKenteken())){
					// alle klanten worden doorgelopen tot de eigenaar van de auto is gevonden
					for(Klant k: klanten){
						for(Auto au: k.getAutos()){
							if(a.getKenteken().equals(au.getKenteken())){
								// de meest recente herinneringsbrief wordt getoond als de klant die heeft
								if(getHerinneringsbrief(k) == null){
									JOptionPane.showMessageDialog(null, "Deze klant heeft geen herinneringsbrieven!", "Error!", JOptionPane.PLAIN_MESSAGE);
								}
								else{
									JOptionPane.showMessageDialog(null, getHerinneringsbrief(k).toString(), "", JOptionPane.PLAIN_MESSAGE);
								}
							}
						}
					}
				}
			}
		}
		// methode om het frame te sluiten
		else if (e.getSource() == sluit) {
			mf.setVisible(true);
			this.dispose();
		}
		else if (e.getSource() == box) {
			onSelectedItemChanged();
		}
	}
}