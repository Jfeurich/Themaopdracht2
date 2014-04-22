package klantenbinding;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domeinklassen.Auto;
import domeinklassen.Klant;

public class HerinneringsbriefFrame extends JFrame implements ActionListener{
	private HoofdFrame mf;
	private ArrayList<Klant> klanten;
	private JButton b1, b2, b3;
	private JLabel l1, l2;
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private Date datum = new Date();
	public HerinneringsbriefFrame(HoofdFrame deMainFrame, ArrayList<Klant> kl){
		if(datum.getMonth() <= 2){
			datum.setMonth(datum.getMonth()+10);
			datum.setYear(datum.getYear()-1);
		}
		else{
			datum.setMonth(datum.getMonth() - 2);
		}
		mf = deMainFrame;
		klanten = kl;
		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(2, 2));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		l1 = new JLabel("Overzicht van klanten die \nlanger dan 6 maanden geen \nonderhoudsbeurt hebben gehad"); hulp.add(l1);
		b1 = new JButton("Overzicht tonen"); hulp.add(b1);
		b1.addActionListener(this);
		l2 = new JLabel("Overzicht van klanten die \nvoormalig regelmatig langs kwamen \nmaar nu langer dan 2 maanden niet zijn geweest"); hulp.add(l2);
		b2 = new JButton("Overzicht tonen"); hulp.add(b2);
		b2.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b3 = new JButton("Sluit"); add(b3, BorderLayout.SOUTH);
		b3.addActionListener(this);
		pack(); setTitle("Voorraad");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == b1) {
			String s = "Dit is een overzicht van alle klanten die al langer dan 6 maanden \ngeen onderhoudsbeurt hebben laten uitvoeren: ";
			// alle klanten worden doorgelopen, er wordt gekeken per klant of een of meerdere van zijn auto's onderhoud nodig heeft
			// dit wordt vervolgens getoond via een JOptionPane
			for(Klant k: klanten){
				try{
					ArrayList<Auto> autos = new ArrayList<Auto>(k.onderhoudNodig());
					if(autos.size() >= 1){
						s += "\n" + k.toString();
						for(Auto a: autos){
							s += "\n" + a;
						}
					}
				}
				catch(Exception error){
					JOptionPane.showMessageDialog(null, "Error: " + error, "Error!", JOptionPane.PLAIN_MESSAGE);
				}
			}
			JOptionPane.showMessageDialog(null, s, "", JOptionPane.PLAIN_MESSAGE);
		}
		else if (e.getSource() == b2) {
			String s = "Dit is een overzicht van alle klanten die al langer dan 2 maanden niet zijn geweest:";
			// alle klanten worden doorgelopen om te kijken welke er de afgelopen twee maanden niet zijn geweest
			// er wordt ook gecontroleerd of ze voor een jaar geleden meer dan 6x langs zijn geweest (dus een vaste klant waren)
			// als dat het geval is worden zijn gegevens getoond via een JOptionPane
			for(Klant k: klanten){
				try{
					if(k.isRegelmatig() && (k.laatsteBezoek().before(datum))){
						s += "\n" + k.toString() + "\nLaatste bezoek op: " + df.format(k.laatsteBezoek());
					}
				}
				catch(Exception error){
					JOptionPane.showMessageDialog(null, "Error: " + error, "Error!", JOptionPane.PLAIN_MESSAGE);
				}
			}
			JOptionPane.showMessageDialog(null, s, "", JOptionPane.PLAIN_MESSAGE);
		}
		else if (e.getSource() == b3) {
			mf.setVisible(true);
			this.dispose();
		}
	}
}