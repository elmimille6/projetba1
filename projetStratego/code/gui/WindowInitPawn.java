package gui;

import main.Game;
import main.GridIA;
import main.IA;

/**
 * This class creates the window for the initialization of the grid by the
 * player.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class WindowInitPawn extends WindowGame {

	private static final long serialVersionUID = 1L;
	public static Game gridPane1/* = new Game(10, 4, 0) */, game = new Game(10,
			1);
	// public GridIA gridIA1 = new GridIA(1), gridIA2 = new GridIA(2);
	public static int team = 1, nbPawn, nbPawns = 40, nbPlayer = 1;
	public static int toInit/* = 0 */, side = 1;

	public WindowInitPawn() {
	}

	public WindowInitPawn(Game game) {
		WindowInitPawn.game = game;
		gridPane1 = new Game(10, 4, 0);
		toInit = 0;
		initGame();
	}

	public WindowInitPawn(Game gridPane1, int toInit) {
		WindowInitPawn.gridPane1 = gridPane1;
		WindowInitPawn.toInit = toInit;
		game.setComplete(0);
		initGame();
	}

	public void initGame() {
		if (toInit == 2) { // New grid
			new InitWindow();
		} else if (toInit == 1) { // Modif
			if (game.getComplete() == 1) {
				int lvl = IA.getIntLvl(game.getLevel());
				game.placeTeam(new GridIA(2, lvl).getGrid(), 2);
				new WindowGame(game);
			} else if (game.getComplete() != 1) {
				new InitWindow();
			}
		} else {
			if (game.getComplete() == 2) {
				new WindowGame(game);
			} else if (game.getInitGridGame() == 0 && game.getComplete() != 1) {
				// AUTOMATICAL
				// TODO// Stratego normal or duel
				int lvl = IA.getIntLvl(game.getLevel());
				game.placeTeam(new GridIA(1, 1).getGrid(), 1);
				game.placeTeam(new GridIA(2, lvl).getGrid(), 2);
				new WindowGame(game);
			} else if (game.getComplete() == 1) {
				if (game.getInitGridGame() == 2) { // 1 player (BLUE)
					// gridPane1 = Game.chosenSize(40);
					nbPlayer = 1;
					nbPawns = 40;
					side = 2;
					team = 2;
					new InitWindow();
				} else if (game.getInitGridGame() == 1 || game.getPlayer() == 2) {
					// 1 player (RED)
					nbPlayer = 1;
					nbPawns = 40;
					side = 1;
					team = 1;
					gridPane1 = Game.chosenSize(40);
					new InitWindow();
				}
			} else if (game.getInitGridGame() == 3 && game.getComplete() == 0) {// BLUE
				// gridPane1 = Game.chosenSize(40);
				nbPlayer = 1;
				nbPawns = 40;
				side = 2;
				team = 2;
				new InitWindow();
			}
		}
	}
}