package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import main.Grid;
import main.GridIA;
import pawn.APawn;
import pawn.Marshal;

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
	public int[] arrow={-1,-1,-1,-1,-1,-1};
	public final int[] arrowN={-1,-1,-1,-1,-1,-1};
	public boolean att=false;

	/**
	 * 
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
		this.setResizable(true);
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
					APawn pawn = grid.get(line, row);
					if (focus != null) {
						if (focus.movePoss(grid, line, row)) {
							grid = focus.move(grid, line, row);
							focus = null;
							pane.recupArrow(arrowN);
							att = true;
						}
					}
					if (pawn != null && !att) {
						arrow = pawn.focus(grid);
						pane.recupArrow(arrow);
						repaint();
					} else {
						pane.recupArrow(arrowN);
						focus = null;
						repaint();
					}
				}
				att = false;
				repaint();
			}
		});
	}

	/**
	 * transform the coord of the cursor into coord of the grid
	 * @param posX x coord of the cursor
	 * @param posY y coord of the cursor
	 * @return an array with x coord and y coord in the grid
	 */
	public int[] getRes(int posX, int posY) {

		int[] res = { 0, 0 };
		for (int i = 1; i <= grid.getLine()+1; i++) {
			if (posY > (this.getHeight() / (grid.getLine()+1)) * (i - 1)
					&& posY < (this.getHeight() / (grid.getLine()+1)) * i) {
				res[0] = i - 1;
			}
		}
		for (int j = 1; j <= grid.getRow()+1; j++) {
			if (posX > (this.getWidth() / (grid.getRow()+1)) * (j - 1)
					&& posX < (this.getWidth() /( grid.getRow()+1)) * j) {
				res[1] = j - 1;
			}
		}

		return res;
	}

	
}
