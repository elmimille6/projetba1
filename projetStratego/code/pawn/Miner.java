package pawn;

/**
* Miner is the class that creates a "miner" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Miner extends APawn {

	/**
	 * Constructor of the miner pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2
	 */
	public Miner(int team){
		setLevelPawn(3);
		setNamePawn("miner");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	
	/**
	 * Constructor of the miner pawn.
	 */
	public Miner(){
		setLevelPawn(3);
		setNamePawn("miner");
		setValue(this.levelPawn*10);
	}
}
