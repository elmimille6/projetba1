package pawn;

/**
* Spy is the class that creates a "spy" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Spy extends APawn {

	/**
	 * Constructor of the spy pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2
	 */
	public Spy(int team){
		setLevelPawn(1);
		setNamePawn("spy");
		setTeam(team);
		setValue(200);
	}
	
	/**
	 * Constructor of the spy pawn.
	 */
	public Spy(){
		setLevelPawn(1);
		setNamePawn("spy");
		setValue(200);
	}

}
