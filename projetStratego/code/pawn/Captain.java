package pawn;

public class Captain extends APawn {
	
	public Captain(int team){
		setLevelPawn(6);
		setNamePawn("captain");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	public Captain(){
		setLevelPawn(6);
		setNamePawn("captain");
		setValue(this.levelPawn*10);
	}
}
