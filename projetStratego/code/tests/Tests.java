package tests;

import main.*;
import pawn.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This is the test class of this project. <br/>
 * It tests the moves and the battles of the pawns, and the victory conditions.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Tests {

	private APawn bomb = new Bomb(), lieutenant = new Lieutenant(),
			major = new Major(), marshal = new Marshal(),
			marshal2 = new Marshal(), miner = new Miner(), spy = new Spy();
	private int win = 1, lost = 2, draw = 0;
	private Game game = new Game(10, 1);;
	private GridAI grid1 = new GridAI(1), grid2 = new GridAI(2);

	// Battles between two pawns.

	/**
	 * This method tests if the spy wins against the marshal.
	 */
	@Test
	public void spyVsMarshal() {
		assertTrue(win == spy.attack(marshal));
	}

	/**
	 * This method tests if the marshal wins against the spy.
	 */
	@Test
	public void marshalVsSpy() {
		assertTrue(win == marshal.attack(spy));
	}

	/**
	 * This method tests if the miner clears of mines.
	 */
	@Test
	public void minerVsBomb() {
		assertTrue(win == miner.attack(bomb));
	}

	/**
	 * This method tests if the marshal blows up against mines.
	 */
	@Test
	public void marshalVsBomb() {
		assertTrue(lost == marshal.attack(bomb));
	}

	/**
	 * This method tests if two marshals are on par.
	 */
	@Test
	public void marshalVsMarshal() {
		assertTrue(draw == marshal.attack(marshal2));
	}

	/**
	 * This method tests if the major wins against the lieutenant.
	 */
	@Test
	public void majorVsLieutenant() {
		assertTrue(win == major.attack(lieutenant));
	}

	// Victory.

	/**
	 * This method tests what happens when a team loses its flag.
	 */
	@Test
	public void blueFlagLost() {
		game.placeTeam(grid1.getGrid(), 1);
		game.placeTeam(grid2.getGrid(), 2);
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				if ((game.getPawn(i, j) != null)
						&& (game.getPawn(i, j).getTeam() == 2)
						&& (game.getPawn(i, j).getLevel() == -6)) {
					game.set(i, j, null);
				}
			}
		}
		assertTrue(1 == game.win()); // Red team wins.
	}

	/**
	 * This method tests what happens when a team can't move.
	 */
	@Test
	public void redBlocked() {
		game.placeTeam(grid1.getGrid(), 1);
		game.placeTeam(grid2.getGrid(), 2);
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				if (i > 5) {
					game.set(i, j, null);
				}
				if (((i == 8) && (j == 9)) || ((i == 9) && (j == 8))) {
					game.set(i, j, new Bomb(1));
				} else if ((i == 9) && (j == 9)) {
					game.set(i, j, new Flag(1));
				}
			}
		}
		assertTrue(2 == game.win()); // Blue team wins.
	}

	// Moves.

	/**
	 * This method tests what happens when a pawn moves more than 5 times
	 * between two squares.
	 */
	@Test
	public void sixMovement() {
		game.placeTeam(grid1.getGrid(), 1);
		game.placeTeam(grid2.getGrid(), 2);
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				if (i > 5) {
					game.set(i, j, null);
				}
				if ((i == 7) && (j == 5)) {
					game.set(i, j, marshal);
				}
			}
		}

		for (int k = 0; k < 5; k++) { // Moves 5 times the pawn.
			marshal.move(game, (6 + (k % 2)), 5);
		}

		assertFalse(marshal.movePoss(game, 7, 5)); // marshal can't move.
	}

	public void initMovesMajor() {
		game.placeTeam(grid1.getGrid(), 1);
		game.placeTeam(grid2.getGrid(), 2);
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				if (i > 5) {
					game.set(i, j, null);
				}
				if ((i == 7) && (j == 5)) {
					game.set(i, j, major);
				}
			}
		}
	}

	/**
	 * This method tests what happens when a pawn moves more than 5 times
	 * between two squares.
	 */
	@Test
	public void threeMovesMajor() {
		initMovesMajor();
		assertFalse(major.movePoss(game, 7, 8)); // marshal can't do it.
	}

	/**
	 * This method tests what happens when a pawn moves more than 5 times
	 * between two squares.
	 */
	@Test
	public void oneMoveMajor() {
		initMovesMajor();
		assertTrue(major.movePoss(game, 7, 6)); // marshal can do it.
	}
}
