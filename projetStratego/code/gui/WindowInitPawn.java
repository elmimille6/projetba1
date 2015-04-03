package gui;

import main.Game;
import main.GridIA;

/**
 * This class creates the window for the initialization of the grid by the
 * player.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class WindowInitPawn extends WindowGame {

	private static final long serialVersionUID = 1L;
	public static Game gridPane1 = new Game(10, 4, 0), game;
	// public GridIA gridIA, gridIA2;
	public static int team = 1, nbPawn, nbPawns = 40, nbPlayer = 1;
	public static int toInit = 0, side = 1;

	public WindowInitPawn() {
	}

	public WindowInitPawn(Game game) {
		WindowInitPawn.game = game;
		initGame();
	}

	public WindowInitPawn(Game gridPane1, int toInit) {
		WindowInitPawn.gridPane1 = gridPane1;
		WindowInitPawn.toInit = toInit;
		initGame();
	}

	public void initGame() {
		if (toInit == 2) {
			System.out.println("New grid");
			new InitWindow();
		} else if (toInit == 1) {
			System.out.println("Modif");
			new InitWindow();
		} else {
			if (game.getComplete() == 2) {
				new WindowGame(game);
			} else if (game.getInitGridGame() == 0 && game.getComplete() != 1) {
				System.out.println("AUTOMATICAL");
				// TODO// Stratego normal or duel
				GridIA gridIA = new GridIA(1);
				GridIA gridIA2 = new GridIA(2);
				game.placeTeam(gridIA.getGrid(), 1);
				game.placeTeam(gridIA2.getGrid(), 2);
				new WindowGame(game);
			} else if (game.getComplete() == 1) {
				if (game.getInitGridGame() == 2) {
					System.out.println("1 player (BLUE)");
					gridPane1 = Game.chosenSize(40);
					nbPlayer = 1;
					nbPawns = 40;
					side = 2;
					team = 2;
					new InitWindow();
				} else if (game.getInitGridGame() == 1 || game.getPlayer() == 2) {
					System.out.println("1 player (RED)");
					gridPane1 = Game.chosenSize(40);
					new InitWindow();
				}
			} else if (game.getInitGridGame() == 3 && game.getComplete() == 0) {// BLUE
				System.out.println("BLUE");
				gridPane1 = Game.chosenSize(40);
				nbPlayer = 1;
				nbPawns = 40;
				side = 2;
				team = 2;
				new InitWindow();
			}
		}
	}
}