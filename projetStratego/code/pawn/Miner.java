package pawn;

/**
 * Miner is the class that creates a "miner" pawn.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Miner extends APawn {

	/**
	 * Main constructor of the miner pawn.
	 */
	public Miner() {
		setLevelPawn(3);
		setNbPawn(5);
		setNamePawn("miner");
		setValue(this.levelPawn * 10);
	}

	/**
	 * Constructor of the miner pawn.
	 * 
	 * @param team
	 *            Team of the pawn, must be 1 or 2.
	 */
	public Miner(int team) {
		setLevelPawn(3);
		setNbPawn(5);
		setNamePawn("miner");
		setTeam(team);
		setValue(this.levelPawn * 10);
		if (team == 1)
			setURI("/image/red/miner.png");
		if (team == 2)
			setURI("/image/blue/miner.png");
	}

	/**
	 * Returns the result of the fight between this pawn and the target.
	 * 
	 * @param tar
	 *            The pawn who is targeted by this pawn.
	 * 
	 * @return 0 if it's a drawn <br/>
	 *         1 if this pawn win <br/>
	 *         2 if this pawn loose <br/>
	 *         3 if tar is the flag.
	 */
	public int attack(APawn tar) {
		if (tar.getLevel() == 12)
			return 3;
		if (tar instanceof Bomb) {
			return 1;
		}
		if (tar.getLevel() == this.levelPawn) {
			return 0;
		}
		if (tar.getLevel() < this.levelPawn) {
			return 1;
		}
		if (tar.getLevel() > this.levelPawn) {
			return 2;
		}
		return -1;
	}
}
