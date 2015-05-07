package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;

/**
 * This class creates the pane of the game.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class PaneGame extends JPanel {

	private static final long serialVersionUID = 1L;
	public Image img, imgBack;
	public Game grid;
	public int[] arrow = { -1, -1, -1, -1, -1, -1 };
	public int[] lastMove = { -1, -1, -1 };
	int view = 1;
	int nbrCol,nbrLigne;
	Graphics graph;

	/**
	 * Constructor of the class.
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
		graph = g;
		System.out.println("paintcomponent");
		try {
			java.net.URL urlBack = getClass().getResource("/image/back.jpg");
			imgBack = ImageIO.read(urlBack);
		} catch (IOException e) {
			e.printStackTrace();
		}
		grid.showGrid();
		nbrLigne = grid.getLine() + 1;
		nbrCol = grid.getRow() + 1;
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
				if (grid.getPawn(i, j) != null) {
					APawn pawn = grid.getPawn(i, j);
					if (pawn.getTeam() == view || view == 0 || pawn.getTeam() == 0 ) {
						link = pawn.getURI();
					} 
					else if (pawn.getShow()){
						link = pawn.getURI();
						System.out.println("pawnshow pc");
					}
					else {
						if (pawn.getTeam() == 1) {
							link = "/image/red/hide.png";
						}
						if (pawn.getTeam() == 2) {
							link = "/image/blue/hide.png";
						}
					}
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
				}
			}
		}
		

		paintArrow(g);
		grid.save();
	}

	public void paintPawn(int i, int j){
		APawn pawn = grid.getPawn(i, j);
		String link = pawn.getURI();
		java.net.URL uri = getClass().getResource(link);
		try {
			img = ImageIO.read(uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
		graph.drawImage(img, (j * (this.getWidth() / (nbrCol))) + 10,
				(i * (this.getHeight() / (nbrLigne))) + 10,
				this.getWidth() / nbrCol - 20, this.getHeight()
						/ nbrLigne - 20, this);
	}
	
	public void paintArrow(Graphics g){
		int line = arrow[4];
		int row = arrow[5];
		

		//paint arrow last move
		lastMove = grid.getLastMove();
		if (lastMove[0] != -1) {
			String linkLM = "/image/arrows/up_moved.png";
			if (lastMove[2] == 0) {
				linkLM = "/image/arrows/up_moved.png";
			}
			if (lastMove[2] == 1) {
				linkLM = "/image/arrows/right_moved.png";
			}
			if (lastMove[2] == 2) {
				linkLM = "/image/arrows/down_moved.png";
			}
			if (lastMove[2] == 3) {
				linkLM = "/image/arrows/left_moved.png";
			}
			java.net.URL uriLM = getClass().getResource(linkLM);
			try {
				img = ImageIO.read(uriLM);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(img, ((lastMove[1]) * (this.getWidth() / nbrCol)) + 10,
					(lastMove[0] * (this.getHeight() / nbrLigne)) + 10,
					this.getWidth() / nbrCol - 20, this.getHeight() / nbrLigne
							- 20, this);
		}
		// dessine les fleches selon le focus
		if (arrow[0] != -1) {
			String linkUp = "/image/arrows/up.png";
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
			String linkR = "/image/arrows/right.png";
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
			String linkD = "/image/arrows/down.png";
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
			String linkL = "/image/arrows/left.png";
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
	/*public void recupGrid(Game nouvGrid) {
		grid = nouvGrid;
		lastMove = nouvGrid.getLastMove();
	}*/

	 public void recupGame(Game game){
	 grid= game;
	 }
	 
	 public void setView(int view){
		 this.view=view;
	 }
}