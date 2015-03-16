package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;
import pawn.Bomb;
import pawn.Flag;

import pawn.*;

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
	public APawn Flag = new Flag(1), Bomb = new Bomb(1);
	public int[] arrow = { -1, -1, -1, -1, -1, -1 };
	public final int[] arrowN = { -1, -1, -1, -1, -1, -1 };
	public boolean att = false;
	JOptionPane jop, jopWin;

	public final String[] resultName = { "Draw", "Red", "Blue" };

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
		this.setTitle("Game");
		this.setLocationRelativeTo(null); // Fenetre centree
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
							int result = win();
							System.out.println("Result = " + result);
							if (result != 0) {
								grid.setView(0);
								repaint();
								jopWin = new JOptionPane();
								jopWin.showMessageDialog(null,
										"The " + resultName[result]
												+ " player wins !", "Result",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								grid.setView(3);
								repaint();

								jop = new JOptionPane();
								jop.showMessageDialog(
										null,
										"It's your turn, "
												+ resultName[((grid.getTurn() + 1) % 2) + 1]
												+ " player !", "Turn finished",
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

	/**
	 * Checks if the game is over and if so, return the number of the player who
	 * win.
	 * 
	 * @return 0 if the game isn't over <br/>
	 *         1 if player 1 win <br/>
	 *         2 if player 2 win.
	 */
	public int win() {
		boolean canPlay = true;
		boolean flag1 = false;
		boolean flag2 = false;
		int winner = 0, test = 0;

		for (int j = 0; j < grid.getLine() + 1; j++) {
			for (int i = 0; i < grid.getRow() + 1; i++) {
				APawn pawn = grid.getPawn(i, j);

				if (pawn != null) {
					if (pawn.getTeam() == 1) {
						test = canMove(1);
						if (test != 0) {
							canPlay = false;
							winner = test;
						} else if (pawn.getClass() == Flag.getClass()) {
							System.out.println("Flag 1");
							flag1 = true;
						}
					} else if (pawn.getTeam() == 2) {
						test = canMove(1);
						if (test != 0) {
							canPlay = false;
							winner = test;
						} else if (pawn.getClass() == Flag.getClass()) {
							System.out.println("Flag 2");
							flag2 = true;
						}
					}
				}
			}
		}

		if (!canPlay) {
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

	/**
	 * Returns the team of the winner or 0 if the game isn't over.
	 * 
	 * @param team
	 *            The team of the current pawn: - 1 for Red <br />
	 *            - 2 for Blue.
	 * 
	 * @return The team of the winner or 0 if there's none.
	 */
	public int canMove(int team) {
		int nbPawnofTeam = 0, nbPawnBloked = 0, nbMoves;
		for (int j = 0; j < grid.getLine() + 1; j++) {
			for (int i = 0; i < grid.getRow() + 1; i++) {
				int[] arrayFocus = null;
				nbMoves = 0;
				APawn currentPawn = grid.getPawn(i, j);

				if (currentPawn != null && currentPawn.getTeam() == team) {
					
					nbPawnofTeam++;
					
					arrayFocus = currentPawn.focus(grid);
					for (int elem = 0; elem < 4; elem++) {
						if (arrayFocus[elem] == -1) {
							nbMoves++;
						}
					}
					if (nbMoves == 4) {
						nbPawnBloked++;
					}
				}
			}
		}
		if (nbPawnofTeam == nbPawnBloked) {
			return (team % 2) + 1;
		}
		return 0;
	}
}
