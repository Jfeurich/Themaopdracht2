package voorraadbeheer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import domeinklassen.Product;

public class ProductFrame extends JFrame implements ActionListener {
	private ArrayList<Product> deVoorraad;
	private HoofdFrame hf;
	private JButton b1, b2, b3, b4, b5;
	private JLabel l1, l2, l3;
	private JTextField tf1, tf2, tf3;
	public ProductFrame(ArrayList<Product> dV, HoofdFrame hoofdFrame) {
		hf = hoofdFrame;
		deVoorraad = dV;
		JPanel hulp = new JPanel();
		hulp.setLayout(new GridLayout(4, 1));
		hulp.setBorder(new EmptyBorder(5, 5, 5, 5));
		b1 = new JButton("Nieuw product"); hulp.add(b1);
		b1.addActionListener(this);
		b2 = new JButton("Wijzig product"); hulp.add(b2);
		b2.addActionListener(this);
		b3 = new JButton("Alle producten"); hulp.add(b3);
		b3.addActionListener(this);
		b4 = new JButton("Verwijder product"); hulp.add(b4);
		b4.addActionListener(this);
		add(hulp, BorderLayout.NORTH);
		b5 = new JButton("Sluit"); add(b5, BorderLayout.SOUTH);
		b5.addActionListener(this);
		pack(); setTitle("Voorraad");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent event) {
		//alle menu knoppen die horen bij het product
		if (event.getSource() == b1) {
			NieuwProductFrame npf = new NieuwProductFrame(deVoorraad, this);
			npf.setVisible(true);
			this.setVisible(false);
		}
		else if (event.getSource() == b2) {
			WijzigProductFrame wpf = new WijzigProductFrame(deVoorraad, this);
			wpf.setVisible(true);
			this.setVisible(false);
		}
		else if (event.getSource() == b3) {
			String s = "Alle producten: ";
			for(Product p: deVoorraad){
				s += "\n" + p;
			}
			JOptionPane.showMessageDialog(null, s, "Gelukt!", JOptionPane.PLAIN_MESSAGE);
		}
		else if (event.getSource() == b4) {
			VerwijderProductFrame verwpf = new VerwijderProductFrame(deVoorraad, this);
			verwpf.setVisible(true);
			this.setVisible(false);
		}
		else if (event.getSource() == b5) {
			hf.setVisible(true);
			this.dispose();
		}
	}
}