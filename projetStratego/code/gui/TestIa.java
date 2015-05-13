package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.IA;

public class TestIa extends JFrame {

	private static final long serialVersionUID = -3048638208007991240L;
	public JComboBox<String> ia1 = new JComboBox<String>(),
			ia2 = new JComboBox<String>();
	public JSlider nbr = new JSlider();
	public JButton goBtn = new JButton("Lancer les tests");
	public String[] listLvl;
	JLabel lab1;

	/**
	 * 
	 */
	public TestIa() {
		this.setResizable(true);
		this.setSize(300, 320);
		this.setLocationRelativeTo(null);
		this.setTitle("Tester l'IA");
		this.setLayout(new GridLayout(4, 1));
		this.setVisible(true);
		listLvl = IA.getListLvl();

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();

		nbr.setMaximum(100);
		nbr.setMinimum(0);
		nbr.setPaintTicks(true);
		nbr.setPaintLabels(true);
		nbr.setMinorTickSpacing(10);
		nbr.setMajorTickSpacing(20);

		lab1 = new JLabel("Nombre de tests a effectuer: " + nbr.getValue());
		panel1.add(lab1);
		panel1.add(nbr);
		this.add(panel1);

		for (int i = 0; i < listLvl.length; i++) {
			ia1.addItem(listLvl[i]);
			ia2.addItem(listLvl[i]);
		}
		JLabel lab2 = new JLabel("Niveau de l'IA 1");
		JLabel lab3 = new JLabel("Niveau de l'IA 2");

		panel2.add(lab2);
		panel2.add(ia1);

		panel3.add(lab3);
		panel3.add(ia2);

		panel4.add(goBtn);

		this.add(panel2);
		this.add(panel3);
		this.add(panel4);

		nbr.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				lab1.setText("Nombre de tests a effectuer: "
						+ ((JSlider) event.getSource()).getValue());
			}
		});

		goBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nbr.getValue() != 0) {
					new ProgressTestIa(nbr.getValue(), (String) ia1
							.getSelectedItem(), (String) ia2.getSelectedItem());
				} else {
					JOptionPane.showMessageDialog(null,
							"Veuillez entrer un nombre valable", "Information",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

	}

}
