package pawn;

public class Marshal extends APawn {

	public Marshal() {
		setLevelPawn(10);
		setNamePawn("marshal");
		setValue(this.levelPawn*10);
	}
	public Marshal(int team){
		setLevelPawn(10);
		setNamePawn("marshal");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	
}
