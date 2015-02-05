package pawn;

public abstract class APawn implements IPawn{

	protected int levelPawn;
	protected String namePawn;
	
	public String toString(){
		return namePawn+" "+String.valueOf(levelPawn);
	}
	
	protected void setLevelPawn(int levelPawn){
		this.levelPawn=levelPawn;
	}
	protected void setNamePawn(String namePawn){
		this.namePawn=namePawn;
	}
}
