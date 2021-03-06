package pawn;

/**
 * Major is the class that creates a "major" pawn.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Major extends APawn {

	private static final long serialVersionUID = 1L;

	/**
	 * Main constructor of the major pawn.
	 */
	public Major() {
		setLevelPawn(7);
		setNbPawn(3);
		setNamePawn("major");
		setValue(this.levelPawn * 10);
	}

	/**
	 * Constructor of the major pawn.
	 * 
	 * @param team
	 *            Team of the pawn, must be 1 or 2.
	 */
	public Major(int team) {
		setLevelPawn(7);
		setNbPawn(3);
		setNamePawn("major");
		setTeam(team);
		setValue(this.levelPawn * 10);
		if (team == 1)
			setURI("/image/red/major.png");
		if (team == 2)
			setURI("/image/blue/major.png");
	}

}
