package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Game;
import main.IA;
import pawn.APawn;
import pawn.Bomb;
import pawn.Flag;
import util.Dic;

//import pawn.*;

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
	public Game game;
	public APawn focus;
	public APawn Flag = new Flag(1), Bomb = new Bomb(1);
	public int[] arrow = { -1, -1, -1, -1, -1, -1 };
	public final int[] arrowN = { -1, -1, -1, -1, -1, -1 };
	public boolean att = false;
	JOptionPane jop, jopWin;
	Dic startTeam;

	public final String[] resultName = { "Red", "Blue" };
	public boolean playGame = true;
	public IA ia;

	/**
	 * 
	 */
	public WindowGame() {

	}

	/**
	 * 
	 * @param ngame
	 */
	@SuppressWarnings("static-access")
	public WindowGame(Game ngame) {
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.game = ngame;
		System.out.println(game.level + " LEVEL WindowGame");
		startTeam = game.getStartTeam();
		paneRed = new PaneGamePawn(startTeam, game, 1);
		paneBlue = new PaneGamePawn(startTeam, game, 2);
		pane = new PaneGame(game);
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
		if (game.getPlayer() == 1) {
			ia = new IA(game.getLevel());
		}
		pane.addMouseListener(new MouseGame());
	}

	/**
	 * Transforms the coordinates of the cursor into coordinates of the game.
	 * 
	 * @param game
	 *            The chosen game.
	 * 
	 * @param pane
	 *            The panel containing the game.
	 * 
	 * @param posX
	 *            The abscissa of the cursor.
	 * 
	 * @param posY
	 *            The ordinate of the cursor.
	 * 
	 * @return An array with the abscissa and the ordinate in the game.
	 */
	public int[] getRes(Game game, JPanel pane, int posX, int posY) {

		int[] res = { 0, 0 };
		res[1] = (posX - (posX % (pane.getWidth() / (game.getRow() + 1))))
				/ (pane.getWidth() / (game.getRow() + 1));
		res[0] = (posY - (posY % (pane.getHeight() / (game.getLine() + 1))))
				/ (pane.getHeight() / (game.getLine() + 1));
		return res;

	}

	class MouseGame implements MouseListener {
		int posX, posY;

		@Override
		public void mouseReleased(MouseEvent e) {
			if (playGame) {
				posX = e.getX();
				posY = e.getY();
				if (e.getButton() == MouseEvent.BUTTON1
						&& game.getPlayer() == 2) {
					click2player();

				}
				if (e.getButton() == MouseEvent.BUTTON1
						&& game.getPlayer() == 1) {
					click1player();
				}
			}
		}

		private void click2player() {
			new Thread(new Runnable() {
				@SuppressWarnings("static-access")
				public void run() {

					int[] res = getRes(game, pane, posX, posY);
					int line = res[0];
					int row = res[1];
					// game.showGrid();
					APawn pawn = game.getPawn(line, row);
					System.out.println(game.getPlayer());
					// System.out.println("pawn= " + pawn);
					// System.out.println("focus= " + focus);
					if (focus != null) {
						if (focus.movePoss(game, line, row)) {
							if (game.getPawn(line, row) != null) {
								game.getPawn(line, row).setShow(true);
								pane.recupArrow(arrowN);
								repaint();
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {
								}
								game.getPawn(line, row).setShow(false);
							}
							game = focus.move(game, line, row);
							att = true;
							game.addTurn();
							pane.recupArrow(arrowN);
							focus = null;
							repaint();
							game.save();
							paneRed.upGame(game);
							paneBlue.upGame(game);

							int result = game.win();
							// System.out
							// .println("Result = " + result);
							if (result != 0) {
								game.setView(0);
								playGame = false;
								repaint();

								jopWin = new JOptionPane();
								jopWin.showMessageDialog(null, "The "
										+ resultName[result - 1]
										+ " player wins !", "Result",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								game.setView(3);
								repaint();

								jop = new JOptionPane();
								jop.showMessageDialog(
										null,
										"It's your turn, "
												+ resultName[((game.getTurn() + 1) % 2)]
												+ " player !", "Turn finished",
										JOptionPane.INFORMATION_MESSAGE);
								game.setView((((game.getTurn() + 1) % 2) + 1));
								repaint();

								paneRed.upGame(game);
								paneBlue.upGame(game);

							}
						}
					}
					if (pawn != null) {
						if (pawn.getTeam() == ((game.getTurn() + 1) % 2) + 1) {

							if (pawn != null && !att) {
								focus = pawn;
								arrow = pawn.focus(game);
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

		private void click1player() {
			new Thread(new Runnable() {
				@SuppressWarnings("static-access")
				public void run() {

					System.out.println(game.win() + " : win");
					if ((((game.getTurn() + 1) % 2) + 1) == 1) {
						int[] res = getRes(game, pane, posX, posY);
						int line = res[0];
						int row = res[1];
						APawn pawn = game.getPawn(line, row);
						if (focus != null) {
							if (focus.movePoss(game, line, row)) {
								if (game.getPawn(line, row) != null) {
									game.getPawn(line, row).setShow(true);
									pane.recupArrow(arrowN);
									repaint();
									try {
										Thread.sleep(2000);
									} catch (InterruptedException e) {
									}
									game.getPawn(line, row).setShow(false);
								}
								game = focus.move(game, line, row);
								att = true;
								game.addTurn();
								pane.recupArrow(arrowN);
								focus = null;
								repaint();
								paneRed.upGame(game);
								paneBlue.upGame(game);

								int result = game.win();
								// System.out
								// .println("Result = " + result);
								if (result != 0) {
									game.setView(0);
									playGame = false;
									repaint();

									jopWin = new JOptionPane();
									jopWin.showMessageDialog(null, "The "
											+ resultName[result - 1]
											+ " player wins !", "Result",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
									}
									int[][] next = ia.getNext(game);
									APawn currentPawn = game.getPawn(next[0][0],
											next[0][1]);
									if (game.getPawn(next[1][0], next[1][1]) != null) {
										currentPawn.setShow(true);
										repaint();
										try {
											Thread.sleep(3000);
										} catch (InterruptedException e) {
										}
										currentPawn.setShow(false);
										repaint();
									}
									currentPawn.move(game, next[1][0], next[1][1]);
									game.addTurn();
									repaint();
									paneRed.upGame(game);
									paneBlue.upGame(game);
									result = game.win();
									// System.out
									// .println("Result = " + result);
									if (result != 0) {
										game.setView(0);
										playGame = false;
										repaint();

										jopWin = new JOptionPane();
										jopWin.showMessageDialog(null, "The "
												+ resultName[result - 1]
												+ " player wins !", "Result",
												JOptionPane.INFORMATION_MESSAGE);
									}
								}
							}
						}
						if (pawn != null) {
							if (pawn.getTeam() == ((game.getTurn() + 1) % 2) + 1) {

								if (pawn != null && !att) {
									focus = pawn;
									arrow = pawn.focus(game);
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
			}).start();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}