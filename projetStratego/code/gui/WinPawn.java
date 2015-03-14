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

public class WinPawn extends WinGame {

	private static final long serialVersionUID = 1L;
	public Game grid1, grid2;
	public PanePawn pane1, pane2;
	public Vector<APawn> pawns = APawn.createTeam(1, 40);
	public APawn spyInit = new Spy(1), scoutInit = new Scout(1),
			minerInit = new Miner(1), sergeantInit = new Sergeant(1),
			lieutenantInit = new Lieutenant(1), captainInit = new Captain(1),
			majorInit = new Major(1), colonelInit = new Colonel(1),
			generalInit = new General(1), marshalInit = new Marshal(1),
			bombInit = new Bomb(1), flagInit = new Flag(1);
	public int nbPawn;
	public int x = 0, y = 0;

	public int posX, posY;
	public APawn currentPawn;
	JOptionPane jop1, jop2;

	/**
	 * This method initializes the window where the player creates his grid.
	 * 
	 * @param nbPlayer
	 *            The number of player: 1 or 2.
	 * 
	 * @param nbPawns
	 *            The number of pawns: 40 for the normal game, 10 for the
	 *            'duel'.
	 */
	@SuppressWarnings("static-access")
	public WinPawn(int nbPlayer, final int nbPawns) {
		this.setTitle("Initialisation grille");
		this.setSize(700, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
			pawns = APawn.createTeam(1, 10);
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

		grid1.showGrid();
		this.setLocationRelativeTo(null);
		// On definit le layout a utiliser sur le content pane
		this.setLayout(new BorderLayout());
		// On ajoute le bouton au content pane de la JFrame

		pane1 = new PanePawn(grid1);
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
					WinGame fenGame = new WinGame(grid);
				}
			}
		});

		pane2 = new PanePawn(grid2);
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
		} else if (pawn.getClass() == sergeantInit.getClass()) {
			pawnInit = sergeantInit;
		} else if (pawn.getClass() == lieutenantInit.getClass()) {
			pawnInit = lieutenantInit;
		} else if (pawn.getClass() == captainInit.getClass()) {
			pawnInit = captainInit;
		} else if (pawn.getClass() == majorInit.getClass()) {
			pawnInit = majorInit;
		} else if (pawn.getClass() == colonelInit.getClass()) {
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
					} else if (e.getButton() == MouseEvent.BUTTON3) {
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
			} else if (pawnInit.getNbPawn() == 0) {
				System.out.println("Name = " + pawnInit.getNamePawn());
				System.out.println("NbPawn = " + pawnInit.getNbPawn());
				System.out.println("You don't have this pawn anymore !");
				break;
			}
			if (!pawns.isEmpty() && pawnInit.getNbPawn() > 0
					&& pawns.elementAt(i).getLevel() == pawnInit.getLevel()) {
				// System.out.println("Size = " + pawns.size());
				// System.out
				// .println("Name = " + pawns.elementAt(i).getNamePawn());
				//
				// System.out.println("Size = " + pawns.size());
				grid1.set(line, row, pawns.elementAt(i));
				grid1.showGrid();
				pawns.removeElementAt(i);
				nbPawn = pawnInit.getNbPawn();
				pawnInit.setNbPawn(--nbPawn);
				// System.out.println("NbPawn = " + pawnInit.getNbPawn());
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
		// System.out.println("Size = " + pawns.size());
		// APawn pawn = grid1.get(line, row);
		System.out.println("line = " + line + " row = " + row);
		System.out.println("Name = " + pawn.getNamePawn());
		grid1.set(line, row, null);
		grid1.showGrid();
		pawns.add(pawn);
		nbPawn = pawn.getNbPawn();
		pawn.setNbPawn(++nbPawn);
		// System.out.println("Name = " + pawn.getNamePawn());
		// System.out.println("NbPawn = " + pawn.getNbPawn());
		// // System.out.println("Size = " + pawns.size());
	}

	/**
	 * 
	 * @return
	 */
	public Game createGrid() {
		return grid1;
	}

}