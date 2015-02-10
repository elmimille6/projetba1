package main;

import pawn.APawn;
import pawn.Lake;

/**
* Grid is the class that creates a "grid" object.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Grid {
	private APawn[][] grid; 
	
	public Grid(int size){
		grid = new APawn[10][10];
		APawn lake = new Lake();
//		grid[4][2]= lake;
//		grid[4][3]= lake;
//		grid[5][2]= lake;
//		grid[5][3]= lake;
//		grid[4][6]= lake;
//		grid[4][7]= lake;
//		grid[5][6]= lake;
//		grid[5][7]= lake;
	}
	
	/**
	 * Put the pawn on the grid.
	 * 
	 * @param i
	 *    Line of the grid
	 *
	 * @param j
	 *    Column of the grid
	 *
	 * @param x
	 *    The pawn to put
	 */
	public void set(int i, int j,APawn x){
		grid[i][j]=x;
		x.setPos(i,j);
	}
	
	/**
	 * Displays the grid.
	 */
	public void showGrid(){
		for (int i = 0;i<grid.length;i++){
			for (int j = 0; j<grid[0].length;j++){
				System.out.print(grid[i][j]+"   ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Check if the pawn can move.
	 * 
	 * @param i 
	 *    Line of the pawn
	 *
	 * @param j
	 *    Column of the pawn
	 *
	 * @return
	 *    A boolean: true or false
	 */
	public boolean pawnCanMove(int i, int j){
		APawn pawn = grid[i][j];
		if (pawn!= null){
			if (pawn.canMove()){
				if (i!=0){
					if (grid[i-1][j]==null){
						return true;
					}
				}
			}
		}
		return false;
	}
	public APawn get(int i,int j){
		return grid[i][j];
	}
}
