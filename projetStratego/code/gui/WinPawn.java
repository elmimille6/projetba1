package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;




import main.Game;
import main.GridIA;
//import main.Game;
import main.GridPawn;
//import main.GridIA;
import pawn.*;

public class WinPawn extends JFrame {

	private static final long serialVersionUID = 1L;
	public GridPawn grid1, grid2;
	public PanePawn pane1, pane2;
	public Vector<APawn> pawns = APawn.createTeam(1);
	public APawn spyInit = new Spy(1), scoutInit = new Scout(1),
			minerInit = new Miner(1);
	public APawn sergeantInit = new Sergeant(1),
			lieutenantInit = new Lieutenant(1);
	public APawn captainInit = new Captain(1), majorInit = new Major(1),
			colonelInit = new Colonel(1);
	public APawn generalInit = new General(1), marshalInit = new Marshal(1),
			bombInit = new Bomb(1);
	public APawn flagInit = new Flag(1);
	public int nbPawn = 0, pawnLevel = 0;
	public int x = 0, y = 0;

	public int posX, posY;
	public APawn focus;

	/**
	 * 
	 */
	public WinPawn() {
		this.setTitle("Initialisation grille");
		this.setSize(700, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		grid1 = new GridPawn(10, 4);
		grid2 = new GridPawn(6, 2);

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

		grid1.showGrid();
		this.setLocationRelativeTo(null);
		// On definit le layout a utiliser sur le content pane
		this.setLayout(new BorderLayout());
		// On ajoute le bouton au content pane de la JFrame

		int xSize = ((int) this.getWidth());
		int ySize = ((int) this.getHeight());
		this.setSize(xSize, ySize);
		int northHeight = (int) (Math.round(ySize * 0.55));
		// int centerHeight = (int) (Math.round(ySize * 0.15));
		int southHeight = (int) (Math.round(ySize * 0.36));

		pane1 = new PanePawn(grid1);
		pane1.setPreferredSize(new Dimension(0, northHeight));

		JPanel Center = new JPanel();
		// Center.setPreferredSize(new Dimension(0, centerHeight));
		JButton play= new JButton("Play");
		Center.add(new JButton("Save")); // Save the grid in the 'Saves' folder
		//Center.add(new JButton("OK")); // Lauches the game with the grid
		Center.add(play);
		Center.add(new JButton("Load")); // Search save in the 'Saves' folder
		
		final JFrame fen= this;
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Game grid = new Game(10);
				GridPawn gridPlayer = createGrid();
				GridIA gridIA = new GridIA(2);
				grid.placeTeam(gridPlayer.getGrid(), 1);
				grid.placeTeam(gridIA.getGrid(), 2);
				fen.dispose();
				WinGame fenGame = new WinGame(grid);
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
					APawn pawn = grid2.get(line, row);
					System.out.println("pawn = " + pawn);
					chooseSquare(pawn);
					repaint();
				}
			}

		});

	}

	/*
	 * public void placePawn(APawn pawnInit) { for (int i = 0; i < pawns.size();
	 * i++) { chosen(pawnInit);
	 */
	/*
	 * while (!pawns.isEmpty() && nbPawn > 0 && pawns.elementAt(i).getLevel() ==
	 * pawnLevel) { System.out.println("Size = " + pawns.size());
	 * System.out.println("Name = " + pawns.elementAt(i).getName());
	 * System.out.println("Level = " + pawns.elementAt(i) + "\n"); grid1.set(y,
	 * x, pawns.elementAt(i)); grid1.showGrid(); pawns.removeElementAt(i);
	 * nbPawn--; if (y < 3) { y++; } else { y= 0; x++; } }
	 */
	/*
	 * if (!pawns.isEmpty() && nbPawn > 0 && pawns.elementAt(i).getLevel() ==
	 * pawnLevel) { System.out.println("Size = " + pawns.size()); System.out
	 * .println("Name = " + pawns.elementAt(i).getNamePawn());
	 * System.out.println("Level = " + pawns.elementAt(i) + "\n"); grid1.set(y,
	 * x, pawns.elementAt(i)); grid1.showGrid(); pawns.removeElementAt(i);
	 * nbPawn--; if (y < 3) { y++; } else { y = 0; x++; } break; } else if
	 * (nbPawn == 0) {
	 * System.out.println("The vector doesn't contain this pawn !"); } else if
	 * (pawns.isEmpty()) { System.out.println("The vector is empty !"); } else
	 * System.out.println("nbPawn = " + nbPawn); } }
	 */

	/**
	 * 
	 * @param pawn
	 */
	public void chosen(APawn pawn) {
		if (spyInit.getLevel() == pawn.getLevel()) {
			nbPawn = 1;
			pawnLevel = spyInit.getLevel();
		} else if (scoutInit.getLevel() == pawn.getLevel()) {
			nbPawn = 8;
			pawnLevel = scoutInit.getLevel();
		} else if (minerInit.getLevel() == pawn.getLevel()) {
			nbPawn = 5;
			pawnLevel = minerInit.getLevel();
		} else if (sergeantInit.getLevel() == pawn.getLevel()) {
			nbPawn = 4;
			pawnLevel = sergeantInit.getLevel();
		} else if (lieutenantInit.getLevel() == pawn.getLevel()) {
			nbPawn = 4;
			pawnLevel = lieutenantInit.getLevel();
		} else if (captainInit.getLevel() == pawn.getLevel()) {
			nbPawn = 4;
			pawnLevel = captainInit.getLevel();
		} else if (majorInit.getLevel() == pawn.getLevel()) {
			nbPawn = 3;
			pawnLevel = majorInit.getLevel();
		} else if (colonelInit.getLevel() == pawn.getLevel()) {
			nbPawn = 2;
			pawnLevel = colonelInit.getLevel();
		} else if (generalInit.getLevel() == pawn.getLevel()) {
			nbPawn = 1;
			pawnLevel = generalInit.getLevel();
		} else if (marshalInit.getLevel() == pawn.getLevel()) {
			nbPawn = 1;
			pawnLevel = marshalInit.getLevel();
		} else if (bombInit.getLevel() == pawn.getLevel()) {
			nbPawn = 6;
			pawnLevel = bombInit.getLevel();
		} else {
			nbPawn = 1;
			pawnLevel = flagInit.getLevel();
		}
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
	public int[] getRes(GridPawn grid, PanePawn pane, int posX, int posY) {

		int[] res = { 0, 0 };
		res[1] = (posX - (posX % (pane.getWidth() / (grid.getRow() + 1))))
				/ (pane.getWidth() / (grid.getRow() + 1));
		res[0] = (posY - (posY % (pane.getHeight() / (grid.getLine() + 1))))
				/ (pane.getHeight() / (grid.getLine() + 1));
		return res;
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
				if (e.getButton() == MouseEvent.BUTTON1) {
					posX = e.getX();
					posY = e.getY();
					int[] res = getRes(grid1, pane1, posX, posY);
					int line = res[0];
					int row = res[1];
					if (grid1.get(line, row) == null) {
						System.out.println("line = " + line + " row = " + row);
						placePawn(pawnInit, line, row);
						repaint();
					} else {
						System.out.println("You can't play here !");
					}
				}
			}
		});
	}

	/**
	 * 
	 * @param pawnInit
	 * @param line
	 * @param row
	 */
	public void /* GridPawn */placePawn(APawn pawnInit, int line, int row) {
		for (int i = 0; i < pawns.size(); i++) {
			chosen(pawnInit);
			if (!pawns.isEmpty() && nbPawn > 0
					&& pawns.elementAt(i).getLevel() == pawnLevel) {
				System.out.println("Size = " + pawns.size());
				System.out
						.println("Name = " + pawns.elementAt(i).getNamePawn());
				System.out.println("Level = " + pawns.elementAt(i) + "\n");
				grid1.set(line, row, pawns.elementAt(i));
				grid1.showGrid();
				pawns.removeElementAt(i);
				nbPawn--;
				break;
			}
			if (pawns.size() == 1) {
				System.out.println("The vector is empty !");
			}
		}
		// return grid1;
	}
	
	public GridPawn createGrid() {
		return grid1;
	}

}