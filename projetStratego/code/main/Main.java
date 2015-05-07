package main;

//import pawn.*;
import gui.MenuWindow;
import gui.WindowInitPawn;
import main.Game;

/**
 * This is the main class of this project.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Main {

	public static MenuWindow menu;
	public static int nbrPlayer, initGridGame, typeOfGame;
	public static WindowInitPawn initPawn;
	public static Game game;
	public static GridIA gridIA, gridIA2;
	public static String level;

	/**
	 * Method that launches the menu of the program.
	 *
	 * @param args
	 *            Number of arguments given.
	 */
	public static void main(String[] args) {
		menu = new MenuWindow();
//		new StratClient();
	}

	/**
	 * Launches the game or the initialization window.
	 */
	public static void play() {
		nbrPlayer = menu.getNbrPlayer();
		initGridGame = menu.getInitGridGame();
		typeOfGame = menu.getTypeOfGame();
		level = menu.lvl1;
		// if (typeOfGame == 40) {
		// System.out.println("Stratego");
		// game = new Game(10, 1);
		// } else {
		// System.out.println("Stratego Duel");
		// game = new Game(8, 1);
		// }

		if (typeOfGame == 40) {
			game = new Game(10, 1);
			if (initGridGame == 1 || initGridGame == 2) {
				GridIA gridIA = new GridIA(((initGridGame) % 2) + 1);
				game.placeTeam(gridIA.getGrid(), ((initGridGame) % 2) + 1);
				game.setComplete(1);
			}
		} /*
		 * else { game = new Game(10, 1); initGridGame = 3; }
		 */
		game.setPlayer(nbrPlayer);
		game.setInitGridGame(initGridGame);
		game.setLevel(level);
		new WindowInitPawn(game);
	}
}