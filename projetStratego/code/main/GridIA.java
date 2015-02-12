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
	int val=0;
	
	/**
	 * Constructor of the grid for the IA.
	 */
	public GridIA() {
		int i=0;
		while(val<17150){
			APawn[][] gridEval= createGrid();
			int value = evalGrid(gridEval);
			if (value > val){
				grid = gridEval;
				val=value;
			}
			i++;
		}
		System.out.println(i);
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
		System.out.println(val);
	}

	public static APawn[][] createGrid(){
		APawn[][] grid = new APawn[4][10];
		Vector<APawn> listPawn = APawn.createTeam();
		while (!listPawn.isEmpty()){
			for (int i=0;i<4;i++){
				for (int j=0;j<10;j++){
					Random rand = new Random();
					int nbrA = rand.nextInt(listPawn.size());
					grid[i][j]=listPawn.get(nbrA);
					listPawn.remove(nbrA);
				}
			}
		}
		return grid;
	}

	public int evalGrid(APawn[][] grid){
		int bonus=2000;
		int valeur=0;
		int l1=0,l2=0,l3=0,l4=0;
		for (int i=0;i<10;i++){
			l1+=grid[0][i].getValue();
			l2+=grid[1][i].getValue();
			l3+=grid[2][i].getValue();
			l4+=grid[3][i].getValue();
			if (grid[2][i] instanceof Flag){
				if (i!=0){
					if(grid[2][i-1] instanceof Bomb || grid[2][i-1] instanceof Marshal){
						valeur+=bonus;
					}
					if (i!=9){
						if(grid[2][i+1] instanceof Bomb|| grid[2][i+1] instanceof Marshal){
							valeur+=bonus;
						}
					}
					if(grid[1][i] instanceof Bomb|| grid[1][i] instanceof Marshal){
						valeur+=bonus;
					}
				}
			}
			if (grid[3][i] instanceof Flag){
				if (i!=0){
					if(grid[3][i-1] instanceof Bomb|| grid[3][i-1] instanceof Marshal){
						valeur+=bonus;
					}
					if (i!=9){
						if(grid[3][i+1] instanceof Bomb|| grid[3][i+1] instanceof Marshal){
							valeur+=bonus;
						}
					}
					if(grid[2][i] instanceof Bomb|| grid[2][i] instanceof Marshal){
						valeur+=bonus;
						}
					}
			}
		}
		return valeur+l1+2*l2+4*l3+6*l4;
	}
}
