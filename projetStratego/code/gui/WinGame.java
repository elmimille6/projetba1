package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;
import pawn.Flag;

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
	JOptionPane jop, jopWin;

	/**
	 * Main constructor of the class.
	 */
	public WinGame() {

	}

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
					int[] res = getRes(grid, pane, posX, posY);
					int line = res[0];
					int row = res[1];
					grid.showGrid();
					APawn pawn = grid.getPawn(line, row);
					System.out.println("pawn= " + pawn);
					System.out.println("focus= " + focus);
					if (focus != null) {
						if (focus.movePoss(grid, line, row)) {
							grid = focus.move(grid, line, row);
							att = true;
							grid.addTurn();
							pane.recupArrow(arrowN);
							focus = null;
							repaint();
							System.out.println("Result = " + win());
							if (win() != 0) {
								grid.setView(0);
								repaint();
								jopWin = new JOptionPane();
								jopWin.showMessageDialog(null, "Le joueur "
										+ win() + " gagne !", "Resultat",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								grid.setView(3);

								jop = new JOptionPane();
								jop.showMessageDialog(
										null,
										"Joueur "
												+ (((grid.getTurn() + 1) % 2) + 1)
												+ " a vous de jouer !",
										"Tour termine",
										JOptionPane.INFORMATION_MESSAGE);
								grid.setView((((grid.getTurn() + 1) % 2) + 1));
								repaint();
							}
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
	public int[] getRes(Game grid, JPanel pane, int posX, int posY) {

		int[] res = { 0, 0 };
		res[1] = (posX - (posX % (pane.getWidth() / (grid.getRow() + 1))))
				/ (pane.getWidth() / (grid.getRow() + 1));
		res[0] = (posY - (posY % (pane.getHeight() / (grid.getLine() + 1))))
				/ (pane.getHeight() / (grid.getLine() + 1));
		return res;

	}

	public int win() {
		boolean canPlay = true;
		boolean flag1 = false;
		boolean flag2 = false;
		int winner = 0;
		APawn Flag = new Flag(1);
		for (int j = 0; j < grid.getLine() + 1; j++) {
			for (int i = 0; i < grid.getRow() + 1; i++) {
				APawn pawn = grid.getPawn(i, j);

				if (pawn != null) {
					if (pawn.canMove(grid, pawn) != 0) {
						winner = pawn.canMove(grid, pawn);
						canPlay = false;
					}
					//System.out.println("winnerddd = "+ pawn.canMove(grid, pawn));
					if (pawn.getClass() == Flag.getClass()
							&& pawn.getTeam() == 1) {
						System.out.println("Flag 1");
						flag1 = true;
					}
					if (pawn.getClass() == Flag.getClass()
							&& pawn.getTeam() == 2) {
						System.out.println("Flag 2");
						flag2 = true;
					}
				}
			}
		}

		if (!canPlay) {
			//return ((turn) % 2) + 1;
			System.out.println("winner = " + winner);
			return winner;
		}

		if (!flag1) {
			return 2;
		}
		if (!flag2) {
			return 1;
		}
		return 0;
	}

}
