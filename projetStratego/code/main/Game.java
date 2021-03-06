package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import pawn.APawn;
import pawn.Lake;

/**
 * Grid is the class that creates a "grid" object.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Game implements java.io.Serializable {

	private static final long serialVersionUID = 8927958880942845647L;
	private APawn[][] grid = new APawn[10][10];
	private int row = 10, line = 10;
	private int turn = 1, player = 2, nbPawns = 40;
	public String level;
	private int[] lastMove = { -1, -1, -1 };
	private int complete = 0;
	private int initGridGame = 0, gameN;

	/**
	 * Main constructor of the class.
	 */
	public Game() {
		APawn lake = new Lake();
		grid[4][2] = lake;
		grid[4][3] = lake;
		grid[5][2] = lake;
		grid[5][3] = lake;
		grid[4][6] = lake;
		grid[4][7] = lake;
		grid[5][6] = lake;
		grid[5][7] = lake;
	}

	/**
	 * Constructor of the grid.
	 * 
	 * @param grid
	 *            The grid of the game.
	 */
	public Game(APawn[][] grid) {
		this.grid = grid;
		row = this.grid[0].length;
		line = this.grid.length;
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
	 *            The mode of the current game:<br/>
	 *            0 if placement of the pawns,<br/>
	 *            1 if original Stratego,<br/>
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
			APawn lake = new Lake();
			grid[3][2] = lake;
			grid[4][2] = lake;
			grid[3][5] = lake;
			grid[4][5] = lake;
		}
		startTeam();
	}

	/**
	 * Constructor of the grid.
	 * 
	 * @param size
	 *            Size of the grid.
	 * 
	 * @param gameMode
	 *            The mode of the current game:<br/>
	 *            0 if placement of the pawns,<br/>
	 *            1 if original Stratego,<br/>
	 *            and 2 if Stratego Duel.
	 */
	public Game(int size, int gameMode) {
		this(size, size, gameMode);
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
	 * Displays the grid in the terminal.
	 */
	public void showGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == null) {
					System.out.print("n       ");
				} else {
					System.out.print(grid[i][j] + "    ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Places the chosen team on the given side of the grid.
	 * 
	 * @param grid
	 *            The grid of pawn to place in the grid.
	 * 
	 * @param side
	 *            The side of the grid:<br/>
	 *            1 in the bottom of the grid,<br/>
	 *            2 on the top of the grid.
	 */
	public void placeTeam(APawn[][] grid, int side) {

		if (side == 1) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					APawn pawn = grid[i][j];
					if (pawn != null) {
						pawn.setTeam(1);
						if (nbPawns == 40) {
							this.set(6 + i, 0 + j, pawn);
						} else {
							this.set(5 + i, 0 + j, pawn);
						}
					}

				}
			}
		}
		if (side == 2) {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					APawn pawn = grid[i][j];
					if (pawn != null) {
						pawn.setTeam(2);
						if (nbPawns == 40) {
							this.set(3 - i, 9 - j, pawn);
						} else {
							this.set(2 - i, 7 - j, pawn);
						}
					}
				}
			}
		}
	}

	/**
	 * Places the chosen team on the given side of the grid.
	 * 
	 * @param grid
	 *            The GridStart grid to place in the grid.
	 * 
	 * @param side
	 *            The side of the grid:<br/>
	 *            1 in the bottom of the grid,<br/>
	 *            2 on the top of the grid.
	 */
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
			e.printStackTrace();
		}
	}

	/**
	 * Loads the grid from the 'grid.save' file.
	 * 
	 * @return The grid if there's no problem,<br/>
	 *         null otherwise.
	 */
	public static Game load() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					"grid.save"));
			Game grid = (Game) in.readObject();
			in.close();
			return grid;
		} catch (ClassNotFoundException e1) {

		} catch (IOException e2) {

		}
		return null;

	}

	/**
	 * Creates a dic of the pawns of a team which start the game.
	 * 
	 * @return A 'Dic' object containing the pawns of a team which start the
	 *         game.
	 */
	private Dic startTeam() {
		Dic team = new Dic();
		Vector<APawn> liste = APawn.createTeam(0, 1, nbPawns);
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

	/**
	 * Checks if the game is over and if so, return the number of the player who
	 * win.
	 * 
	 * @return 0 if the game isn't over,<br/>
	 *         1 if player 1 win,<br/>
	 *         2 if player 2 win.
	 */
	public int win() {
		boolean canPlay = false;
		boolean flag1 = false;
		boolean flag2 = false;
		for (int j = 0; j < this.getRow() + 1; j++) {
			for (int i = 0; i < this.getLine() + 1; i++) {
				if (this.getPawn(i, j) != null) {
					APawn pawn = this.getPawn(i, j);
					if (pawn.getTeam() == 1 && pawn.getLevel() == -6) {
						flag1 = true;
					}
					if (pawn.getTeam() == 2 && pawn.getLevel() == -6) {
						flag2 = true;
					}
					if (pawn.getTeam() == ((turn + 1) % 2) + 1
							&& canPlay == false) {
						if (pawn.movePoss(this, j, i - 1)) {
							canPlay = true;
						} else if (pawn.movePoss(this, i, j + 1)) {
							canPlay = true;
						} else if (pawn.movePoss(this, i - 1, j)) {
							canPlay = true;
						} else if (pawn.movePoss(this, i + 1, j)) {
							canPlay = true;
						}

					}
				}

			}

		}
		if (!flag1) {
			return 2;
		}
		if (!flag2) {
			return 1;
		}
		if (!canPlay) {
			return (turn % 2) + 1;
		}
		return 0;
	}

	/**
	 * Returns the team of the winner or 0 if the game isn't over.
	 * 
	 * @param team
	 *            The team of the current pawn:<br/>
	 *            1 for Red <br/>
	 *            2 for Blue.
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

	/**
	 * Returns a grid "Game" of the chosen size.
	 * 
	 * @param typeOfGame
	 *            40 for the Stratego, <br/>
	 *            10 for the Stratego Duel.
	 * 
	 * @return A grid "Game" of the chosen size.
	 */
	public static Game chosenSize(int typeOfGame) {
		if (typeOfGame == 40) {
			return new Game(10, 4, 0);
		} else {
			return new Game(8, 3, 0);
		}
	}

	/**
	 * Increments the "complete" value.<br/>
	 * When its value is 2, the grid is complete.
	 */
	public void addComplete() {
		complete++;
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
		if (i >= 0 && i < line && j >= 0 && j < row) {
			return grid[i][j];
		}
		return null;
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

	/**
	 * Gets the team which begins.
	 * 
	 * @return The team which begins.
	 */
	public Dic getStartTeam() {
		return startTeam();
	}

	/**
	 * Gets the last move in the game.
	 * 
	 * @return A grid containing the lasts moves.
	 */
	public int[] getLastMove() {
		return lastMove;
	}

	/**
	 * Sets the last move in the game from the 'move' parameter.
	 * 
	 * @param move
	 *            The last move in the game.
	 */
	public void setLastMove(int[] move) {
		this.lastMove = move;
	}

	/**
	 * Gets the number of pawns.
	 * 
	 * @return The number of pawns:<br/>
	 *         40 for the Original Stratego or<br/>
	 *         10 for the Stratego Duel.
	 */
	public int getNbPawns() {
		return nbPawns;
	}

	/**
	 * Sets the number of pawns.
	 * 
	 * @param nb
	 *            The number of pawns:<br/>
	 *            40 for the Original Stratego or<br/>
	 *            10 for the Stratego Duel.
	 */
	public void setNbPawns(int nb) {
		this.nbPawns = nb;
	}

	/**
	 * Gets if the grid of the game is complete.
	 * 
	 * @return true if the grid of the game is complete, <br/>
	 *         false otherwise.
	 */
	public int getComplete() {
		return complete;
	}

	/**
	 * Sets the value of the 'complete' value.
	 * 
	 * @param complete
	 *            0 if the grid is empty or<br/>
	 *            1 if the grid contains 1 team or<br/>
	 *            2 if the grid is complete.
	 */
	public void setComplete(int complete) {
		this.complete = complete;
	}

	/**
	 * Resets the number of moves for the given pawn.
	 * 
	 * @param pawn
	 *            The pawn we want to resets its number of moves.
	 */
	public void resetMove(APawn pawn) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				APawn pa = grid[i][j];
				if (pa != null) {
					if (pa.getTeam() == pawn.getTeam()) {
						if (pawn.posX != i || pawn.posY != j) {
							pa.resetMove();
						}
					}
				}
			}
		}

	}

	/**
	 * Gets the level of the IA.
	 * 
	 * @return The level of the IA.
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Sets the level of the IA.
	 * 
	 * @param level
	 *            The level:<br/>
	 *            0 for the low IA,<br/>
	 *            2 for the higher IA.
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Gets if the player wants to initialize its grid.
	 * 
	 * @return 0 if everything is automatic,<br/>
	 *         1 if the red player wants to initialize,<br/>
	 *         2 if the blue player wants to initialize or<br/>
	 *         3 if the two players want to initialize.
	 */
	public int getInitGridGame() {
		return initGridGame;
	}

	/**
	 * Sets the 'initGridGame' value to the given one.
	 * 
	 * @param initGridGame
	 *            The new value of 'initGridGame'.
	 */
	public void setInitGridGame(int initGridGame) {
		this.initGridGame = initGridGame;
	}

	/**
	 * Gets the next team.
	 * 
	 * @return The next team (1 or 2).
	 */
	public int getNextTeam() {
		return ((turn + 1) % 2) + 1;
	}

	/**
	 * Gets the value of 'gameN'.<br/>
	 * If the game is online, checks if it must creates a new window or update
	 * it.
	 * 
	 * @return 0 if it must create a new window,<br/>
	 *         1 otherwise.
	 */
	public int getGameN() {
		return this.gameN;
	}

	/**
	 * Sets the value of 'gameN'.
	 * 
	 * @param gameN
	 *            0 if it must create a new window,<br/>
	 *            1 otherwise.
	 */
	public void setGameN(int gameN) {
		this.gameN = gameN;
	}
}
