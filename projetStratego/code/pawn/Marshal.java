package pawn;

/**
 * Marshal is the class that creates a "marshal" pawn.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Marshal extends APawn {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main constructor of the marshal pawn.
	 */
	public Marshal() {
		setLevelPawn(10);
		setNbPawn(1);
		setNamePawn("marshal");
		setValue(this.levelPawn * 10);
	}

	/**
	 * Constructor of the marshal pawn.
	 * 
	 * @param team
	 *            Team of the pawn, must be 1 or 2.
	 */
	public Marshal(int team) {
		setLevelPawn(10);
		setNbPawn(1);
		setNamePawn("marshal");
		setTeam(team);
		setValue(this.levelPawn * 10);
		if (team == 1)
			setURI("/image/red/marshal.png");
		if (team == 2)
			setURI("/image/blue/marshal.png");
	}

}
