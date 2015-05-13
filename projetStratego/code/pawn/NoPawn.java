package pawn;

/**
 * NoPawn is the class that creates a gray pawn for the initialization windows.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class NoPawn extends APawn {

	private static final long serialVersionUID = 1L;

	/**
	 * Main constructor of no_pawn pawn.
	 * 
	 * @param name
	 * 
	 */
	public NoPawn(String name) {
		setLevelPawn(0);
		setTeam(0);
		setNamePawn(name);
		setURI("/image/no_pawn/no_" + name + ".png");
	}
}
