package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.GridStart;

public class WinManager extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6421255455563013157L;
	private Vector<GridStart> list;
	private JComboBox/* <GridStart> */combo = new JComboBox/* <GridStart> */();
	private JButton nouv, modif, supp;
	private GridStart focus;
	public PaneInitPawn panelCenter;

	public WinManager(int i){
		this.setLayout(new GridLayout());
		
	}
	
	public WinManager() {
		this.setLayout(new BorderLayout());
		this.setTitle("Gestionnaire de grille de depart ");
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		load();
		JPanel paneNorth = new JPanel();
		JPanel paneSouth = new JPanel();
		focus = list.get(0);
		paneNorth.setLayout(new GridLayout(1, 3));
		paneSouth.setLayout(new GridLayout(1, 2));
		for (int i = 0; i < list.size(); i++) {
			combo.addItem(list.get(i));
		}
		combo.addActionListener(new ChoixCombo());
		JPanel paneCombo = new JPanel();
		JPanel paneNouv = new JPanel();
		JLabel lab = new JLabel("Selectionnez une grille");
		JLabel lab2 = new JLabel("ou creer une ");
		paneCombo.add(lab);
		paneCombo.add(combo);
		paneNorth.add(paneCombo);
		nouv = new JButton("nouvelle grille");
		modif = new JButton("modifier");
		supp = new JButton("supprimer");
		supp.addActionListener(new actionSupp());
		paneSouth.add(modif);
		paneSouth.add(supp);
		paneNouv.add(lab2);
		paneNouv.add(nouv);
		paneNorth.add(paneNouv);

		panelCenter = new PaneInitPawn();
		// panelCenter.setMinimumSize(new Dimension(this.getWidth(),300));
		// paneSouth.setSize(50, 50);

		this.add(paneNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(paneSouth, BorderLayout.SOUTH);
		this.validate();

	}

	public void load() {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream("gridStart.save"));
			Vector<GridStart> vector = (Vector<GridStart>) in.readObject();
			this.list = vector;
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void close() {
		this.dispose();
	}

	class ChoixCombo implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			focus = (GridStart) combo.getSelectedItem();
			System.out.println(focus + " focus");
			panelCenter.setGrid(focus.getGrid());
			panelCenter.repaint();
		}
	}

	class actionSupp implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			focus.delete();
			/* WinManager fen = */new WinManager();
			close();
		}
	}

	class ItemState implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			System.out.println("�v�nement d�clench� sur : " + e.getItem());
		}
	}
}
