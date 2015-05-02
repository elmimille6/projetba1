package tests;

import main.*;
import pawn.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author alexandre
 *
 */
public class Tests {

	private APawn spy = new Spy(), marshal = new Marshal(),
			marshal2 = new Marshal(), miner = new Miner(), bomb = new Bomb();
	private int win = 1, lost = 2, draw = 0;
	private Game game = new Game(10, 1);;
	private GridIA grid1 = new GridIA(1), grid2 = new GridIA(2);

	/**
	 * This method tests if the spy wins against the marshal.
	 */
	@Test
	public void spyVMarshal() {
		assertTrue(win == spy.attack(marshal));
	}

	/**
	 * This method tests if the marshal wins against the spy.
	 */
	@Test
	public void marshalVSpy() {
		assertTrue(win == marshal.attack(spy));
	}

	/**
	 * This method tests if the miner clears of mines.
	 */
	@Test
	public void minerVBomb() {
		assertTrue(win == miner.attack(bomb));
	}

	/**
	 * This method tests if the marshal blows up against mines.
	 */
	@Test
	public void marshalVBomb() {
		assertTrue(lost == marshal.attack(bomb));
	}

	/**
	 * This method tests if two marshals are on par.
	 */
	@Test
	public void marshalVMarshal() {
		assertTrue(draw == marshal.attack(marshal2));
	}

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
}
