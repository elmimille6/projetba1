package main;

import pawn.*;

/**
 * Grid is the class that creates a "grid" object.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class GridPawn{
	private APawn[][] grid;
	private int row, line;

	/**
	 * Constructor of the grid.
	 * 
	 * @param size
	 *            Size of the grid.
	 */
	public GridPawn(int rowSize, int lineSize) {
		row = rowSize;
		line = lineSize;
		grid = new APawn[line][row];
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
		if (x != null && x.getClass() == APawn.class) {
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
	public APawn get(int i, int j) {
		return grid[i][j];
	}

	/**
	 * Accessor of the row.
	 * 
	 * @return The forward row.
	 */
	public int getRow() {
		return row - 1;
	}

	/**
	 * Accessor of the line.
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
}
