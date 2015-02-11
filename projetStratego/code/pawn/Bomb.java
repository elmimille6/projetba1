package pawn;

import main.Grid;

/**
* Bomb is the class that creates a "bomb" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Bomb extends APawn {

	/**
	 * Constructor of the bomb pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2
	 */
	public Bomb(int team){
		setLevelPawn(11);
		setNamePawn("bomb");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	
	/**
	 * Constructor of the bomb pawn.
	 */
	public Bomb(){
		setLevelPawn(11);
		setNamePawn("bomb");
		setValue(this.levelPawn*10);
	}
	public boolean movePoss(Grid grid,int x, int y){
		return false;
	}
}
