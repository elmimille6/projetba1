package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Dic;
import main.Game;
import main.AI;
import pawn.APawn;
import pawn.Bomb;
import pawn.Flag;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

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
	public AI ia;
	public Client client;
	public int Oplayer;

	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu1 = new JMenu("Triche");
	private JCheckBoxMenuItem showGrid = new JCheckBoxMenuItem(
			"Montrer toute la grille");
	private JCheckBoxMenuItem showPawn = new JCheckBoxMenuItem(
			"Montrer les pions connus");

	/**
	 * Main constructor of the class.
	 */
	public WindowGame() {

	}

	/**
	 * Constructor of the class.
	 * 
	 * @param ngame
	 *            The 'game' object of the current game.
	 */
	public WindowGame(Game ngame) {

		this.game = ngame;
		startTeam = game.getStartTeam();
		paneRed = new PaneGamePawn(startTeam, game, 1);
		paneBlue = new PaneGamePawn(startTeam, game, 2);
		pane = new PaneGame(game);
		pane.setView(game.getNextTeam());
		// When we use the last game, the current team is viewable.
		pane.setLayout(new BorderLayout());
		this.add(paneRed, BorderLayout.WEST);
		this.add(paneBlue, BorderLayout.EAST);
		this.setSize(900, 700);
		this.setResizable(true);
		this.setTitle("Game");
		this.setLocationRelativeTo(null); // The window is centered.
		this.add(pane, BorderLayout.CENTER);

		menu1.add(showPawn);

		if (game.getPlayer() != 3) {
			menu1.add(showGrid);
		}
		menuBar.add(menu1);
		this.setJMenuBar(menuBar);

		showPawn.addActionListener(new PawnListener());
		showGrid.addActionListener(new GridListener());

		this.setVisible(true);
		if (game.getPlayer() == 1) {
			ia = new AI(game.getLevel());
		}
		pane.addMouseListener(new MouseGame());
	}

	/**
	 * Constructor of the class when the game is online.
	 * 
	 * @param ngame
	 *            The 'game' object of the current game.
	 * 
	 * @param client
	 *            The client object used to communicate with the server.
	 * 
	 * @param Oplayer
	 *            The number of this player: 1 or 2.
	 */
	public WindowGame(Game ngame, Client client, int Oplayer) {
		this(ngame);
		this.game.setGameN(1);
		this.client = client;
		this.Oplayer = Oplayer;
		client.addListener(new Listener() {
			@SuppressWarnings("static-access")
			public void received(Connection connection, Object object) {
				if (object instanceof Game) {
					if (game.getGameN() == 1) {
						game = (Game) object;
						pane.recupGame(game);

						repaint();
						int result = game.win();
						if (result != 0) {
							pane.setView(0);
							playGame = false;
							repaint();

							jopWin = new JOptionPane();
							jopWin.showMessageDialog(null, "Le joueur "
									+ resultName[result - 1] + " gagne !",
									"Resultat", JOptionPane.INFORMATION_MESSAGE);
							clientClose();
						}
					}

				}
				if (object instanceof int[]) {
					int[] res = (int[]) object;

					APawn pawn = game.getPawn(res[0], res[1]);
					pawn.setShow(!pawn.getShow());
					pane.recupGame(game);
					pane.repaint();
				}
			}
		});
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pane.setView(Oplayer);
		pane.recupGame(game);

		repaint();
	}

	/**
	 * Closes the widow.
	 */
	public void clientClose() {
		client.close();
	}

	/**
	 * Action listener to display (or hide) the known pawns.
	 * 
	 * @author CAREDDA Giuliano, DUCOBU Alexandre
	 */
	class PawnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (showPawn.isSelected()) {
				pane.setShowKnow(true);
				repaint();
			} else {
				pane.setShowKnow(false);
				repaint();
			}
		}
	}

	/**
	 * Action listener to display (or hide) the entire grid.
	 * 
	 * @author CAREDDA Giuliano, DUCOBU Alexandre
	 */
	class GridListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (showGrid.isSelected()) {
				pane.setView(0);
				repaint();
			} else {
				if (game.getPlayer() == 2) {
					pane.setView(game.getNextTeam());
				} else if (game.getPlayer() == 1) {
					pane.setView(1);
				}

				repaint();
			}
		}
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

	/**
	 * This class redefines the right click event.
	 * 
	 * @author CAREDDA Giuliano, DUCOBU Alexandre
	 */
	class MouseGame implements MouseListener {
		int posX, posY;

		/**
		 * Redefines the right click event:<br/>
		 * based the game mode, the known pawns can be displayed (hidden).
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3 && game.getPlayer() == 2) {
				pane.setShowKnow(false);
				repaint();
			}
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

		/**
		 * This method contains the 'game' for the 2 players mode.
		 */
		private void click2player() {
			new Thread(new Runnable() {

				/**
				 * Runs the game.
				 */
				@SuppressWarnings("static-access")
				public void run() {

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
							game.save();
							focus = null;
							repaint();
							paneRed.upGame(game);
							paneBlue.upGame(game);

							int result = game.win();
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

		/**
		 * This method contains the 'game' for the player versus AI mode.
		 */
		private void click1player() {
			new Thread(new Runnable() {

				/**
				 * Runs the game.
				 */
				@SuppressWarnings("static-access")
				public void run() {
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
				}
			}).start();
		}

		/**
		 * This method contains the 'game' for the online mode.
		 */
		private void clickOnline() {
			new Thread(new Runnable() {

				/**
				 * Runs the game.
				 */
				@SuppressWarnings("static-access")
				public void run() {
					pane.recupGame(game);
					repaint();
					if (game.getNextTeam() == Oplayer) {
						int[] res = getRes(game, pane, posX, posY);
						int line = res[0];
						int row = res[1];
						APawn pawn = game.getPawn(line, row);
						if (focus != null) {
							if (focus.movePoss(game, line, row)) {
								if (game.getPawn(line, row) != null) {
									game.getPawn(line, row).setShow(true);
									int[] att = { focus.posX, focus.posY };
									client.sendTCP(att);
									pane.recupArrow(arrowN);
									repaint();
									try {
										Thread.sleep(2000);
									} catch (InterruptedException e) {
									}
									client.sendTCP(att);
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
								int result = game.win();
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
				}
			}).start();
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3 && game.getPlayer() == 2) {
				pane.setShowKnow(true);
				repaint();
			}

		}

	}

}
