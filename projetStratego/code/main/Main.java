package main;

import pawn.*;
import gui.*;

/**
* This is the main class of this project.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Main {

	public static void main(String[] args){ 
//		Window fenetre = new Window();
		Grid test = new Grid(10);
		
//		Scout p1 = new Scout(1);
//		Miner p2 = new Miner(2);
//		Spy p3 = new Spy(2);
//    	Miner p2 = new Miner(1);
//		test.set(2, 2, p1);
//		test.set(2, 5, p2);
//		test.set(2, 6, p3);
//		test.showGrid();
//		System.out.println(p2.movePoss(test, 2, 2));
//		p1.move(test, 2, 6);
//		test.showGrid();
//		GridIA test = new GridIA();
//		test.showGrid();
//		p2.move(test, 2, 2);
//		test.showGrid();
		Main.play();
		
	}
	public static void play(){
		WinGame fen=new WinGame();
	}
}
