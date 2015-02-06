package pawn;

public class General extends APawn {

	public General() {
		setLevelPawn(9);
		setNamePawn("general");
		setValue(this.levelPawn*10);
	}
	public General(int team){
		setLevelPawn(9);
		setNamePawn("general");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	

}
