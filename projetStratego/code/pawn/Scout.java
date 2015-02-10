package pawn;

import main.Grid;

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
	/**
	 * test if a move is possible
	 * @param grid the grid of the game
	 * @param x the coord to move
	 * @param y the coord to move
	 * @return the grid after the moving
	 */
	public boolean movePoss(Grid grid,int x, int y){
		APawn target = grid.get(x, y); 
		if (target instanceof Lake){ //test if the target isnt a lake
			return false;
		}
		else if (target instanceof APawn){ //test if the target isnt a pawn of the same team
			if (target.getTeam()==this.team){
				return false;
			}
		}
		else if (x-this.posX!=0 && y-this.posY!=0){ //test if the move is in the same lane
			return false;
		}
		else if (y-this.posY==0 && x-this.posX==0){ //test if the pawn isnt already on the target
			return false;
		}
		return true;
	}
}
