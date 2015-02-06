package pawn;

public abstract class APawn implements IPawn{

	protected int levelPawn;
	protected String namePawn;
	protected int team=0; 
	
	public String toString(){
		return String.valueOf(levelPawn);
	}
	/**
	 * set the level of the pawn
	 * @param levelPawn level of the pawn, must be between 0 and 12  
	 */
	protected void setLevelPawn(int levelPawn){
		this.levelPawn=levelPawn;
	}
	/**
	 * set the name of the pawn
	 * @param namePawn name of the pawn
	 */
	protected void setNamePawn(String namePawn){
		this.namePawn=namePawn;
	}
	/**
	 * set the team of the pawn
	 * @param team number of the team, 0 for neutral, 1 for red, 2 for blue
	 */
	protected void setTeam(int team){
		this.team=team;
	}
	/**
	 * tell if the pawn can move
	 * @return true or false
	 */
	public boolean canMove(){
		return true;
	}
}
