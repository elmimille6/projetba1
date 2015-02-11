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
		Bomb p1 = new Bomb(2);
		Miner p2 = new Miner(1);
		test.set(2, 2, p1);
		test.set(2, 3, p2);
		test.showGrid();
//		GridIA test = new GridIA();
//		test.showGrid();
		p2.move(test, 2, 2);
		test.showGrid();
	}
}
