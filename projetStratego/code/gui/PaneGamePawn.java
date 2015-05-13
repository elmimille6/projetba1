package gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;
import util.Dic;

public class PaneGamePawn extends JPanel {

	private static final long serialVersionUID = 1L;
	Dic startTeam;
	Game grid;
	int side, width, height;
	Dic count;
	JPanel pane;
	boolean move;

	/**
	 * 
	 * @param dic
	 * @param game
	 * @param side
	 */
	public PaneGamePawn(Dic dic, Game game, int side) {
		this.setLayout(new GridLayout(15, 1));
		startTeam = dic;
		this.side = side;
		upGame(game);

	}

	/**
	 * 
	 * @param width
	 * @param heigth
	 */
	public void fixSize(int width, int heigth) {
		this.width = width;
		this.height = heigth;
	}

	/**
	 * 
	 * @param game
	 */
	public void upGame(Game game) {
		move = true;
		grid = game;
		count = count();
		this.removeAll();
		this.affichage();
		this.validate();
	}

	/**
	 * 
	 * @return
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
	 * 
	 */
	public void affichage() {
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
