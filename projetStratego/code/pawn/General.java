package pawn;

/**
 * General is the class that creates a "general" pawn.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class General extends APawn {

	/**
	 * Main constructor of the general pawn.
	 */
	public General() {
		setLevelPawn(9);
		setNbPawn(1);
		setNamePawn("general");
		setValue(this.levelPawn * 10);
	}

	/**
	 * Constructor of the general pawn.
	 * 
	 * @param team
	 *            Team of the pawn, must be 1 or 2.
	 */
	public General(int team) {
		setLevelPawn(9);
		setNbPawn(1);
		setNamePawn("general");
		setTeam(team);
		setValue(this.levelPawn * 10);
		if (team == 1)
			setURI("/image/red/general.png");
		if (team == 2)
			setURI("/image/blue/general.png");
	}

}
