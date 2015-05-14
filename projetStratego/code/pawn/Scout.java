package pawn;

import main.Game;

/**
 * Scout is the class that creates a "scout" pawn.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Scout extends APawn {

	private static final long serialVersionUID = 1L;

	/**
	 * Main Constructor of the scout pawn.
	 */
	public Scout() {
		setLevelPawn(2);
		setNbPawn(8);
		setNamePawn("scout");
		setValue(this.levelPawn * 10);
	}

	/**
	 * Constructor of the scout pawn.
	 * 
	 * @param team
	 *            Team of the pawn, must be 1 or 2.
	 */
	public Scout(int team) {
		setLevelPawn(2);
		setNbPawn(8);
		setNamePawn("scout");
		setTeam(team);
		setValue(this.levelPawn * 10);
		if (team == 1)
			setURI("/image/red/scout.png");
		if (team == 2)
			setURI("/image/blue/scout.png");
	}

	/**
	 * Tests if a move is possible.
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
	 * @return true if the move is possible, false otherwise.
	 */
	public boolean movePoss(Game grid, int x, int y) {

		if (x < 0 || y < 0 || x > grid.getLine() || y > grid.getRow()) {
			return false;
		}

		if (place.size() >= 6) {
			int[] tab1 = place.get(place.size() - 4);
			int[] tab2 = place.get(place.size() - 6);
			int[] tab3 = place.get(place.size() - 2);
			if (tab1[0] == x && x == tab2[0] && tab1[1] == y && tab2[1] == y
					&& tab3[0] == x && tab3[1] == y) {
				return false;
			}

		}

		if (x > grid.getLine()) {
			return false;
		}
		if (y > grid.getRow()) {
			return false;
		}
		APawn target = grid.getPawn(x, y);
		if (target instanceof Lake) {
			// Tests if the target isn't a lake.
			return false;
		} else if (target instanceof APawn) {
			// Tests if the target isn't a pawn of the same team.
			if (target.getTeam() == this.team) {
				return false;
			}
		} else if (x - this.posX != 0 && y - this.posY != 0) {
			// Tests if the move is in the same lane.
			return false;
		} else if (y - this.posY == 0 && x - this.posX == 0) {
			// Tests if the pawn isn't already on the target.
			return false;
		}
		if (x - this.posX != 0) {
			// Checks if the chosen lane is empty.
			if (x < this.posX) {
				for (int i = 1; posX - i != x && posX - i >= 0; i++) {
					if (grid.getPawn(posX - i, posY) != null) {
						return false;
					}
				}
			}
			if (x > this.posX) {
				for (int i = 1; posX + i != x && posX + i < grid.getLine(); i++) {
					if (grid.getPawn(posX + i, posY) != null) {
						return false;
					}
				}
			}
		}
		if (y - this.posY != 0) {
			// Checks if the chosen column is empty.
			if (y < this.posY) {
				for (int i = 1; posY - i != y && posY - i >= 0; i++) {
					if (grid.getPawn(posX, posY - i) != null) {
						return false;
					}
				}
			}
			if (y > this.posY) {
				for (int i = 1; posY + i != y && posY + i < grid.getRow(); i++) {
					if (grid.getPawn(posX, posY + i) != null) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Focus a pawn and check if move is available for him, store the result
	 * into the array 'arrow'.
	 * 
	 * @param grid
	 *            The grid of the game.
	 */
	public int[] focus(Game grid) {
		int[] arrow = { -1, -1, -1, -1, posX, posY };
		if (posX != grid.getLine()) {
			// Checks the downward move.
			if (this.movePoss(grid, posX + 1, posY)) {
				for (int i = 1; this.movePoss(grid, posX + i, posY); i++) {
					arrow[2] = i;
				}

			} else {
				arrow[2] = -1;
			}
		} else {
			arrow[2] = -1;

		}
		if (posY != grid.getRow()) {
			// Checks the rightward movement
			if (this.movePoss(grid, posX, posY + 1)) {
				for (int i = 1; this.movePoss(grid, posX, posY + i); i++) {
					arrow[1] = i;

				}
			} else {
				arrow[1] = -1;
			}
		} else {
			arrow[1] = -1;
		}

		if (posX != 0) {
			// Checks the upward move.
			if (this.movePoss(grid, posX - 1, posY)) {
				boolean test = true;
				for (int i = 1; test; i++) {
					if (posX - i > -1) {
						if (this.movePoss(grid, posX - i, posY)) {
							arrow[0] = i;
						}
					} else {
						test = false;
					}
				}
			} else {
				arrow[0] = -1;
			}
		} else {
			arrow[0] = -1;
		}
		if (posY != 0) {
			// Checks the leftward movement
			if (this.movePoss(grid, posX, posY - 1)) {
				boolean test = true;
				for (int i = 1; test; i++) {
					if (posY - i > -1) {
						if (this.movePoss(grid, posX, posY - i)) {
							arrow[3] = i;
						}
					} else {
						test = false;
					}
				}
			} else {
				arrow[3] = -1;
			}
		} else {
			arrow[3] = -1;
		}
		return arrow;
	}
}
