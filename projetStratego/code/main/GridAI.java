package main;

import java.util.Random;
import java.util.Vector;

import pawn.*;

/**
 * GridAI is the class that creates a "grid" object for the AI.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class GridAI {

	APawn[][] grid;
	int val = 0;

	/**
	 * Main constructor of the class.
	 */
	public GridAI() {
	}

	/**
	 * Constructor of the grid for the AI.
	 * 
	 * @param team
	 *            The team of the IA.
	 */
	public GridAI(int team) {
		eval(team, 17000);
	}

	/**
	 * Constructor of the class,<br/>
	 * takes an APawn[][] object to a GridAI object.
	 * 
	 * @param tab
	 *            The gridAI as an APawn[][] object.
	 */
	public GridAI(APawn[][] tab) {
		this.grid = tab;
	}

	/**
	 * Constructor of the class based on the team and the level of the AI.
	 * 
	 * @param team
	 *            The team of the new GridAI.
	 * 
	 * @param level
	 *            The level of the new GridAI.
	 */
	public GridAI(int team, int level) {
		if (level == 1) {
			eval(team, 17000);
		} else if (level == 0) {
			eval(team, 10000);
		}
	}

	/**
	 * This method evaluates the level of the GridIA object created.
	 * 
	 * @param team
	 *            The team of the created grid.
	 * 
	 * @param level
	 *            The value of the level of difficulty of the grid.
	 */
	public void eval(int team, int level) {
		while (val < level) {
			APawn[][] gridEval = createGrid(team);
			int value = evalGrid(gridEval);
			if (value > val) {
				grid = gridEval;
				val = value;
			}
		}
	}

	/**
	 * Gets the pawn at the given coordinates.
	 * 
	 * @param i
	 *            The abscissa of the grid.
	 * 
	 * @param j
	 *            The ordinate of the grid.
	 * 
	 * @return The pawn at the given coordinates.
	 */
	public APawn getPawn(int i, int j) {
		return grid[i][j];
	}

	/**
	 * Displays the grid.
	 */
	public void showGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j] + "   ");
			}
			System.out.println();
		}
		System.out.println(val);
	}

	/**
	 * Creates a grid of pawns for the IA.
	 * 
	 * @param team
	 *            The team of the IA.
	 * 
	 * @return The created grid of pawns for the IA.
	 */
	public static APawn[][] createGrid(int team) {
		APawn[][] grid = new APawn[4][10];
		Vector<APawn> listPawn = APawn.createTeam(0, team, 40);
		// 40 if 40 pawns
		while (!listPawn.isEmpty()) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 10; j++) {
					Random rand = new Random();
					int nbrA = rand.nextInt(listPawn.size());
					grid[i][j] = listPawn.get(nbrA);
					listPawn.remove(nbrA);
				}
			}
		}
		return grid;
	}

	/**
	 * Evaluates the grid.
	 * 
	 * @param grid
	 *            The grid to evaluate.
	 * 
	 * @return The value of the grid.
	 */
	public int evalGrid(APawn[][] grid) {
		int bonus = 2000;
		int value = 0;
		int l1 = 0, l2 = 0, l3 = 0, l4 = 0;
		for (int i = 0; i < 10; i++) {
			l1 += grid[0][i].getValue();
			l2 += grid[1][i].getValue();
			l3 += grid[2][i].getValue();
			l4 += grid[3][i].getValue();
			if (grid[2][i].getClass() == Flag.class) {
				if (i != 0) {
					if (grid[2][i - 1].getClass() == Bomb.class
							|| grid[2][i - 1].getClass() == Marshal.class) {
						value += bonus;
					}
					if (i != 9) {
						if (grid[2][i + 1].getClass() == Bomb.class
								|| grid[2][i + 1].getClass() == Marshal.class) {
							value += bonus;
						}
					} else {
						value += bonus;
					}
					if (grid[1][i].getClass() == Bomb.class
							|| grid[1][i].getClass() == Marshal.class) {
						value += bonus;
					}
				} else {
					value += bonus;
					if (grid[2][i + 1].getClass() == Bomb.class
							|| grid[2][i + 1].getClass() == Marshal.class) {
						value += bonus;
					}
					if (grid[1][i].getClass() == Bomb.class
							|| grid[1][i].getClass() == Marshal.class) {
						value += bonus;
					}

				}
			}
			if (grid[3][i].getClass() == Flag.class) {
				if (i != 0) {
					if (grid[3][i - 1].getClass() == Bomb.class
							|| grid[3][i - 1].getClass() == Marshal.class) {
						value += bonus;
					}
					if (i != 9) {
						if (grid[3][i + 1].getClass() == Bomb.class
								|| grid[3][i + 1].getClass() == Marshal.class) {
							value += bonus;
						}
					}
					if (grid[2][i].getClass() == Bomb.class
							|| grid[2][i].getClass() == Marshal.class) {
						value += bonus;
					}
				} else {
					value += bonus;
					if (i != 9) {
						if (grid[3][i + 1].getClass() == Bomb.class
								|| grid[3][i + 1].getClass() == Marshal.class) {
							value += bonus;
						}
					}
					if (grid[2][i].getClass() == Bomb.class
							|| grid[2][i].getClass() == Marshal.class) {
						value += bonus;
					}
				}
			}
		}
		return value + l1 + 2 * l2 + 4 * l3 + 6 * l4;
	}

	/**
	 * Gets the grid.
	 * 
	 * @return The grid.
	 */
	public APawn[][] getGrid() {
		return this.grid;
	}
}
