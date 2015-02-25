package pawn;

import main.Game;

/**
 * Bomb is the class that creates a "bomb" pawn.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Bomb extends APawn {

	/**
	 * Main constructor of the bomb pawn.
	 */
	public Bomb() {
		setLevelPawn(11);
		setNamePawn("bomb");
		setValue(this.levelPawn * 10);
	}

	/**
	 * Constructor of the bomb pawn.
	 * 
	 * @param team
	 *            Team of the pawn, must be 1 or 2.
	 */
	public Bomb(int team) {
		setLevelPawn(11);
		setNamePawn("bomb");
		setTeam(team);
		setValue(this.levelPawn * 10);
		if (team == 1)
			setURI("/image/red/bomb.png");
		if (team == 2)
			setURI("/image/blue/bomb.png");
		// setURI("/image/red/bomb.png");
	}

	/**
	 * Tells that the bomb can't move.
	 * 
	 * @param grid
	 *            The grid of the game.
	 * 
	 * @param x
	 *            The abscissa of the 'bomb' pawn.
	 * 
	 * @param y
	 *            The ordinate of the 'bomb' pawn.
	 * 
	 * @return False
	 */
	public boolean movePoss(Game grid, int x, int y) {
		return false;
	}
}
