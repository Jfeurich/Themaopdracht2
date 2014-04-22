package klantenbinding;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import domeinklassen.Auto;
import domeinklassen.Klant;

public class KlantFrame extends ZoekFrame{

	public KlantFrame(HoofdFrame deMainFrame, ArrayList<Auto> au, ArrayList<Klant> kl){
		super(deMainFrame, au, kl);
		info.setText("Alle klanten");
		setTitle("Zoek klant");
		// de methode om de kentekens van alle auto's aan de combobox toe te voegen wordt aangeroepen
		addAlleKlanten(box);
		onSelectedItemChanged();
	}

	public void actionPerformed(ActionEvent e){
		//de methode om de meest recente herinneringsbrief van de geselecteerde klant te vinden wordt aangeroepen
		if (e.getSource() == zoek) {
			for(Klant k: klanten){
				// alle klanten worden doorgelopen tot de geselecteerde klant is gevonden
				if(geselecteerde.equals(k.getNaam())){
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