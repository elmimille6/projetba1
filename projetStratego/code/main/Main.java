package main;

//import pawn.*;
import gui.MenuWindow;
import gui.WindowGame;
import gui.WindowInitPawn;

import java.util.Random;

/**
 * This is the main class of this project.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Main {

	public static MenuWindow menu;
	public static int nbrPlayer, initGridGame, typeOfGame;
	public static WindowInitPawn initPawn;
	public static Game grid;
	public static GridIA gridIA, gridIA2;

	/**
	 * Method that launches the menu of the program.
	 *
	 * @param args
	 *            Number of arguments given.
	 */
	public static void main(String[] args) {
		menu = new MenuWindow();
//		Random rand = new Random();
//		rand.nextInt(10);
	}

	/**
	 * Launches the game or the initialization window.
	 */
	public static void play() {
		nbrPlayer = menu.getNbrPlayer();
		initGridGame = menu.getInitGridGame();
		typeOfGame = menu.getTypeOfGame();
		if (initGridGame == 0) {//check the menu.comboinit
			System.out.println("Automatic");
			if (typeOfGame == 40) {
				System.out.println("Stratego");
				// grid = new Game(10, 1);
				// gridIA = new GridIA(1);
				// gridIA2 = new GridIA(2);
			} else {
				System.out.println("Stratego Duel");
				/*
				 * grid = new Game(8, 1); // grid.setView(2); gridIA = new
				 * GridIA(1); gridIA2 = new GridIA(2);
				 */
			}

			grid = new Game(10, 1);
			gridIA = new GridIA(1);
			gridIA2 = new GridIA(2);
			grid.placeTeam(gridIA.getGrid(), 1);
			grid.placeTeam(gridIA2.getGrid(), 2);
			new WindowGame(grid);

		} else if (initGridGame == 1) { // TODO
			System.out.println("Manual 1");
			initPawn = new WindowInitPawn(Game.chosenSize(typeOfGame),
					nbrPlayer, typeOfGame, 1);
		} else if (initGridGame == 2) {
			System.out.println("Manual 2");
			initPawn = new WindowInitPawn(Game.chosenSize(typeOfGame),
					nbrPlayer, typeOfGame, 2);
		} else {
			System.out.println("Manual 3");
			initPawn = new WindowInitPawn(Game.chosenSize(typeOfGame),
					nbrPlayer, typeOfGame, 0);
		}
	}
}