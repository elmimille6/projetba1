package pawn;

public class Sergeant extends APawn {

	public Sergeant(int team){
		setLevelPawn(4);
		setNamePawn("sergeant");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	public Sergeant(){
		setLevelPawn(4);
		setNamePawn("sergeant");
		setValue(this.levelPawn*10);
	}

}
