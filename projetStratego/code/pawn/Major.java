package pawn;

public class Major extends APawn {

	public Major(int team){
		setLevelPawn(4);
		setNamePawn("major");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	public Major(){
		setLevelPawn(7);
		setNamePawn("major");
		setValue(this.levelPawn*10);
	}

}
