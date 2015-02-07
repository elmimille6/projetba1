package pawn;

/**
* Scout is the class that creates a "scout" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Scout extends APawn {

	/**
	 * Constructor of the scout pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2
	 */
	public Scout(int team){
		setLevelPawn(2);
		setNamePawn("scout");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	
	/**
	 * Constructor of the scout pawn.
	 */
	public Scout(){
		setLevelPawn(2);
		setNamePawn("scout");
		setValue(this.levelPawn*10);
	}

}
