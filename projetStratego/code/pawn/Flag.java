package pawn;

import main.Grid;

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
		if(team==1){
			setURI("/image/red/flag.png");
		}
		if(team==2){
			setURI("/image/blue/flag.png");
		}
	}
	
	/**
	 * Constructor of the flag pawn.
	 */
	public Flag(){
		setLevelPawn(12);
		setNamePawn("flag");
		setValue(400);
	}
	
	public boolean movePoss(Grid grid,int x, int y){
		return false;
	}
}
