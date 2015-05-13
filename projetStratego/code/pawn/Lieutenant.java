package pawn;

/**
 * Lieutenant is the class that creates a "lieutenant" pawn.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Lieutenant extends APawn {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main constructor of the lieutenant pawn.
	 */
	public Lieutenant() {
		setLevelPawn(5);
		setNbPawn(4);
		setNamePawn("lieutenant");
		setValue(this.levelPawn * 10);
	}

	/**
	 * Constructor of the lieutenant pawn.
	 * 
	 * @param team
	 *            Team of the pawn, must be 1 or 2.
	 */
	public Lieutenant(int team) {
		setLevelPawn(5);
		setNbPawn(4);
		setNamePawn("lieutenant");
		setTeam(team);
		setValue(this.levelPawn * 10);
		if (team == 1)
			setURI("/image/red/lieutenant.png");
		if (team == 2)
			setURI("/image/blue/lieutenant.png");
	}

}
