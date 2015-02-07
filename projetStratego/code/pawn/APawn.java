package pawn;
import java.util.Vector;

/**
* This class creates a "pawn" object.
*
* @author CAREDDA Giuliano, DUCOBU Alexandre
*/
public abstract class APawn implements IPawn{

	protected int levelPawn;
	protected String namePawn;
	protected int team=0;
	protected int value;
	
	public String toString(){
		return String.valueOf(levelPawn);
	}
	
	protected void setValue(int valuePawn){
		this.value=valuePawn;
	}
	
	/**
	 * Set the level of the pawn.
	 * 
	 * @param levelPawn
	 *    The level of the pawn, must be between 0 and 12  
	 */
	protected void setLevelPawn(int levelPawn){
		this.levelPawn=levelPawn;
	}
	
	/**
	 * Set the name of the pawn.
	 * 
	 * @param namePawn
	 *    The name of the pawn
	 */
	protected void setNamePawn(String namePawn){
		this.namePawn=namePawn;
	}
	
	/**
	 * Set the team of the pawn.
	 * 
	 * @param team
	 *    The number of the team, 0 for neutral, 1 for red, 2 for blue
	 */
	protected void setTeam(int team){
		this.team=team;
	}
	
	/**
	 * Tell if the pawn can move.
	 * 
	 * @return
	 *    A boolean: true or false
	 */
	public boolean canMove(){
		return true;
	}
	
	/**
	 * Make a vector of the 40 pawn in a team at the begin of the game.
	 * 
	 * @return
	 *    The vector of 40 pawn
	 */
	public static Vector<APawn> createTeam(){
		Vector<APawn> listPawn = new Vector<APawn>();
		for (int i=0;i<6;i++){
			listPawn.add(new Bomb());
		}
		for (int i=0;i<8;i++){
			listPawn.add(new Scout());
		}
		for (int i=0;i<5;i++){
			listPawn.add(new Miner());
		}
		for (int i=0;i<4;i++){
			listPawn.add(new Sergeant());
		}
		for (int i=0;i<4;i++){
			listPawn.add(new Captain());
		}
		for (int i=0;i<4;i++){
			listPawn.add(new Lieutenant());
		}
		for (int i=0;i<3;i++){
			listPawn.add(new Major());
		}
		for (int i=0;i<2;i++){
			listPawn.add(new Colonel());
		}
		listPawn.add(new Spy());
		listPawn.add(new Marshal());
		listPawn.add(new General());
		listPawn.add(new Flag());
		
		return listPawn;
	}
	
	public int getLevel(){
		return levelPawn;
	}
	
	public int getValue(){
		return value;
	}
}
