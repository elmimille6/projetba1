package main;

import pawn.APawn;
import pawn.Lake;

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
	
	public void set(int i, int j,APawn x){
		grid[i][j]=x;
	}
	
	public void showGrid(){
		for (int i = 0;i<grid.length;i++){
			for (int j = 0; j<grid[0].length;j++){
				System.out.print(grid[i][j]+"   ");
			}
			System.out.println();
		}
	}
}
