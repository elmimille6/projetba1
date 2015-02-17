package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import main.Grid;
import main.GridIA;
import pawn.APawn;

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
	public int posX, posY;
	public Grid grid;
	public APawn focus;
	public int[][] arrow={{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1}};

	/**
	 * 
	 * @param jeuGive
	 */
	@SuppressWarnings("static-access")
	public WinGame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		grid = new Grid(10);
		GridIA gridIA = new GridIA(1);
		grid.placeTeam(gridIA.getGrid(), 1);
		GridIA gridIA2 = new GridIA(2);
		grid.placeTeam(gridIA2.getGrid(), 2);
		grid.showGrid();
		pane = new PaneGame(grid);
		pane.setLayout(new BorderLayout());
		this.setSize(700, 700);
		// grid = jeu.recupGrid();
		// nbrPlayer = jeu.nbrPlayer();
		// pane.recupGrid(grid);
		// pane.recupGame(jeu);
		// this.setResizable(true);
		// typeGame = jeu.recupeType();
		this.setContentPane(pane);
		this.setVisible(true);
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					posX = e.getX();
					posY = e.getY();
					int[] res = getRes(posX, posY);
					int line = res[0];
					int row = res[1];
					focus(line, row);
					pane.recupArrow(arrow);
					repaint();

				}
			}
		});
	}

	public int[] getRes(int posX, int posY) {

		int[] res = { 0, 0 };
		for (int i = 1; i <= grid.getLine(); i++) {
			if (posY > (this.getHeight() / grid.getLine()) * (i - 1)
					&& posY < (this.getHeight() / grid.getLine()) * i) {
				res[0] = i - 1;
			}
		}
		for (int j = 1; j <= grid.getRow(); j++) {
			if (posX > (this.getWidth() / grid.getRow()) * (j - 1)
					&& posX < (this.getWidth() / grid.getRow()) * j) {
				res[1] = j - 1;
			}
		}

		return res;
	}

	public void focus(int line, int row) { //TODO check movePoss
		focus = grid.get(line, row);
		System.out.println(focus.movePoss(grid, line - 1, row));
		arrow[4][0] = line;
		arrow[4][1] = row;
		if (line != grid.getLine()) {// check down move
			System.out.println("D");
			if (focus.movePoss(grid, line + 1, row)) {
				arrow[2][0] = line + 1;
				arrow[2][1] = row;
				System.out.println("ok");
			} else {
				arrow[2][0] = -1;
				arrow[2][1] = -1;
			}
		} else {
			arrow[2][0] = -1;
			arrow[2][1] = -1;
		}
		if (row != grid.getRow()) {// check right move
			System.out.println("R");
			if (focus.movePoss(grid, line, row + 1)) {
				arrow[1][0] = line;
				arrow[1][1] = row + 1;
				System.out.println("ok");
			} else {
				arrow[1][0] = -1;
				arrow[1][1] = -1;
			}
		} else {
			arrow[1][0] = -1;
			arrow[1][1] = -1;
		}
		if (line != 0) {// check up move
			System.out.println("U");
			if (focus.movePoss(grid, line - 1, row)) {
				arrow[0][0] = line + 1;
				arrow[0][1] = row;
				System.out.println("ok");
			} else {
				arrow[0][0] = -1;
				arrow[0][1] = -1;
			}
		} else {
			arrow[0][0] = -1;
			arrow[0][1] = -1;
		}
		if (row != 0) {// check left move
			System.out.println("L");
			if (focus.movePoss(grid, line, row - 1)) {
				arrow[3][0] = line;
				arrow[3][1] = row - 1;
				System.out.println("ok");
			} else {
				arrow[3][0] = -1;
				arrow[3][1] = -1;
			}
		} else {
			arrow[0][0] = -1;
			arrow[0][1] = -1;
		}
		for (int i =0;i<5;i++){
			for(int j = 0;j<2;j++){
				System.out.println(arrow[i][j]);
			}
		}
	}
}
