package pawn;

public class Flag extends APawn {

	public Flag(int team){
		setLevelPawn(12);
		setNamePawn("flag");
		setTeam(team);
		setValue(400);
	}
	public Flag(){
		setLevelPawn(12);
		setNamePawn("flag");
		setValue(400);
	}
}
