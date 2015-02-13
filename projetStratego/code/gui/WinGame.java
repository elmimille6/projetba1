package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import main.Grid;
import main.GridIA;

/**
 * 
 * @author giuliano
 * 
 */
public class WinGame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PaneGame pane;
	public int typeGame, nbrPlayer;
	public int[][] grid;
	private Thread t;


	/**
	 * 
	 * @param jeuGive
	 */
	@SuppressWarnings("static-access")
	public WinGame() {
		Grid test = new Grid(10);
		GridIA grid = new GridIA(1);
		test.placeTeam(grid.getGrid(), 1);
		GridIA grid2 = new GridIA(2);
		test.placeTeam(grid2.getGrid(), 2);
		test.showGrid();
		pane = new PaneGame(test);
		pane.setLayout(new BorderLayout());
		this.setSize(700, 700);
//		grid = jeu.recupGrid();
//		nbrPlayer = jeu.nbrPlayer();
//		pane.recupGrid(grid);
//		pane.recupGame(jeu);
//		this.setResizable(true);
//		typeGame = jeu.recupeType();
		this.setContentPane(pane);
		this.setVisible(true);

	}
}
