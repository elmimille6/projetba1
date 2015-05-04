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

import main.Game;
import main.GridStart;

public class WinManager extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6421255455563013157L;
	private Vector<GridStart> list;
	private JComboBox combo;
	private JButton newGrid, change, delete;
	private GridStart focus;
	public PaneInitPawn panelCenter;
	public JPanel paneCombo = new JPanel();

	public WinManager(int i) {
		this.setLayout(new GridLayout());

	}

	public WinManager() {
		this.setLayout(new BorderLayout());
		this.setTitle("Gestionnaire de grille de depart ");
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		if (load()) {
			JPanel paneNorth = new JPanel();
			JPanel paneSouth = new JPanel();
			focus = list.get(0);
			paneNorth.setLayout(new GridLayout(1, 3));
			paneSouth.setLayout(new GridLayout(1, 2));
			combo = new JComboBox();
			for (int i = 0; i < list.size(); i++) {
				combo.addItem(list.get(i));
			}
			combo.addActionListener(new ChoixCombo());

			JPanel paneNouv = new JPanel();
			JLabel selectGrid = new JLabel("Selectionnez une grille");
			JLabel createNewGrid = new JLabel("ou creez en une ");
			paneCombo.add(selectGrid);
			paneCombo.add(combo);
			paneNorth.add(paneCombo);
			newGrid = new JButton("Nouvelle grille");
			change = new JButton("Modifier");
			delete = new JButton("Supprimer");
			newGrid.addActionListener(new actionNewGrid());
			delete.addActionListener(new actionSupp());
			change.addActionListener(new actionModif());
			paneSouth.add(change);
			paneSouth.add(delete);
			paneNouv.add(createNewGrid);
			paneNouv.add(newGrid);
			paneNorth.add(paneNouv);

			panelCenter = new PaneInitPawn();
			// panelCenter.setMinimumSize(new Dimension(this.getWidth(),300));
			// paneSouth.setSize(50, 50);

			this.add(paneNorth, BorderLayout.NORTH);
			this.add(panelCenter, BorderLayout.CENTER);
			this.add(paneSouth, BorderLayout.SOUTH);
			this.validate();
		} else {
			/* WindowInitPawn fen = */new WindowInitPawn(new Game(10, 4, 0), 2);
			close();
		}
		// this.addFocusListener(new FocusListener() {
		// // Arriv�e du focus
		// public void focusGained(FocusEvent e) {
		// upCombo();
		// System.out.println("focus");
		// }
		//
		// @Override
		// public void focusLost(FocusEvent arg0) {
		// System.out.println("lost focus");
		// }
		// });
	}

	public void upCombo() {
		load();
		combo.removeAllItems();
		for (int i = 0; i < list.size(); i++) {
			combo.addItem(list.get(i));
		}
		combo.repaint();
		this.repaint();

	}

	@SuppressWarnings("resource")
	public boolean load() {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream("gridStart.save"));
			@SuppressWarnings("unchecked")
			Vector<GridStart> vector = (Vector<GridStart>) in.readObject();
			this.list = vector;
			in.close();
			if (vector.size() == 0) {
				return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("here");
			return false;
		} catch (IOException e) {
			System.out.println("here2");
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("here3");
			return false;
		}

	}

	public void close() {
		this.dispose();
	}

	class ChoixCombo implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (combo.getItemCount() != 0) {
				focus = (GridStart) combo.getSelectedItem();
				System.out.println(focus + " focus");
				panelCenter.setGrid(focus.getGrid());
				panelCenter.repaint();
			}
		}
	}

	class actionNewGrid implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new WindowInitPawn(new Game(10, 4, 0), 2);
			close();
		}
	}

	class actionSupp implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			focus.delete();
			upCombo();
		}
	}

	class actionModif implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Modif");
			new WindowInitPawn(new Game(focus.getGrid()), 1);
			close();
		}
	}

	class ItemState implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			System.out.println("Evenement declenche sur : " + e.getItem());
		}
	}
}
