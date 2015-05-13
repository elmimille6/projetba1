package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import main.Main;

/**
 * This class creates a grid manager.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class GridManager extends JFrame {

	private static final long serialVersionUID = -6421255455563013157L;
	private Vector<GridStart> list;
	private JComboBox/* <GridStart> */combo;
	private JButton newGrid, change, delete;
	private GridStart focus;
	public PaneInitPawn panelCenter;
	public JPanel paneCombo = new JPanel();

	/**
	 * Main constructor of the class.<br/>
	 * Creates a window where the user can delete or modify saved grid and
	 * create grid.
	 */
	public GridManager() {
		this.setLayout(new BorderLayout());
		this.setTitle("Gestionnaire de grille de depart ");
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		if (load()) {
			JPanel paneNorth = new JPanel();
			JPanel paneSouth = new JPanel();
			focus = list.get(0);
			paneNorth.setLayout(new GridLayout(1, 3));
			paneSouth.setLayout(new GridLayout(1, 2));
			combo = new JComboBox/* <GridStart> */();
			for (int i = 0; i < list.size(); i++) {
				combo.addItem(list.get(i));
			}
			combo.addActionListener(new ChoiceCombo());

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
			delete.addActionListener(new actionDelete());
			change.addActionListener(new actionModify());
			paneSouth.add(change);
			paneSouth.add(delete);
			paneNouv.add(createNewGrid);
			paneNouv.add(newGrid);
			paneNorth.add(paneNouv);

			panelCenter = new PaneInitPawn();

			this.add(paneNorth, BorderLayout.NORTH);
			this.add(panelCenter, BorderLayout.CENTER);
			this.add(paneSouth, BorderLayout.SOUTH);
			this.validate();
		} else {
			new WindowInitPawn(new Game(10, 4, 0), 2);
			close();
		}
	}

	/**
	 * Update the 'combo' JComboBox.
	 */
	public void updateCombo() {
		load();
		combo.removeAllItems();
		for (int i = 0; i < list.size(); i++) {
			combo.addItem(list.get(i));
		}
		combo.repaint();
		this.repaint();

	}

	/**
	 * This method loads the chosen grid from the "GridStart.save" file.
	 * 
	 * @return true if the grid is loaded,<br/>
	 *         false otherwise.
	 */
	@SuppressWarnings("resource")
	public boolean load() {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream("GridStart.save"));
			@SuppressWarnings("unchecked")
			Vector<GridStart> vector = (Vector<GridStart>) in.readObject();
			this.list = vector;
			in.close();
			if (vector.size() == 0) {
				Main.copyFile();
				return load();
			}
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (NullPointerException e) {
			Main.copyFile();
			return load();
		}

	}

	/**
	 * Closes the window.
	 */
	public void close() {
		this.dispose();
	}

	/**
	 * Action listener for the choice of the saved grid.
	 * 
	 * @author CAREDDA Giuliano, DUCOBU Alexandre
	 */
	class ChoiceCombo implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (combo.getItemCount() != 0) {
				focus = (GridStart) combo.getSelectedItem();
				panelCenter.setGrid(focus.getGrid());
				panelCenter.repaint();
			}
		}
	}

	/**
	 * Action listener to create a new grid.
	 * 
	 * @author CAREDDA Giuliano, DUCOBU Alexandre
	 */
	class actionNewGrid implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new WindowInitPawn(new Game(10, 4, 0), 2);
			close();
		}
	}

	/**
	 * Action listener to delete a saved grid.
	 * 
	 * @author CAREDDA Giuliano, DUCOBU Alexandre
	 */
	class actionDelete implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			focus.delete();
			updateCombo();
		}
	}

	/**
	 * Action listener to modify a saved grid.
	 * 
	 * @author CAREDDA Giuliano, DUCOBU Alexandre
	 */
	class actionModify implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new WindowInitPawn(new Game(focus.getGrid()), 1);
			close();
		}
	}

}
