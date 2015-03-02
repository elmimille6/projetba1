package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;

/**
 * This class creates the window of the game.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class PaneGame extends JPanel {

	private static final long serialVersionUID = 1L;
	public Image img, imgBack;
	public Game grid;
	public int[] arrow = { -1, -1, -1, -1, -1, -1 };

	/**
	 * 
	 * 
	 * @param grid
	 *            The grid.
	 */
	public PaneGame(Game grid) {
		this.grid = grid;

	}

	/**
	 * 
	 */
	public void paintComponent(Graphics g) {

		try {
			java.net.URL urlBack = getClass().getResource("/image/back.jpg");
			// java.net.URL urlO = getClass().getResource(jeu.recupImg1());
			// java.net.URL urlX = getClass().getResource(jeu.recupImg2());
			// java.net.URL urlOOO =
			// getClass().getResource("/image/oth/OOO.png");
			// imgO = ImageIO.read(urlO);
			// imgX = ImageIO.read(urlX);
			// imgOOO = ImageIO.read(urlOOO);
			imgBack = ImageIO.read(urlBack);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// dessine le quadrillage
		int nbrLigne = grid.getLine() + 1;
		int nbrCol = grid.getRow() + 1;
		int view = grid.getView();
		// System.out.println(nbrLigne+" ligne");
		// System.out.println(nbrCol+" col");
		// dessine l image de fond
		g.drawImage(imgBack, 0, 0, this.getWidth(), this.getHeight(), this);
		// dessine les lignes
		for (int lig = 1; lig < nbrCol; lig++) {
			g.drawLine((this.getWidth() / nbrCol) * lig, 0,
					(this.getWidth() / nbrCol) * lig, this.getHeight());
		}
		for (int lig = 1; lig < nbrLigne; lig++) {
			g.drawLine(0, (this.getHeight() / nbrLigne) * lig, this.getWidth(),
					(this.getHeight() / nbrLigne) * lig);
		}

		// dessine les images des pions selon la grid
		String link = "/image/red/hide.png";
		for (int i = 0; i < (nbrLigne); i++) {
			for (int j = 0; j < (nbrCol); j++) {
				if (grid.get(i, j) != null) {

					APawn pawn = grid.get(i, j);
					if (pawn.getTeam() == view || view == 0
							|| pawn.getTeam() == 0) {
						link = pawn.getURI();
					} else {
						if (pawn.getTeam() == 1) {

							link = "/image/red/hide.png";
						}
						if (pawn.getTeam() == 2) {
							link = "/image/blue/hide.png";
						}

					}
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
		int line = arrow[4];
		int row = arrow[5];
		// System.out.println(row);
		// System.out.println(arrow[0]+" ICI");

		// dessine les fleches selon le focus
		if (arrow[0] != -1) {
			String linkUp = "/image/up.png";
			java.net.URL uriUp = getClass().getResource(linkUp);
			try {
				img = ImageIO.read(uriUp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img, (row * (this.getWidth() / nbrCol)) + 10,
					((line - arrow[0]) * (this.getHeight() / nbrLigne)) + 10,
					this.getWidth() / nbrCol - 20, this.getHeight() / nbrLigne
							- 20, this);
			// System.out.println("dessin");
		}
		if (arrow[1] != -1) {
			String linkR = "/image/right.png";
			java.net.URL uriR = getClass().getResource(linkR);
			try {
				img = ImageIO.read(uriR);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img,
					((row + arrow[1]) * (this.getWidth() / nbrCol)) + 10,
					(line * (this.getHeight() / nbrLigne)) + 10,
					this.getWidth() / nbrCol - 20, this.getHeight() / nbrLigne
							- 20, this);
		}
		if (arrow[2] != -1) {
			String linkD = "/image/down.png";
			java.net.URL uriD = getClass().getResource(linkD);
			try {
				img = ImageIO.read(uriD);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img, (row * (this.getWidth() / nbrCol)) + 10,
					((line + arrow[2]) * (this.getHeight() / nbrLigne)) + 10,
					this.getWidth() / nbrCol - 20, this.getHeight() / nbrLigne
							- 20, this);
		}
		if (arrow[3] != -1) {
			String linkL = "/image/left.png";
			java.net.URL uriL = getClass().getResource(linkL);
			try {
				img = ImageIO.read(uriL);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img,
					((row - arrow[3]) * (this.getWidth() / nbrCol)) + 10,
					(line * (this.getHeight() / nbrLigne)) + 10,
					this.getWidth() / nbrCol - 20, this.getHeight() / nbrLigne
							- 20, this);
		}

	}

	/**
	 * Gets the array 'nouvArrow' and store it into 'arrow'.
	 * 
	 * @param nouvArrow
	 *            The new array 'arrow'.
	 */
	public void recupArrow(int[] nouvArrow) {
		arrow = nouvArrow;
	}

	/**
	 * Retrieves the grid in parameter.
	 * 
	 * @param nouvGrid
	 *            The grid.
	 */
	public void recupGrid(Game nouvGrid) {
		grid = nouvGrid;
	}

	// public void recupGame(AGame type){
	// jeu = type;
	// }
}
