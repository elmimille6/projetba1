package main;

import pawn.*;

/**
* This is the main class of this project.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Main {

	public static void main(String[] args){ 
//		Window fenetre = new Window();
		Grid test = new Grid(10);
//		
		Captain cap = new Captain(2);
		Marshal mar = new Marshal(1);
		test.set(2, 2, cap);
		test.set(2, 3, mar);
		test.showGrid();
//		GridIA test = new GridIA();
//		test.showGrid();
		mar.move(test, 2, 2);
		test.showGrid();
	}
}
