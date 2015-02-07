package pawn;

/**
* Colonel is the class that creates a "colonel" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Colonel extends APawn {

	/**
	 * Constructor of the colonel pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2
	 */
	public Colonel(int team){
		setLevelPawn(8);
		setNamePawn("colonel");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	
	/**
	 * Constructor of the colonel pawn.
	 */
	public Colonel(){
		setLevelPawn(8);
		setNamePawn("colonel");
		setValue(this.levelPawn*10);
	}
}
