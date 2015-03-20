package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;

import main.Game;
import main.GridIA;
//import main.GridPawn;
import pawn.*;

/**
 * This class creates the window for the initialization of the grid by the
 * player.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class WindowInitPawn extends WindowGame {

	private static final long serialVersionUID = 1L;
	public Game grid1, grid2;
	public PaneInitPawn pane1, pane2;
	public int team = 1;
	public Vector<APawn> pawns /* = APawn.createTeam(1, 40) */;
	public APawn spyInit, scoutInit, minerInit, sergeantInit, lieutenantInit,
			captainInit, majorInit, colonelInit, generalInit, marshalInit,
			bombInit, flagInit, noPawn;
	public int nbPawn, posX, posY;
	public int x = 0, y = 0, nbPawns = 40;
	public APawn currentPawn, pawnShow;
	public JOptionPane jop1, jop2;
	public String link;

	/**
	 * This method initializes the window where the player creates his grid.
	 * 
	 * @param nbPlayer
	 *            The number of player: 1 or 2.
	 * 
	 * @param nbPawns
	 *            The number of pawns: 40 for the normal game, 10 for the
	 *            'duel'.
	 * 
	 * @param team
	 *            The team of pawns: 1 for the red <br/>
	 *            2 for the blue.
	 */
	@SuppressWarnings("static-access")
	public WindowInitPawn(int nbPlayer, final int nbPawns, final int team) {
		this.setTitle("Initialization of the grid");
		this.setSize(700, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.nbPawns = nbPawns;
		this.team = team;
		initialize();

		int xSize = ((int) this.getWidth());
		int ySize = ((int) this.getHeight());
		this.setSize(xSize, ySize);

		int northHeight = (int) (Math.round(ySize * 0.55)), southHeight = (int) (Math
				.round(ySize * 0.35));

		if (nbPawns == 40) {
			grid1 = new Game(10, 4, 0);
			grid2 = new Game(6, 2, 0);

			grid2.set(0, 0, spyInit);
			grid2.set(0, 1, scoutInit);
			grid2.set(0, 2, minerInit);
			grid2.set(0, 3, sergeantInit);
			grid2.set(0, 4, lieutenantInit);
			grid2.set(0, 5, captainInit);
			grid2.set(1, 0, majorInit);
			grid2.set(1, 1, colonelInit);
			grid2.set(1, 2, generalInit);
			grid2.set(1, 3, marshalInit);
			grid2.set(1, 4, bombInit);
			grid2.set(1, 5, flagInit);

		} else if (nbPawns == 10) {
			// pawns = APawn.createTeam(1, 10);
			grid1 = new Game(8, 3, 0);
			grid2 = new Game(7, 1, 0);

			northHeight = (int) (Math.round(ySize * 0.65));
			southHeight = (int) (Math.round(ySize * 0.25));

			grid2.set(0, 0, spyInit);
			grid2.set(0, 1, scoutInit);
			scoutInit.setNbPawn(2);
			grid2.set(0, 2, minerInit);
			minerInit.setNbPawn(2);
			grid2.set(0, 3, generalInit);
			grid2.set(0, 4, marshalInit);
			grid2.set(0, 5, bombInit);
			bombInit.setNbPawn(2);
			grid2.set(0, 6, flagInit);
		}

		// grid1.showGrid();
		this.setLocationRelativeTo(null);
		// On definit le layout a utiliser sur le content pane
		this.setLayout(new BorderLayout());
		// On ajoute le bouton au content pane de la JFrame

		pane1 = new PaneInitPawn(grid1);
		pane1.setPreferredSize(new Dimension(0, northHeight));

		JPanel Center = new JPanel();
		// Center.setPreferredSize(new Dimension(0, centerHeight));
		JButton play = new JButton("Play");
		Center.add(new JButton("Save")); // Save the grid in the 'Saves' folder
		// Center.add(new JButton("OK")); // Lauches the game with the grid
		Center.add(play);
		Center.add(new JButton("Load")); // Search save in the 'Saves' folder

		final JFrame fen = this;

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!pawns.isEmpty()) {
					// System.out
					// .println("You don't have positioned all your pawns.");
					jop1 = new JOptionPane();
					jop1.showMessageDialog(null,
							"You don't have positioned all your pawns.",
							"Warning", JOptionPane.WARNING_MESSAGE);
				} else if (nbPawns != 10 && !canPlay()) {
					// System.out
					// .println("Make sure you can move at least one pawn.");
					jop2 = new JOptionPane();
					jop2.showMessageDialog(null,
							"Make sure you can move at least one pawn.",
							"Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					Game grid = new Game(10, 1);
					Game gridPlayer = createGrid();
					GridIA gridIA = new GridIA(2);
					grid.placeTeam(gridPlayer.getGrid(), 1);
					grid.placeTeam(gridIA.getGrid(), 2);
					fen.dispose();
					WindowGame fenGame = new WindowGame(grid);
				}
			}
		});

		pane2 = new PaneInitPawn(grid2);
		pane2.setPreferredSize(new Dimension(0, southHeight));

		this.getContentPane().add(pane1, BorderLayout.NORTH);

		this.getContentPane().add(Center, BorderLayout.CENTER);

		this.getContentPane().add(pane2, BorderLayout.SOUTH);

		this.setVisible(true);

		pane2.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					posX = e.getX();
					posY = e.getY();
					int[] res = getRes(grid2, pane2, posX, posY);
					int line = res[0];
					int row = res[1];
					APawn pawn = grid2.getPawn(line, row);
					// System.out.println("pawn = " + pawn);
					currentPawn = pawn;
					chooseSquare(chosenPawn(pawn));
					repaint();
				}
			}

		});

	}

	/**
	 * Initializes the vector and the pawns.
	 */
	public void initialize() {
		pawns = APawn.createTeam(team, nbPawns);
		if (nbPawns == 40) {
			sergeantInit = new Sergeant(team);
			lieutenantInit = new Lieutenant(team);
			captainInit = new Captain(team);
			majorInit = new Major(team);
			colonelInit = new Colonel(team);
		}
		spyInit = new Spy(team);
		scoutInit = new Scout(team);
		minerInit = new Miner(team);
		generalInit = new General(team);
		marshalInit = new Marshal(team);
		bombInit = new Bomb(team);
		flagInit = new Flag(team);
	}

	/**
	 * Verifies if the player can move at least one pawn at the beginning of the
	 * game.
	 * 
	 * @return true if the player can move, <br/>
	 *         false otherwise.
	 */
	public boolean canPlay() {
		int i = 0, j = 0;
		while (i < 10) {
			if (i != 0 && (i % 2 == 0)) {
				i += 2;
			}
			if (grid1.getPawn(0, i).getClass() == bombInit.getClass()) {
				j++;
			}
			i++;
		}
		return j != 6;
	}

	/**
	 * Returns the chosen pawn: this pawn is used for the count of each class of
	 * pawn.
	 * 
	 * @param pawn
	 *            A pawn of the class of the pawnInit expected.
	 * 
	 * @return The pawnInit expected.
	 */
	public APawn chosenPawn(APawn pawn) {
		APawn pawnInit = spyInit;
		if (pawn.getClass() == scoutInit.getClass()) {
			pawnInit = scoutInit;
		} else if (pawn.getClass() == minerInit.getClass()) {
			pawnInit = minerInit;
		} else if (nbPawns == 40 && pawn.getClass() == sergeantInit.getClass()) {
			pawnInit = sergeantInit;
		} else if (nbPawns == 40
				&& pawn.getClass() == lieutenantInit.getClass()) {
			pawnInit = lieutenantInit;
		} else if (nbPawns == 40 && pawn.getClass() == captainInit.getClass()) {
			pawnInit = captainInit;
		} else if (nbPawns == 40 && pawn.getClass() == majorInit.getClass()) {
			pawnInit = majorInit;
		} else if (nbPawns == 40 && pawn.getClass() == colonelInit.getClass()) {
			pawnInit = colonelInit;
		} else if (pawn.getClass() == generalInit.getClass()) {
			pawnInit = generalInit;
		} else if (pawn.getClass() == marshalInit.getClass()) {
			pawnInit = marshalInit;
		} else if (pawn.getClass() == bombInit.getClass()) {
			pawnInit = bombInit;
		} else if (pawn.getClass() == flagInit.getClass()) {
			pawnInit = flagInit;
		}
		return pawnInit;
	}

	/**
	 * Chooses the square where the pawn will be placed.
	 * 
	 * @param pawnInit
	 *            The pawn to place.
	 */
	public void chooseSquare(final APawn pawnInit) {
		pane1.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (pawnInit.getClass() == currentPawn.getClass()) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						posX = e.getX();
						posY = e.getY();
						int[] res = getRes(grid1, pane1, posX, posY);
						int line = res[0];
						int row = res[1];
						if (grid1.getPawn(line, row) != null) {
							System.out.println("You can't play here !");
						} else {
							// System.out.println("line = " + line + " row = " +
							// row);
							placePawn(pawnInit, line, row);
							repaint();
						}
					} else if (e.getButton() == MouseEvent.BUTTON3) {// delete
																		// pawn
						posX = e.getX();
						posY = e.getY();
						int[] res = getRes(grid1, pane1, posX, posY);
						int line = res[0];
						int row = res[1];
						if (grid1.getPawn(line, row) == null) {
							System.out.println("You can't delete here !");
						} else {
							// System.out.println("line = " + line + " row = " +
							// row);
							APawn pawn = grid1.getPawn(line, row);
							deletePawn(chosenPawn(pawn), line, row);
							repaint();
						}
					}
				}
			}
		});
	}

	/**
	 * Places the pawn at the given coordinates.
	 * 
	 * @param pawnInit
	 *            The type of the pawn to place.
	 * 
	 * @param line
	 *            The line of the pawn.
	 * 
	 * @param row
	 *            The row of the pawn.
	 */
	public void placePawn(APawn pawnInit, int line, int row) {
		for (int i = 0; i < pawns.size(); i++) {
			if (pawns.size() == 0) {
				System.out.println("The vector is empty !");
				break;
			}
			if (!pawns.isEmpty() && pawnInit.getNbPawn() > 0
					&& pawns.elementAt(i).getLevel() == pawnInit.getLevel()) {
				grid1.set(line, row, pawns.elementAt(i));
//				System.out.println("Add");
//				System.out.println("line = " + line + " row = " + row);
				// grid1.showGrid();
				pawns.removeElementAt(i);
				nbPawn = pawnInit.getNbPawn();
				pawnInit.setNbPawn(--nbPawn);
				// System.out.println("NbPawnd = " + pawnInit.getNbPawn());
				if (pawnInit.getNbPawn() == 0) {
					if (nbPawns == 40) {
						for (int x = 0; x < 2; x++) {
							for (int y = 0; y < 6; y++) {
								pawnShow = grid2.getPawn(x, y);
								if (pawnShow.getClass() == pawnInit.getClass()) {
									showPawn(x, y, pawnShow, true);
								}
							}
						}
					} else if (nbPawns == 10) {
						for (int y = 0; y < 7; y++) {
							pawnShow = grid2.getPawn(x, y);
							if (pawnShow.getClass() == pawnInit.getClass()) {
								showPawn(0, y, pawnShow, true);
							}
						}
					}
				}
				break;
			}
		}
	}

	/**
	 * Deletes the pawn at the given coordinates.
	 * 
	 * @param pawnInit
	 *            The type of the pawn to delete.
	 * 
	 * @param line
	 *            The line of the pawn.
	 * 
	 * @param row
	 *            The row of the pawn.
	 */
	public void deletePawn(APawn pawn, int line, int row) {
//		System.out.println("Pop");
//		System.out.println("line = " + line + " row = " + row);

		if (pawn.getNbPawn() == 0) {
			if (nbPawns == 40) {
				for (int x = 0; x < 2; x++) {
					for (int y = 0; y < 6; y++) {
						pawnShow = grid2.getPawn(x, y);
						if (pawnShow.getNamePawn() == pawn.getNamePawn()) {
							showPawn(x, y, pawnShow, false);
						}
					}
				}
			} else if (nbPawns == 10) {
				for (int y = 0; y < 7; y++) {
					pawnShow = grid2.getPawn(0, y);
					if (pawnShow.getNamePawn() == pawn.getNamePawn()) {
						showPawn(0, y, pawnShow, false);
					}
				}
			}
		}

		grid1.set(line, row, null);
		// grid1.showGrid();
		pawns.add(newPawn(pawn));
		nbPawn = pawn.getNbPawn();
		pawn.setNbPawn(++nbPawn);
		// System.out.println("NbPawn = " + pawn.getNbPawn());
	}

	/**
	 * This method shows or hides the pawn given in parameter in the grid2.
	 * 
	 * @param x
	 *            The row of the grid.
	 * 
	 * @param y
	 *            The line of the grid.
	 * 
	 * @param pawnShow
	 *            The pawn who will be show or won't be.
	 * 
	 * @param show
	 *            The boolean who says if the pawn is shown or isn't.
	 */
	public void showPawn(int x, int y, APawn pawnShow, boolean show) {
		if (show) {
			grid2.set(x, y, noPawn = new NoPawn(pawnShow.getNamePawn()));
		} else {
			grid2.set(x, y, initPawnIs(pawnShow));
		}
		repaint();
	}

	/**
	 * Returns the initPawn that fits with the one given.
	 * 
	 * @param pawn
	 *            The pawn of the same class of the wanted initPawn.
	 * 
	 * @return The wanted initPawn.
	 */
	public APawn initPawnIs(APawn pawn) {

		if (pawn.getNamePawn() == spyInit.getNamePawn()) {
			return spyInit;
		} else if (pawn.getNamePawn() == scoutInit.getNamePawn()) {
			return scoutInit;
		} else if (pawn.getNamePawn() == minerInit.getNamePawn()) {
			return minerInit;
		} else if (pawn.getNamePawn() == generalInit.getNamePawn()) {
			return generalInit;
		} else if (pawn.getNamePawn() == marshalInit.getNamePawn()) {
			return marshalInit;
		} else if (pawn.getNamePawn() == bombInit.getNamePawn()) {
			return bombInit;
		} else if (pawn.getNamePawn() == flagInit.getNamePawn()) {
			return flagInit;
		} else if (pawn.getNamePawn() == sergeantInit.getNamePawn()) {
			return sergeantInit;
		} else if (pawn.getNamePawn() == lieutenantInit.getNamePawn()) {
			return lieutenantInit;
		} else if (pawn.getNamePawn() == captainInit.getNamePawn()) {
			return captainInit;
		} else if (pawn.getNamePawn() == majorInit.getNamePawn()) {
			return majorInit;
		} else {
			return colonelInit;
		}
	}

	/**
	 * Returns a new instance of the given pawn.
	 * 
	 * @param pawn
	 *            The pawn which we want a new instance.
	 * 
	 * @return The new instance of the pawn.
	 */
	public APawn newPawn(APawn pawn) {
		APawn newPawn;

		if (pawn.getClass() == spyInit.getClass()) {
			newPawn = new Spy(team);
		} else if (pawn.getClass() == scoutInit.getClass()) {
			newPawn = new Scout(team);
		} else if (pawn.getClass() == minerInit.getClass()) {
			newPawn = new Miner(team);
		} else if (pawn.getClass() == generalInit.getClass()) {
			newPawn = new General(team);
		} else if (pawn.getClass() == marshalInit.getClass()) {
			newPawn = new Marshal(team);
		} else if (pawn.getClass() == bombInit.getClass()) {
			newPawn = new Bomb(team);
		} else if (pawn.getClass() == flagInit.getClass()) {
			newPawn = new Flag(team);
		} else if (pawn.getClass() == sergeantInit.getClass()) {
			newPawn = new Sergeant(team);
		} else if (pawn.getClass() == lieutenantInit.getClass()) {
			newPawn = new Lieutenant(team);
		} else if (pawn.getClass() == captainInit.getClass()) {
			newPawn = new Captain(team);
		} else if (pawn.getClass() == majorInit.getClass()) {
			newPawn = new Major(team);
		} else {
			newPawn = new Colonel(team);
		}

		return newPawn;
	}

	/**
	 * 
	 * @return
	 */
	public Game createGrid() {
		return grid1;
	}
}