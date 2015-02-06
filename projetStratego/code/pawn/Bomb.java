package pawn;

public class Bomb extends APawn {

	public Bomb(int team){
		setLevelPawn(11);
		setNamePawn("bomb");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	public Bomb(){
		setLevelPawn(11);
		setNamePawn("bomb");
		setValue(this.levelPawn*10);
	}
	

}
