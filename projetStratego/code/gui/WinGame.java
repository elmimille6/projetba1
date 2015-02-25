package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.Game;
import pawn.APawn;

/**
 * 
 * @author giuliano
 * 
 */
public class WinGame extends JFrame {

	private static final long serialVersionUID = 1L;
	public PaneGame pane;
	public int posX, posY;
	public Game grid;
	public APawn focus;
	public int[] arrow = { -1, -1, -1, -1, -1, -1 };
	public final int[] arrowN = { -1, -1, -1, -1, -1, -1 };
	public boolean att = false;
	JOptionPane jop;

	/**
	 * 
	 */
	@SuppressWarnings("static-access")
	public WinGame(Game ngrid) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.grid = ngrid;

		pane = new PaneGame(grid);
		pane.setLayout(new BorderLayout());
		this.setSize(700, 700);
		this.setResizable(true);
		this.setContentPane(pane);
		this.setVisible(true);
		pane.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1
						&& grid.getPlayer() == 2) {
					posX = e.getX();
					posY = e.getY();
					int[] res = getRes(posX, posY);
					int line = res[0];
					int row = res[1];
					grid.showGrid();
					APawn pawn = grid.get(line, row);
					System.out.println("pawn= "+pawn);
					System.out.println("focus= "+focus);
					if (focus != null) {
						if (focus.movePoss(grid, line, row)) {
							grid = focus.move(grid, line, row);
							att = true;
							grid.addTurn();
							grid.setView(3);
							pane.recupArrow(arrowN);
							focus = null;
							repaint();
							jop = new JOptionPane();
							jop.showMessageDialog(null,
									"Joueur "
											+ (((grid.getTurn() + 1) % 2) + 1)
											+ " à vous de jouer !",
									"Tour terminé",
									JOptionPane.INFORMATION_MESSAGE);
							grid.setView((((grid.getTurn() + 1) % 2) + 1));
							repaint();
						}
					}
					if (pawn != null) {
						if (pawn.getTeam() == ((grid.getTurn() + 1) % 2) + 1) {

							if (pawn != null && !att) {
								focus = pawn;
								arrow = pawn.focus(grid);
								pane.recupArrow(arrow);
								repaint();
							} else {
								pane.recupArrow(arrowN);
								focus = null;
								repaint();
							}
							att = false;
							repaint();
						}
					}
					att = false;
				}
			}
		});
	}

	/**
	 * Transforms the coordinates of the cursor into coordinates of the grid.
	 * 
	 * @param posX
	 *            The abscissa of the cursor.
	 * 
	 * @param posY
	 *            The ordinate of the cursor.
	 * 
	 * @return An array with the abscissa and the ordinate in the grid.
	 */
	public int[] getRes(int posX, int posY) {

		int[] res = { 0, 0 };
		res[1] = (posX - (posX % (pane.getWidth() / (grid.getLine() + 1))))
				/ (pane.getWidth() / (grid.getLine() + 1));
		res[0] = (posY - (posY % (pane.getHeight() / (grid.getRow() + 1))))
				/ (pane.getHeight() / (grid.getRow() + 1));
		return res;

	}

}
