package pawn;

import main.Game;

/**
 * Flag is the class that creates a "flag" pawn.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Flag extends APawn {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main constructor of the flag pawn.
	 */
	public Flag() {
		setLevelPawn(-6);
		setNbPawn(1);
		setNamePawn("flag");
		setValue(400);
	}

	/**
	 * Constructor of the flag pawn.
	 * 
	 * @param team
	 *            Team of the pawn, must be 1 or 2.
	 */
	public Flag(int team) {
		setLevelPawn(-6);
		setNbPawn(1);
		setNamePawn("flag");
		setTeam(team);
		setValue(400);
		if (team == 1)
			setURI("/image/red/flag.png");
		if (team == 2)
			setURI("/image/blue/flag.png");
	}

	/**
	 * Tells that the flag can't move.
	 * 
	 * @param grid
	 *            The grid of the game.
	 * 
	 * @param x
	 *            The abscissa of the 'flag' pawn.
	 * 
	 * @param y
	 *            The ordinate of the 'flag' pawn.
	 * 
	 * @return False
	 */
	public boolean movePoss(Game grid, int x, int y) {
		return false;
	}
}
