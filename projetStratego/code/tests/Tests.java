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

}
