package pawn;

import main.Grid;

/**
 * Scout is the class that creates a "scout" pawn.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Scout extends APawn {

	/**
	 * Main Constructor of the scout pawn.
	 */
	public Scout() {
		setLevelPawn(2);
		setNamePawn("scout");
		setValue(this.levelPawn * 10);
	}

	/**
	 * Constructor of the scout pawn.
	 * 
	 * @param team
	 *    Team of the pawn, must be 1 or 2.
	 */
	public Scout(int team) {
		setLevelPawn(2);
		setNamePawn("scout");
		setTeam(team);
		setValue(this.levelPawn * 10);
		if (team==1)
			setURI("/image/red/scout.png");
		if (team==2)
			setURI("/image/blue/scout.png");
	}

	/**
	 * Test if a move is possible.
	 * 
	 * @param grid
	 *    The grid of the game.
	 *    
	 * @param x
	 *    The abscissa of the object.
	 *    
	 * @param y
	 *    The ordinate of the object.
	 *    
	 * @return
	 *    The grid after the moving.
	 */
	public boolean movePoss(Grid grid, int x, int y) {
		APawn target = grid.get(x, y);
		if (target instanceof Lake) { // test if the target isnt a lake
			return false;
		} else if (target instanceof APawn) { // test if the target isnt a pawn of the same team
			if (target.getTeam() == this.team) {
				return false;
			}
		} else if (x - this.posX != 0 && y - this.posY != 0) { // test if the move is in the same lane
			return false;
		} else if (y - this.posY == 0 && x - this.posX == 0) { // test if the pawn isnt already on the target
			return false;
		} else if (x - this.posX != 0) {//check if the lane X is empty
			for (int i = 1; i < Math.abs(x - this.posX); i++) {
				if (x < posX) {
					if (grid.get(posX - i, posY) != null) {
						return false;
					}
				}
				if (x > posX) {
					if (grid.get(posX + i, posY) != null) {
						return false;
					}
				}
			}
		}
		else if (y - this.posY != 0) { //check if the lane X is empty
			for (int i = 1; i < Math.abs(y - this.posY); i++) {
				if (y < posY) {
					if (grid.get(posX , posY-i) != null) {
						return false;
					}
				}
				if (y > posY) {
					if (grid.get(posX , posY+ i) != null) {
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 * Focus a pawn and check if move is available for him, store the result into the array 'arrow'.
	 * 
	 * @param grid
	 *    The grid of the game.
	 */
	public int[] focus(Grid grid) { //TODO retourner le nombre de case qu il peut avancer (avec des for)
		int[] arrow={-1,-1,-1,-1,posX,posY};
//		System.out.println(focus);
		if (posX != grid.getLine()) {// check down move
//			System.out.println("D");
			if (this.movePoss(grid, posX + 1, posY)) {
				arrow[2] = 1;
//				System.out.println("ok");
			} else {
				arrow[2] = -1;
			}
		} else {
			arrow[2] = -1;
			
		}
		if (posY != grid.getRow()) {// check right move
			System.out.println("R");
			if (this.movePoss(grid, posX, posY + 1)) {
				arrow[1] = 1;
				System.out.println("ok");
			} else {
				arrow[1] = -1;
			}
		} else {
			arrow[1] = -1;
		}
		if (posX != 0) {// check up move
//			System.out.println("U");
			if (this.movePoss(grid, posX - 1, posY)) {
				arrow[0] = 1;
//				System.out.println("ok");
			} else {
				arrow[0] = -1;
			}
		} else {
			arrow[0] = -1;
		}
		if (posY != 0) {// check left move
			System.out.println("L");
			if (this.movePoss(grid, posX, posY - 1)) {
				arrow[3] = 1;
				System.out.println("ok");
			} else {
				arrow[3] = -1;
			}
		} else {
			arrow[3] = -1;
		}
		for (int i =0;i<arrow.length;i++){
				System.out.print(arrow[i]+"    ");
		}
		return arrow;
	}
}
