package pawn;

/**
* Colonel is the class that creates a "colonel" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Colonel extends APawn {
	
	/**
	 * Main constructor of the colonel pawn.
	 */
	public Colonel(){
		setLevelPawn(8);
		setNamePawn("colonel");
		setValue(this.levelPawn*10);
	}

	/**
	 * Constructor of the colonel pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2.
	 */
	public Colonel(int team){
		setLevelPawn(8);
		setNamePawn("colonel");
		setTeam(team);
		setValue(this.levelPawn*10);
		if (team==1)
			setURI("/image/red/colonel.png");
		if (team==2)
			setURI("/image/blue/colonel.png");
	}
}
