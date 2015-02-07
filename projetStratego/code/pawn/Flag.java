package pawn;

/**
* Flag is the class that creates a "flag" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Flag extends APawn {

	/**
	 * Constructor of the flag pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2
	 */
	public Flag(int team){
		setLevelPawn(12);
		setNamePawn("flag");
		setTeam(team);
		setValue(400);
	}
	
	/**
	 * Constructor of the flag pawn.
	 */
	public Flag(){
		setLevelPawn(12);
		setNamePawn("flag");
		setValue(400);
	}
}
