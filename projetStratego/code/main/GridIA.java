package main;

import java.util.Random;
import java.util.Vector;

import pawn.*;

/**
 * GridIA is the class that creates a "grid" object for the IA.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class GridIA {

	APawn[][] grid;
	int val = 0;

	public GridIA() {

	}

	/**
	 * Constructor of the grid for the IA.
	 * 
	 * @param team
	 *            The team of the IA.
	 */
	public GridIA(int team) {
		eval(team, 17000);
	}

	public GridIA(APawn[][] tab) {
		this.grid = tab;
	}

	public GridIA(int team, int lvl) {
		if (lvl == 1) {
			eval(team, 17000);
		} else if (lvl == 0) {
			eval(team, 10000);
		}
	}

	public void eval(int team, int valeur) {
		while (val < valeur) {
			APawn[][] gridEval = createGrid(team);
			int value = evalGrid(gridEval);
			if (value > val) {
				grid = gridEval;
				val = value;
			}
		}
	}

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
		int valeur = 0;
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
						valeur += bonus;
					}
					if (i != 9) {
						if (grid[2][i + 1].getClass() == Bomb.class
								|| grid[2][i + 1].getClass() == Marshal.class) {
							valeur += bonus;
						}
					} else {
						valeur += bonus;
					}
					if (grid[1][i].getClass() == Bomb.class
							|| grid[1][i].getClass() == Marshal.class) {
						valeur += bonus;
					}
				} else {
					valeur += bonus;
					if (grid[2][i + 1].getClass() == Bomb.class
							|| grid[2][i + 1].getClass() == Marshal.class) {
						valeur += bonus;
					}
					if (grid[1][i].getClass() == Bomb.class
							|| grid[1][i].getClass() == Marshal.class) {
						valeur += bonus;
					}

				}
			}
			if (grid[3][i].getClass() == Flag.class) {
				if (i != 0) {
					if (grid[3][i - 1].getClass() == Bomb.class
							|| grid[3][i - 1].getClass() == Marshal.class) {
						valeur += bonus;
					}
					if (i != 9) {
						if (grid[3][i + 1].getClass() == Bomb.class
								|| grid[3][i + 1].getClass() == Marshal.class) {
							valeur += bonus;
						}
					}
					if (grid[2][i].getClass() == Bomb.class
							|| grid[2][i].getClass() == Marshal.class) {
						valeur += bonus;
					}
				} else {
					valeur += bonus;
					if (i != 9) {
						if (grid[3][i + 1].getClass() == Bomb.class
								|| grid[3][i + 1].getClass() == Marshal.class) {
							valeur += bonus;
						}
					}
					if (grid[2][i].getClass() == Bomb.class
							|| grid[2][i].getClass() == Marshal.class) {
						valeur += bonus;
					}
				}
			}
		}
		return valeur + l1 + 2 * l2 + 4 * l3 + 6 * l4;
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
