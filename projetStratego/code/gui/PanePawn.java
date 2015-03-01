package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.GridPawn;
import pawn.APawn;

/**
 * This class
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class PanePawn extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Image img, imgBack;
	public GridPawn grid;
	public int[] arrow = { -1, -1, -1, -1, -1, -1 };

	public PanePawn(GridPawn grid) {
		this.grid = grid;

		try {
			java.net.URL urlBack = getClass().getResource("/image/back.jpg");
			imgBack = ImageIO.read(urlBack);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {

		// dessine le quadrillage
		int nbrLigne = grid.getLine() + 1;
		int nbrCol = grid.getRow() + 1;

		// System.out.println(nbrLigne+" ligne");
		// System.out.println(nbrCol+" col");
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
		int i;
		int j;
		for (i = 0; i < (nbrLigne); i++) {
			for (j = 0; j < (nbrCol); j++) {
				if (grid.get(i, j) != null) {
					APawn pawn = grid.get(i, j);
					String link = pawn.getURI();
					java.net.URL uri = getClass().getResource(link);
					// System.out.println(link);
					// System.out.println(uri);
					try {
						img = ImageIO.read(uri);
					} catch (IOException e) {
						e.printStackTrace();
					}
					g.drawImage(img, (j * (this.getWidth() / (nbrCol))) + 10,
							(i * (this.getHeight() / (nbrLigne))) + 10,
							this.getWidth() / nbrCol - 20, this.getHeight()
									/ nbrLigne - 20, this);
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @param nouvGrid
	 * 
	 */
	public void recupGrid(GridPawn nouvGrid) {
		grid = nouvGrid;
	}

	// public void recupGame(AGame type){
	// jeu = type;
	// }
}