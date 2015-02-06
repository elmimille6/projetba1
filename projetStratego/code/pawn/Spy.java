package pawn;

public class Spy extends APawn {

	public Spy(int team){
		setLevelPawn(1);
		setNamePawn("spy");
		setTeam(team);
		setValue(200);
	}
	public Spy(){
		setLevelPawn(1);
		setNamePawn("spy");
		setValue(200);
	}

}
