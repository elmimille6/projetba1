package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;
import util.Dic;

/**
 * This class creates a panel that displays the number of pawns in the game of
 * each team.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class PaneGamePawn extends JPanel {

	private static final long serialVersionUID = 1L;
	Dic startTeam;
	Game grid;
	int side, width, height;
	Dic count;
	JPanel pane;
	boolean move;

	/**
	 * Main constructor of the class.
	 * 
	 * @param dic
	 *            Dic object of the start team.
	 * @param game
	 *            Game object of the current game.
	 * @param side
	 *            Tells which team's pawns are to be counted.
	 */
	public PaneGamePawn(Dic dic, Game game, int side) {
		this.setLayout(new GridLayout(15, 1));
		startTeam = dic;
		this.side = side;
		upGame(game);

	}

	/**
	 * This method's called to update the panel.
	 * 
	 * @param game
	 *            The updated game object.
	 */
	public void upGame(Game game) {
		move = true;
		grid = game;
		count = count();
		this.removeAll();
		this.display();
		this.validate();
	}

	/**
	 * This method's called to count the pawns.
	 * 
	 * @return The Dic object containing the results of the calculation.
	 */
	public Dic count() {
		Dic dic = new Dic();
		for (int i = 0; i <= grid.getLine(); i++) {
			for (int j = 0; j <= grid.getRow(); j++) {
				if (grid.getPawn(i, j) instanceof APawn) {
					APawn pawn = grid.getPawn(i, j);
					if (pawn.getTeam() == side) {
						if (dic.isIn(pawn)) {
							dic.set(pawn, dic.get(pawn) + 1);
						} else {
							dic.add(pawn, 1);
						}
					}
				}
			}
		}
		return dic;
	}

	/**
	 * Called when the panel is updating, displays the panel.
	 */
	public void display() {
		JLabel lTeam;
		if (side == 1) {
			lTeam = new JLabel("  Equipe rouge");
		} else {
			lTeam = new JLabel("  Equipe bleue");
		}
		this.add(lTeam);
		int g = startTeam.getSize();
		for (int i = 0; i < g; i++) {
			JLabel label = new JLabel("  "
					+ ((APawn) startTeam.getObject(i)).getNamePawn() + " : "
					+ count.get(startTeam.getObject(i)) + "/"
					+ startTeam.get(i) + "  ");
			this.add(label);
		}
		move = false;
	}
}
