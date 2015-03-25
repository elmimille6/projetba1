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
import util.Dic;
import pawn.*;

/**
 * This class creates the window of the game.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class WindowGame extends JFrame {

	private static final long serialVersionUID = 1L;
	public PaneGame pane;
	public PaneGamePawn paneRed, paneBlue;
	public int posX, posY;
	public Game grid;
	public APawn focus;
	public APawn Flag = new Flag(1), Bomb = new Bomb(1);
	public int[] arrow = { -1, -1, -1, -1, -1, -1 };
	public final int[] arrowN = { -1, -1, -1, -1, -1, -1 };
	public boolean att = false;
	JOptionPane jop, jopWin;
	Dic startTeam;

	public final String[] resultName = { "Red", "Blue" };
	public boolean playGame = true;

	public WindowGame() {

	}

	/**
	 * 
	 */
	@SuppressWarnings("static-access")
	public WindowGame(Game ngrid) {
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.grid = ngrid;
		startTeam = grid.getStartTeam();
		paneRed = new PaneGamePawn(startTeam, grid, 1);
		paneBlue = new PaneGamePawn(startTeam, grid, 2);
		pane = new PaneGame(grid);
		pane.setLayout(new BorderLayout());
		// paneRed.fixSize(this.getWidth()/12, this.getHeight());
		this.add(paneRed, BorderLayout.WEST);
		this.add(paneBlue, BorderLayout.EAST);
		this.setSize(900, 700);
		this.setResizable(true);
		this.setTitle("Game");
		this.setLocationRelativeTo(null); // Fenetre centree
		// this.setContentPane(pane);
		this.add(pane, BorderLayout.CENTER);
		this.setVisible(true);
		pane.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (playGame) {
					if (e.getButton() == MouseEvent.BUTTON1
							&& grid.getPlayer() == 2) {
						posX = e.getX();
						posY = e.getY();
						new Thread(new Runnable() {
							public void run() {

								int[] res = getRes(grid, pane, posX, posY);
								int line = res[0];
								int row = res[1];
//								grid.showGrid();
								APawn pawn = grid.getPawn(line, row);
//								System.out.println("pawn= " + pawn);
//								System.out.println("focus= " + focus);
								if (focus != null) {
									if (focus.movePoss(grid, line, row)) {
										if (grid.getPawn(line, row) != null) {
											grid.getPawn(line, row).setShow(
													true);
											pane.recupArrow(arrowN);
											repaint();
											try {
												Thread.sleep(1000);
											} catch (InterruptedException e) {
											}
											grid.getPawn(line, row).setShow(
													false);
										}
										grid = focus.move(grid, line, row);
										att = true;
										grid.addTurn();
										pane.recupArrow(arrowN);
										focus = null;
										repaint();
										paneRed.upGame(grid);
										paneBlue.upGame(grid);

										int result = grid.win();
//										System.out
//												.println("Result = " + result);
										if (result != 0) {
											grid.setView(0);
											playGame = false;
											repaint();
											jopWin = new JOptionPane();
											jopWin.showMessageDialog(
													null,
													"The "
															+ resultName[result - 1]
															+ " player wins !",
													"Result",
													JOptionPane.INFORMATION_MESSAGE);
										} else {
											grid.setView(3);
											repaint();

											jop = new JOptionPane();
											jop.showMessageDialog(
													null,
													"It's your turn, "
															+ resultName[((grid
																	.getTurn() + 1) % 2)]
															+ " player !",
													"Turn finished",
													JOptionPane.INFORMATION_MESSAGE);
											grid.setView((((grid.getTurn() + 1) % 2) + 1));
											repaint();
											paneRed.upGame(grid);
											paneBlue.upGame(grid);

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
						}).start();
					}
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
}
