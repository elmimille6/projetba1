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
//		Grid test = new Grid(10);
//		Grid test = Grid.load();
//		
//		Scout p1 = new Scout(1);
//		Miner p2 = new Miner(2);
//		Spy p3 = new Spy(2);
//    	Miner p2 = new Miner(1);
//		test.set(2, 2, p1);
//		test.set(2, 5, p2);
//		test.set(2, 6, p3);
//		test.showGrid();
//		test.save();
//		System.out.println(p2.movePoss(test, 2, 2));
//		p1.move(test, 2, 6);
//		test.showGrid();
//		GridIA test = new GridIA();
//		test.showGrid();
//		p2.move(test, 2, 2);
//		test.showGrid();
//		Window fen = new Window();
		play();
		
	}
	public static void play(){
		Grid grid = new Grid(10);
//		grid.setView(2);
		GridIA gridIA = new GridIA(1);
		grid.placeTeam(gridIA.getGrid(), 1);
		GridIA gridIA2 = new GridIA(2);
		grid.placeTeam(gridIA2.getGrid(), 2);
		grid.showGrid();
//		Scout p1 = new Scout(1);
//		grid.set(4, 0, p1);
		WinGame fen=new WinGame(grid);
	}
}
