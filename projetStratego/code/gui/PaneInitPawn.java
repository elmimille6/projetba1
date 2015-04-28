package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;
import pawn.NoPawn;

/**
 * This class creates the pane for the initialization of the grid by the player.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class PaneInitPawn extends JPanel {

	private static final long serialVersionUID = 1L;
	public Image img, imgBack;
	public Game grid;

	/**
	 * Constructor of the class.
	 * 
	 * @param grid
	 *            The grid.
	 */
	public PaneInitPawn(Game grid) {
		this.grid = grid;

		try {
			java.net.URL urlBack = getClass().getResource("/image/back.jpg");
			imgBack = ImageIO.read(urlBack);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PaneInitPawn() {
		this.grid = new Game(new APawn[4][10]);
	}

	/**
	 * 
	 */
	public void paintComponent(Graphics g) {
		// dessine le quadrillage
		int nbrLigne = grid.getLine() + 1;
		int nbrCol = grid.getRow() + 1;

		
		
		g.drawImage(imgBack, 0, 0, this.getWidth(), this.getHeight(), this);

		for (int lig = 1; lig < nbrCol; lig++) {
			g.drawLine((this.getWidth() / nbrCol) * lig, 0,
					(this.getWidth() / nbrCol) * lig, this.getHeight());
		}
		for (int lig = 1; lig < nbrLigne; lig++) {
			g.drawLine(0, (this.getHeight() / nbrLigne) * lig, this.getWidth(),
					(this.getHeight() / nbrLigne) * lig);
		}

		// dessine les images des pions selon la grid
		String link;
		for (int i = 0; i < (nbrLigne); i++) {
			for (int j = 0; j < (nbrCol); j++) {
				if (grid.getPawn(i, j) != null) {
					APawn pawn = grid.getPawn(i, j);
					link = pawn.getURI();
					java.net.URL uri = getClass().getResource(link);
					try {
						img = ImageIO.read(uri);
					} catch (IOException e) {
						e.printStackTrace();
					}
					g.drawImage(img, (j * (this.getWidth() / (nbrCol))) + 10,
							(i * (this.getHeight() / (nbrLigne))) + 10,
							this.getWidth() / nbrCol - 20, this.getHeight()
									/ nbrLigne - 20, this);
					if (pawn.getSelected()
							&& !pawn.getClass().equals(NoPawn.class)) { // Border
																		// image
						g.setColor(Color.green);
						
						g.drawRect((j * (this.getWidth() / (nbrCol))) + 10,
								(i * (this.getHeight() / (nbrLigne))) + 10,
								this.getWidth() / nbrCol - 20, this.getHeight()
										/ nbrLigne - 20);

						g.setColor(Color.black);
					}
				}
			}
		}
	}

	public void setGrid(APawn[][] ngrid) {
		Game game = new Game(ngrid);
		this.grid = game;

	}
}