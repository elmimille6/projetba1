package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import pawn.APawn;
import pawn.Bomb;
import pawn.Flag;
import pawn.Lake;
import util.Dic;

/**
 * Grid is the class that creates a "grid" object.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Game implements java.io.Serializable {

	private static final long serialVersionUID = 8927958880942845647L;
	private APawn[][] grid;
	private int row, line;
	public int view = 1, turn = 1, player = 2, nbPawn = 40;
	public Dic startTeam;
	public int[] lastMove = { -1, -1, -1 };
	public APawn Flag = new Flag(1), Bomb = new Bomb(1);

	/**
	 * Main constructor of the class.
	 */
	public Game() {

	}

	public Game(APawn[][] ngrid) {
		grid = ngrid;
		row = grid[0].length;
		line = grid.length;
	}

	/**
	 * Constructor of the grid.
	 * 
	 * @param rowNumber
	 *            The number of rows.
	 * 
	 * @param lineNumber
	 *            The number of lines.
	 * 
	 * @param gameMode
	 *            The mode of the current game: 0 if placement of the pawns <br/>
	 *            1 if original Stratego <br/>
	 *            and 2 if Stratego Duel.
	 */
	public Game(int rowNumber, int lineNumber, int gameMode) {
		row = rowNumber;
		line = lineNumber;
		grid = new APawn[line][row];
		if (gameMode == 1) {
			APawn lake = new Lake();
			grid[4][2] = lake;
			grid[4][3] = lake;
			grid[5][2] = lake;
			grid[5][3] = lake;
			grid[4][6] = lake;
			grid[4][7] = lake;
			grid[5][6] = lake;
			grid[5][7] = lake;
		} else if (gameMode == 2) {
			nbPawn = 10;
			APawn lake = new Lake();
			grid[3][2] = lake;
			grid[4][2] = lake;
			grid[3][5] = lake;
			grid[4][5] = lake;
		}
		startTeam = startTeam();
	}

	/**
	 * Constructor of the grid.
	 * 
	 * @param size
	 *            Size of the grid.
	 * 
	 * @param gameMode
	 *            The mode of the current game: 0 if placement of the pawns <br/>
	 *            1 if original Stratego <br/>
	 *            and 2 if Stratego Duel.
	 */
	public Game(int size, int gameMode) {
		this(size, size, gameMode);
		System.out.println("gameMode = " + gameMode);
	}

	/**
	 * Puts the pawn on the grid.
	 * 
	 * @param i
	 *            Line of the grid.
	 * 
	 * @param j
	 *            Column of the grid.
	 * 
	 * @param x
	 *            The pawn to put.
	 */
	public void set(int i, int j, APawn x) {
		grid[i][j] = x;
		if (x instanceof APawn) {
			x.setPos(i, j);
		}
	}

	/**
	 * Displays the grid.
	 */
	public void showGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == null) {
					System.out.print("n     ");
				} else {
					System.out.print(grid[i][j] + "    ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Places the teams on each side of the grid.
	 * 
	 * @param tgrid
	 *            The grid of pawn to place in the grid.
	 * 
	 * @param side
	 *            The side of the grid: 1 in the bottom of the grid and 2 on the
	 *            top of the grid.
	 */
	public void placeTeam(APawn[][] tgrid, int side) {
		if (side == 1) {
			for (int i = 0; i < tgrid.length; i++) {
				for (int j = 0; j < tgrid[0].length; j++) {
					// grid[6+i][0+j]=tgrid[i][j];
					this.set(6 + i, 0 + j, tgrid[i][j]);
				}
			}
		}
		if (side == 2) {
			for (int i = 0; i < tgrid.length; i++) {
				for (int j = 0; j < tgrid[0].length; j++) {
					// grid[3-i][9-j]=tgrid[i][j];
					// tgrid[i][j].setPos(3-i, 9-j);
					tgrid[i][j].setTeam(2);
					this.set(3 - i, 9 - j, tgrid[i][j]);
				}
			}
		}
	}

	public void placeTeam(GridStart grid, int side) {
		placeTeam(grid.getGrid(), side);
	}

	/**
	 * Saves the grid in the 'grid.save' file.
	 */
	public void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream("grid.save"));
			out.writeObject(this);
			out.close();
		} catch (IOException e) {

		}
	}

	/**
	 * Loads the grid from the 'grid.save' file.
	 */
	public static Game load() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					"grid.save"));
			Game grid = (Game) in.readObject();
			in.close();
			System.out.println("HERE+   " + grid);
			return grid;
		} catch (ClassNotFoundException e1) {

		} catch (IOException e2) {

		}
		return null;

	}

	/**
	 * Gets the pawn at the given coordinates.
	 * 
	 * @param i
	 *            Line of the pawn.
	 * 
	 * @param j
	 *            Column of the pawn.
	 * 
	 * @return Pawn at the given coordinates.
	 */
	public APawn getPawn(int i, int j) {
		return grid[i][j];
	}

	/**
	 * Gets the forward row.
	 * 
	 * @return The forward row.
	 */
	public int getRow() {
		return row - 1;
	}

	/**
	 * Gets the forward line.
	 * 
	 * @return The forward line.
	 */
	public int getLine() {
		return line - 1;
	}

	/**
	 * Gets the grid.
	 * 
	 * @return The grid.
	 */
	public APawn[][] getGrid() {
		return this.grid;
	}

	/**
	 * Gets the view.
	 * 
	 * @return The value of view.
	 */
	public int getView() {
		return view;
	}

	/**
	 * Sets a new value to the view.
	 * 
	 * @param vie
	 *            Changes the view.
	 */
	public void setView(int vie) {
		this.view = vie;
	}

	/**
	 * Gets the turn.
	 * 
	 * @return The current turn.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Adds one turn.
	 */
	public void addTurn() {
		turn++;
	}

	/**
	 * Gets the number of players.
	 * 
	 * @return The current number of player.
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * Sets the number of players.
	 * 
	 * @param players
	 *            Changes the number of players.
	 */
	public void setPlayer(int players) {
		this.player = players;
	}

	private Dic startTeam() {
		Dic team = new Dic();
		Vector<APawn> liste = APawn.createTeam(1, nbPawn);
		while (!liste.isEmpty()) {
			if (team.isIn(liste.get(0))) {
				team.increase(liste.get(0));
			} else {
				team.add(liste.get(0), 1);
			}
			liste.remove(0);
		}
		return team;
	}

	public Dic getStartTeam() {
		return startTeam;
	}

	public int[] getLastMove() {
		System.out.println("MOVE!!!!!!!!!!!!!!!!!");
		return lastMove;
	}

	public void setLastMove(int[] move) {
		this.lastMove = move;
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

		for (int j = 0; j < this.getLine() + 1; j++) {
			for (int i = 0; i < this.getRow() + 1; i++) {
				APawn pawn = this.getPawn(i, j);

				if (pawn != null) {
					if (pawn.getTeam() == 1) {
						test = this.canMove(1);
						if (test != 0) {
							canPlay = false;
							winner = test;
						} else if (pawn.getClass() == Flag.getClass()) {
							System.out.println("Flag 1");
							flag1 = true;
						}
					} else if (pawn.getTeam() == 2) {
						test = this.canMove(1);
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
			// System.out.println("winner = " + winner);
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
		for (int j = 0; j < this.getLine() + 1; j++) {
			for (int i = 0; i < this.getRow() + 1; i++) {
				int[] arrayFocus = null;
				nbMoves = 0;
				APawn currentPawn = this.getPawn(i, j);

				if (currentPawn != null && currentPawn.getTeam() == team) {

					nbPawnofTeam++;

					arrayFocus = currentPawn.focus(this);
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
		} else {
			return 0;
		}
	}
}
