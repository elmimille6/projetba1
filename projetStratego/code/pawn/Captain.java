package pawn;

/**
* Captain is the class that creates a "captain" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Captain extends APawn {
	
	/**
	 * Constructor of the captain pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2
	 */
	public Captain(int team){
		setLevelPawn(6);
		setNamePawn("captain");
		setTeam(team);
		setValue(this.levelPawn*10);
		setURI("/image/red/captain.png");
		if(team==1){
			setURI("/image/red/captain.png");
		}
		else{
			setURI("/image/blue/captain.png");
		}
	}
	
	/**
	 * Constructor of the captain pawn.
	 */
	public Captain(){
		setLevelPawn(6);
		setNamePawn("captain");
		setValue(this.levelPawn*10);
	}
}
