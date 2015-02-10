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
	
	/**
	 * return the result of the fight between this pawn and the target
	 * @param tar the pawn who is targeted by this pawn
	 * @return 0 if it's a drawn <br/> 1 if this pawn win <br/> 2 if this pawn loose
	 */
	public int attack(APawn tar){
		if (tar instanceof Bomb){
			return 1;
		}
		if(tar.getLevel()==this.levelPawn){
			return 0;
		}
		if(tar.getLevel()<this.levelPawn){
			return 1;
		}
		if(tar.getLevel()>this.levelPawn){
			return 2;
		}
		return -1;
	}
}
