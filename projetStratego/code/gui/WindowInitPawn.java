package gui;

import main.Game;
import main.GridAI;
import main.AI;

/**
 * This class choose the right action for the initialization of the grid by the
 * player.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class WindowInitPawn extends WindowGame {

	private static final long serialVersionUID = 1L;
	public static Game gridPane1, game = new Game(10, 1);
	public static int team = 1, nbPawn, nbPawns = 40, nbPlayer = 1;
	public static int toInit, side = 1;

	/**
	 * Main constructor of the class.
	 */
	public WindowInitPawn() {
	}

	/**
	 * Constructor of the class when a 'game' object is given.
	 * 
	 * @param game
	 *            The 'game' object of the game.
	 */
	public WindowInitPawn(Game game) {
		WindowInitPawn.game = game;
		gridPane1 = new Game(10, 4, 0);
		toInit = 0;
		initGame();
	}

	/**
	 * Constructor of the class when the user loads a grid or creates an
	 * automatic grid.
	 * 
	 * @param gridPane1
	 *            The panel where the loaded or automatic grid is placed.
	 * 
	 * @param toInit
	 *            This value says what the 'initGame' method has to do:<br/>
	 *            if toInit = 0, the grid is complete and there's nothing to do,<br/>
	 *            if toInit = 1, the grid has to be modify,<br/>
	 *            if toInit = 2, a new grid has to be created.
	 */
	public WindowInitPawn(Game gridPane1, int toInit) {
		WindowInitPawn.gridPane1 = gridPane1;
		WindowInitPawn.toInit = toInit;
		game.setComplete(0);
		initGame();
	}

	/**
	 * This method allows the user to create or modify grid for each team of the
	 * game, and launches also the game when the grid is complete.
	 */
	public void initGame() {
		if (toInit == 2) {
			// New grid
			new InitWindow();
		} else if (toInit == 1) {
			// Modif
			if (game.getComplete() == 1) {
				int lvl = AI.getIntLevel(game.getLevel());
				game.placeTeam(new GridAI(2, lvl).getGrid(), 2);
				new WindowGame(game);
			} else if (game.getComplete() != 1) {
				new InitWindow();
			}
		} else {
			if (game.getComplete() == 2) {
				new WindowGame(game);
			} else if (game.getInitGridGame() == 0 && game.getComplete() != 1) {
				// AUTOMATICAL
				int lvl = AI.getIntLevel(game.getLevel());
				game.placeTeam(new GridAI(1, 1).getGrid(), 1);
				game.placeTeam(new GridAI(2, lvl).getGrid(), 2);
				new WindowGame(game);
			} else if (game.getComplete() == 1) {
				if (game.getInitGridGame() == 2) {
					// 1 player (BLUE)
					nbPlayer = 1;
					nbPawns = 40;
					side = 2;
					team = 2;
					new InitWindow();
				} else if (game.getInitGridGame() == 1 || game.getPlayer() == 2) {
					// 1 player (RED)
					nbPlayer = 1;
					nbPawns = game.getNbPawns();
					side = 1;
					team = 1;
					gridPane1 = Game.chosenSize(40);
					new InitWindow();
				}
			} else if (game.getInitGridGame() == 3 && game.getComplete() == 0) {
				// BLUE
				nbPlayer = 1;
				nbPawns = game.getNbPawns();
				side = 2;
				team = 2;
				new InitWindow();
			}
		}
	}
}