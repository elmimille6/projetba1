package main;

import pawn.*;
import gui.*;

/**
 * This is the main class of this project.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Main {

	public static MenuWindow menu;

	public static void main(String[] args) {
		menu = new MenuWindow();
		// Grid test = new Grid(10);
		// Grid test = Grid.load();
		//
		// Scout p1 = new Scout(1);
		// Miner p2 = new Miner(2);
		// Spy p3 = new Spy(2);
		// Miner p2 = new Miner(1);
		// test.set(2, 2, p1);
		// test.set(2, 5, p2);
		// test.set(2, 6, p3);
		// test.showGrid();
		// test.save();
		// System.out.println(p2.movePoss(test, 2, 2));
		// p1.move(test, 2, 6);
		// test.showGrid();
		// GridIA test = new GridIA();
		// test.showGrid();
		// p2.move(test, 2, 2);
		// test.showGrid();
		// Window fen = new Window();
		// Main.play();

	}

	/**
	 * 
	 */
	public static void play() {
		int nbrPlayer = menu.getNbrPlayer();
		int initGridGame = menu.getInitGridGame();
		int typeOfGame = menu.getTypeOfGame();
		WindowInitPawn initPawn;
		Game grid;
		GridIA gridIA, gridIA2;
		if (initGridGame == 0) {
			System.out.println("Not init");
			if (typeOfGame == 40) {
				/*
				 * grid = new Game(10, 1); // grid.setView(2); gridIA = new
				 * GridIA(1); gridIA2 = new GridIA(2);
				 */
				System.out.println("Stratego");
			} else {
				/*
				 * grid = new Game(8, 1); // grid.setView(2); gridIA = new
				 * GridIA(1); gridIA2 = new GridIA(2);
				 */
				System.out.println("Stratego Duel");
			}

			grid = new Game(10, 1);
			gridIA = new GridIA(1);
			gridIA2 = new GridIA(2);
			grid.placeTeam(gridIA.getGrid(), 1);
			grid.placeTeam(gridIA2.getGrid(), 2);
			grid.showGrid();
			/* WindowGame fenGame = */new WindowGame(grid);

		} else if (initGridGame == 1) { //TODO
			System.out.println("Init 1");
			initPawn = new WindowInitPawn(chosenSize(typeOfGame), nbrPlayer, typeOfGame, 1);
		} else if (initGridGame == 2) {
			System.out.println("Init 2");
			initPawn = new WindowInitPawn(chosenSize(typeOfGame), nbrPlayer, typeOfGame, 2);
		} else {
			System.out.println("Init 3");
			initPawn = new WindowInitPawn(chosenSize(typeOfGame), nbrPlayer, typeOfGame, 0);
		}
	}
	
	public static Game chosenSize(int typeOfGame) {
		if (typeOfGame == 40) {
			return new Game(10, 4, 0);
		} else {
			return new Game(8, 3, 0);
		}
	}
}