package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;
import util.Dic;

/**
 * panel which display the number of the pawn in the game of each team
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 *
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
	 * main constructor of the object
	 * 
	 * @param dic
	 *            dic object of the start team
	 * @param game
	 *            game object of the current game
	 * @param side
	 *            which team to count the pawn
	 */
	public PaneGamePawn(Dic dic, Game game, int side) {
		this.setLayout(new GridLayout(15, 1));
		startTeam = dic;
		this.side = side;
		upGame(game);

	}

	/**
	 * methods called to update the panel
	 * 
	 * @param game
	 *            the updated game object
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
	 * methods called to count the pawn
	 * 
	 * @return the dic object with the results of the count
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
	 * called when the panel is updating, display the panel
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
			JLabel label = new JLabel("  " + ((APawn) startTeam.getObject(i)).getNamePawn() + " : " + count.get(startTeam.getObject(i)) + "/" + startTeam.get(i) + "  ");
			this.add(label);
		}
		move = false;

	}
}
