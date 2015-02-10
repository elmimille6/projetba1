package pawn;

/**
* Sergeant is the class that creates a "sergeant" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Sergeant extends APawn {

	/**
	 * Constructor of the sergeant pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2
	 */
	public Sergeant(int team){
		setLevelPawn(4);
		setNamePawn("sergeant");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	
	/**
	 * Constructor of the sergeant pawn.
	 */
	public Sergeant(){
		setLevelPawn(4);
		setNamePawn("sergeant");
		setValue(this.levelPawn*10);
	}

}