package main;

import pawn.APawn;

public class Grid {
	private APawn[][] grid; 
	
	public Grid(int size){
		grid = new APawn[10][10];
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
