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

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

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

	public final String[] resultName = { "Rouge", "Bleu" };
	public boolean playGame = true;
	public IA ia;
	public Client client;
	public int Oplayer;

	/**
	 * 
	 */
	public WindowGame() {

	}

	public WindowGame(Game ngame, Client client, int Oplayer) {
		this(ngame);
		this.game.setGameN(1);
		this.client = client;
		this.Oplayer = Oplayer;
		System.out.println("OPLAYER=" + Oplayer);
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof Game) {
					if (game.getGameN() == 1) {
						System.out.println("received game client");
						game = (Game) object;
						pane.recupGame(game);
						
						repaint();
					}
				}
			}
		});
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pane.setView(Oplayer);
		pane.recupGame(game);
		repaint();
	}

	/**
	 * 
	 * @param ngame
	 */
	public WindowGame(Game ngame) {
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.game = ngame;
//		System.out.println(game.level + " LEVEL WindowGame");
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
				if (e.getButton() == MouseEvent.BUTTON1
						&& game.getPlayer() == 3) {
					pane.setView(Oplayer);
					clickOnline();
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
//					System.out.println(game.getPlayer());
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
							game.save();
							focus = null;
							repaint();
							paneRed.upGame(game);
							paneBlue.upGame(game);

							int result = game.win();
							// System.out
							// .println("Result = " + result);
							if (result != 0) {
								pane.setView(0);
								playGame = false;
								repaint();

								jopWin = new JOptionPane();
								jopWin.showMessageDialog(null, "Le joueur "
										+ resultName[result - 1] + " gagne !",
										"Resultat",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								pane.setView(3);
								repaint();

								jop = new JOptionPane();
								jop.showMessageDialog(
										null,
										"C'est votre tour, joueur "
												+ resultName[((game.getTurn() + 1) % 2)]
												+ " !", "Fin du tour",
										JOptionPane.INFORMATION_MESSAGE);
								pane.setView((((game.getTurn() + 1) % 2) + 1));
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
					pane.setView(0);
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
									pane.setView(0);
									playGame = false;
									repaint();

									jopWin = new JOptionPane();
									jopWin.showMessageDialog(null, "Le joueur "
											+ resultName[result - 1]
											+ " gagne !", "Resultat",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
									}
									int[][] next = ia.getNext(game);
									APawn currentPawn = game.getPawn(
											next[0][0], next[0][1]);
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
									currentPawn.move(game, next[1][0],
											next[1][1]);
									game.addTurn();
									game.save();
									repaint();
									paneRed.upGame(game);
									paneBlue.upGame(game);
									result = game.win();
									// System.out
									// .println("Result = " + result);
									if (result != 0) {
										pane.setView(0);
										playGame = false;
										repaint();

										jopWin = new JOptionPane();
										jopWin.showMessageDialog(
												null,
												"Le joueur "
														+ resultName[result - 1]
														+ " gagne !",
												"Resultat",
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
					System.out.println("KNOW");
					game.showKnow(0);
					System.out.println("MOVED");
					game.showMoved(0);
				}
			}).start();
		}

		private void clickOnline() {
			new Thread(new Runnable() {
				@SuppressWarnings("static-access")
				public void run() {
					pane.recupGame(game);
					repaint();
					if (game.getNextTeam() == Oplayer) {
						System.out.println("Oplayer true");
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

								client.sendTCP(game);
								System.out.println("send client game");
								int result = game.win();
								// System.out
								// .println("Result = " + result);
								if (result != 0) {
									pane.setView(0);
									playGame = false;
									repaint();

									jopWin = new JOptionPane();
									jopWin.showMessageDialog(null, "Le joueur "
											+ resultName[result - 1]
											+ " gagne !", "Resultat",
											JOptionPane.INFORMATION_MESSAGE);
									client.close();
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
//					System.out.println("KNOW");
//					game.showKnow(0);
//					System.out.println("MOVED");
//					game.showMoved(0);
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
