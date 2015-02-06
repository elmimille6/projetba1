package pawn;

public class Lieutenant extends APawn {

	public Lieutenant(int team){
		setLevelPawn(5);
		setNamePawn("lieutenant");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	public Lieutenant(){
		setLevelPawn(5);
		setNamePawn("lieutenant");
		setValue(this.levelPawn*10);
	}

}
