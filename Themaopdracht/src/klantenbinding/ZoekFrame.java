package klantenbinding;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domeinklassen.Auto;
import domeinklassen.Herinneringsbrief;
import domeinklassen.Klant;

public class ZoekFrame extends JFrame implements ActionListener{
	//dit is een superklasse die als template geldt voor AutoFrame en KlantFrame
	//alleen de actionperformed is anders bij beide en de inhoud van de combobox
	protected JLabel info;
	protected JPanel hulp;
	protected JComboBox box;
	protected JButton zoek, sluit;
	protected ArrayList<Klant> klanten;
	protected ArrayList<Auto> autos;
	protected HoofdFrame mf;
	protected String geselecteerde;

	public ZoekFrame(HoofdFrame deMainFrame, ArrayList<Auto> au, ArrayList<Klant> kl) {
		mf = deMainFrame;
		autos = au;
		klanten = kl;
		hulp = new JPanel();
		hulp.setLayout(new GridLayout(3, 1));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		info = new JLabel("");
		hulp.add(info);
		// een lege combobox wordt aangemaakt
		box = new JComboBox();
		hulp.add(box);
		box.addActionListener(this);
		zoek = new JButton("Zoek");
		zoek.addActionListener(this);
		hulp.add(zoek);
		add(hulp, BorderLayout.NORTH);
		sluit = new JButton("Sluit");
		sluit.addActionListener(this);
		add(sluit, BorderLayout.SOUTH);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void onSelectedItemChanged() {
		Object obj = box.getSelectedItem();
		geselecteerde = (String)obj;
	}

	public void actionPerformed(ActionEvent e) {
	}
	// een methode om de namen van alle klanten toe te voegen aan de lege combobox
	protected void addAlleKlanten(JComboBox box){
		int i = 0;
		for(Klant k: klanten){
			box.addItem(k.getNaam());
			i++;
		}
	}
	// een methode om de kentekens van alle autos toe te voegen aan de lege combobox
	public void addAlleAutos(JComboBox box){
		int i = 0;
		for(Auto a: autos){
			box.addItem(a.getKenteken());
			i++;
		}
	}
	// de meest recente herinneringsbrief van de klant wordt opgehaald
	public Herinneringsbrief getHerinneringsbrief(Klant k){
		Herinneringsbrief laatsteBrief = null;
		for(Herinneringsbrief hb: k.getHerinneringsBrieven()){
			if(laatsteBrief == null){
				laatsteBrief = hb;
			}
			if(hb.getDatum().after(laatsteBrief.getDatum())){
				laatsteBrief = hb;
			}
		}
		return laatsteBrief;
	}
}