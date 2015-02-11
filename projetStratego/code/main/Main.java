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
		Scout scou = new Scout(2);
//		Marshal mar = new Marshal(1);
		test.set(2, 2, scou);
//		test.set(2, 3, mar);
		test.showGrid();
//		GridIA test = new GridIA();
//		test.showGrid();
		System.out.println(scou.movePoss(test,2, 5));
		scou.move(test, 2, 5);
		test.showGrid();
	}
}
