package pawn;

public class Scout extends APawn {

	public Scout(int team){
		setLevelPawn(2);
		setNamePawn("scout");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	public Scout(){
		setLevelPawn(2);
		setNamePawn("scout");
		setValue(this.levelPawn*10);
	}

}
