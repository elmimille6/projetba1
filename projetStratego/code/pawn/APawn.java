package pawn;

import java.util.Vector;

import main.Grid;

/**
 * This class creates a "pawn" object.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public abstract class APawn {

	protected int levelPawn;
	protected String namePawn;
	protected int team = 0;
	protected int value;
	public int posX;
	public int posY;
	protected String URI;

	/**
	 * Returns a string representation of a 'APawn' object.
	 */
	public String toString() {
		if (levelPawn < 10) {
			return String.valueOf(levelPawn) + " ";
		}
		return String.valueOf(levelPawn);
	}

	/**
	 * Set the position on the grid of the pawn.
	 * 
	 * @param posX
	 *            The abscissa of the object.
	 * 
	 * @param posY
	 *            The ordinate of the object.
	 */
	public void setPos(int posX, int posY) {
		this.posY = posY;
		this.posX = posX;
	}

	/**
	 * Set the value of the pawn.
	 * 
	 * @param valuePawn
	 *            The value of the pawn.
	 */
	protected void setValue(int valuePawn) {
		this.value = valuePawn;
	}

	/**
	 * Set the URI of the image of the pawn.
	 * 
	 * @param URI
	 *            The URI of the image of the pawn.
	 */
	protected void setURI(String URI) {
		this.URI = URI;
	}

	/**
	 * Set the level of the pawn.
	 * 
	 * @param levelPawn
	 *            The level of the pawn, must be between 0 and 12.
	 */
	protected void setLevelPawn(int levelPawn) {
		this.levelPawn = levelPawn;
	}

	/**
	 * Set the name of the pawn.
	 * 
	 * @param namePawn
	 *            The name of the pawn.
	 */
	protected void setNamePawn(String namePawn) {
		this.namePawn = namePawn;
	}

	/**
	 * Set the team of the pawn.
	 * 
	 * @param team
	 *            The number of the team, 0 for neutral, 1 for red, 2 for blue.
	 */
	protected void setTeam(int team) {
		this.team = team;
	}

	/**
	 * Make a vector of the 40 pawn in a team at the begin of the game.
	 * 
	 * @return The vector of 40 pawn.
	 */
	public static Vector<APawn> createTeam(int team) {
		Vector<APawn> listPawn = new Vector<APawn>();

		for (int i = 0; i < 6; i++) {
			listPawn.add(new Bomb(team));
		}

		for (int i = 0; i < 8; i++) {
			listPawn.add(new Scout(team));
		}

		for (int i = 0; i < 5; i++) {
			listPawn.add(new Miner(team));
		}

		for (int i = 0; i < 4; i++) {
			listPawn.add(new Sergeant(team));
		}

		for (int i = 0; i < 4; i++) {
			listPawn.add(new Captain(team));
		}

		for (int i = 0; i < 4; i++) {
			listPawn.add(new Lieutenant(team));
		}

		for (int i = 0; i < 3; i++) {
			listPawn.add(new Major(team));
		}

		for (int i = 0; i < 2; i++) {
			listPawn.add(new Colonel(team));
		}

		listPawn.add(new Spy(team));
		listPawn.add(new Marshal(team));
		listPawn.add(new General(team));
		listPawn.add(new Flag(team));

		return listPawn;
	}

	/**
	 * Get the level of the pawn.
	 * 
	 * @return The level of the pawn.
	 */
	public int getLevel() {
		return levelPawn;
	}

	/**
	 * Get the value of the pawn to create a starter grid for AI.
	 * 
	 * @return The value of the pawn.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Get the team of the pawn.
	 * 
	 * @return The number of the team.
	 */
	public int getTeam() {
		return this.team;
	}

	/**
	 * Get the URI of the pawn.
	 * 
	 * @return The URI of the pawn.
	 */
	public String getURI() {
		return this.URI;
	}

	/**
	 * Test if a move is possible.
	 * 
	 * @param grid
	 *            The grid of the game.
	 * 
	 * @param x
	 *            The abscissa of the object.
	 * 
	 * @param y
	 *            The ordinate of the object.
	 * 
	 * @return true or false
	 */
	public boolean movePoss(Grid grid, int x, int y) {
		APawn target = grid.get(x, y);
		if (target instanceof Lake) { // test if the target isn't a lake
			System.out.println(1);
			return false;
		}
		if (target instanceof APawn) { // test if the target isn't a pawn of the
										// same team
			if (target.getTeam() == this.team) {
				return false;
			}
		}
		
		if (Math.abs(x - this.posX )> 1) { // test if the move is
															// not too long
			return false;
		}
		if (Math.abs(y - this.posY) > 1 ) { // test if the move is
															// not too long
			return false;
		}
		if (x - this.posX != 0 && y - this.posY != 0) { // test if the move is
														// in the same lane
			return false;
		}
		if (y - this.posY == 0 && x - this.posX == 0) { // test if the pawn
														// isn't already on the
														// target
			return false;
		}
		return true;
	}

	/**
	 * Return the result of the fight between this pawn and the target.
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
		if (tar.getLevel() == this.levelPawn)
			return 0;
		if (tar.getLevel() < this.levelPawn)
			return 1;
		if (tar.getLevel() > this.levelPawn)
			return 2;
		return -1;
	}

	/**
	 * Move the pawn. <br/>
	 * ! warning: be careful to test if the moving is possible BEFORE with
	 * movePoss(Grid grid, int x, int y).
	 * 
	 * @param grid
	 *            The grid of the game.
	 * 
	 * @param x
	 *            The abscissa of the object.
	 * 
	 * @param y
	 *            The ordinate of the object.
	 * 
	 * @return The grid after the move.
	 */
	public Grid move(Grid grid, int x, int y) {
		APawn tar = grid.get(x, y);
		if (tar == null) {// no pawn on the coordinates targeted
			grid.set(this.posX, this.posY, null);// delete the old coordinates
													// of the pawn
			grid.set(x, y, this);// set the new coordinates of the pawn
			return grid;
		}

		int res = this.attack(tar);// get the result of the fight
		if (res == 0) {// it's a draw
			grid.set(x, y, null);// delete the pawn targeted
			grid.set(this.posX, this.posY, null);// delete the pawn who attack
		} else if (res == 1) {// the pawn who attack win
			grid.set(this.posX, this.posY, null);// delete the old coordinates
													// of the pawn
			grid.set(x, y, this);// set the new coordinates of the pawn
		} else if (res == 2)// the pawn who attack loose
			grid.set(this.posX, this.posY, null);// delete the pawn who attack
		else if (res == 3)
			System.out.println("Victory !");
		return grid;
	}

	/**
	 * focus a pawn and check if move is available for him, store the result
	 * into the array 'arrow'
	 * 
	 * @param line
	 *            coord of the pawn
	 * @param row
	 *            coord of the pawn
	 */
	public int[] focus(Grid grid) {
		int[] arrow = { -1, -1, -1, -1, posX, posY };
		// System.out.println(focus);
		if (posX != grid.getLine()) {// check down move
		// System.out.println("D");
			if (this.movePoss(grid, posX + 1, posY)) {
				arrow[2] = 1;
				// System.out.println("ok");
			} else {
				arrow[2] = -1;
			}
		} else {
			arrow[2] = -1;

		}
		if (posY != grid.getRow()) {// check right move
//			System.out.println("R");
			if (this.movePoss(grid, posX, posY + 1)) {
				arrow[1] = 1;
//				System.out.println("ok");
			} else {
				arrow[1] = -1;
			}
		} else {
			arrow[1] = -1;
		}
		if (posX != 0) {// check up move
		// System.out.println("U");
			if (this.movePoss(grid, posX - 1, posY)) {
				arrow[0] = 1;
				// System.out.println("ok");
			} else {
				arrow[0] = -1;
			}
		} else {
			arrow[0] = -1;
		}
		if (posY != 0) {// check left move
//			System.out.println("L");
			if (this.movePoss(grid, posX, posY - 1)) {
				arrow[3] = 1;
//				System.out.println("ok");
			} else {
				arrow[3] = -1;
			}
		} else {
			arrow[3] = -1;
		}
		for (int i = 0; i < arrow.length; i++) {
//			System.out.print(arrow[i] + "    ");
		}
		return arrow;
	}
}
