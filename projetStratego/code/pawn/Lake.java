package pawn;

import main.Grid;

/**
* Lake is the class that creates a "lake" pawn.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public class Lake extends APawn{

	/**
	 * Main constructor of the lake pawn.
	 */
	public Lake(){
		setLevelPawn(0);
		setTeam(0);
		setNamePawn("lake");
		setURI("/image/lake.png");
	}
	
	public boolean movePoss(Grid grid,int x, int y){
		return false;
	}
}
