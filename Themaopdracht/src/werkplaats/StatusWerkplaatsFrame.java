package werkplaats;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import domeinklassen.Bedrijf;
import domeinklassen.Klus;

public class StatusWerkplaatsFrame extends JFrame implements ActionListener{
	private WerkplaatsFrame werkplaatsframe;
	private JButton b1,b2,b3;
	private JComboBox bx,bx2;
	private Bedrijf bedrijf;
	private Klus deKlus;
	private String geselecteerde,geselecteerde2;

	public StatusWerkplaatsFrame(WerkplaatsFrame wf,Bedrijf b, Klus k){
		werkplaatsframe = wf;
		bedrijf = b;
		deKlus = k;
		setLayout(new FlowLayout());
		//combobox voor de facturen
		bx = new JComboBox();
		bx.addItem(deKlus); add(bx);
		bx2 = new JComboBox();
		bx2.addItem("Voltooid");
		bx2.addItem("Wordt gerepareerd");
		bx2.addItem("Wachten op onderdelen");
		add(bx2);
		bx.addActionListener(this);
		b1 = new JButton("Set Status"); add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Sluit"); add(b2);
		b2.addActionListener(this);
		pack(); setTitle("Status");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void onSelectedItemChanged() {
		Object obj = bx.getSelectedItem();
		geselecteerde = (String)obj;
	}
	public void onSelectedItemChangedStatus(){
		Object obj = bx2.getSelectedItem();
		geselecteerde2 = (String)obj;
	}
	public void setSelectedItem(){
		bx2.setSelectedItem(deKlus.getStatus());
	}

	public void actionPerformed(ActionEvent e){
		if (e.getSource() == b1) {
			deKlus.setStatus((String)bx2.getSelectedItem()); // Verandert de status van de klus
			String s = "De status van de klus is veranderd naar: " + deKlus.getStatus();
			JOptionPane.showMessageDialog(null,s, "GREAT SUCCES", JOptionPane.PLAIN_MESSAGE);
		}
		else if (e.getSource() == b2){
			werkplaatsframe.setVisible(true);
			this.dispose();
		}
		else if(e.getSource() == bx){
				onSelectedItemChanged();
		}
		else if(e.getSource() == bx2){
			onSelectedItemChangedStatus();
		}
	}
}