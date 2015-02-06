package pawn;

public class Colonel extends APawn {

	public Colonel(int team){
		setLevelPawn(8);
		setNamePawn("colonel");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	public Colonel(){
		setLevelPawn(8);
		setNamePawn("colonel");
		setValue(this.levelPawn*10);
	}
}
