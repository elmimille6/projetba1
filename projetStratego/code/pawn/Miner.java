package pawn;

public class Miner extends APawn {

	public Miner(int team){
		setLevelPawn(3);
		setNamePawn("miner");
		setTeam(team);
		setValue(this.levelPawn*10);
	}
	public Miner(){
		setLevelPawn(3);
		setNamePawn("miner");
		setValue(this.levelPawn*10);
	}
}
