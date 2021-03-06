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
	int nbrCol, nbrLigne;
	boolean showKnow = false;

	/**
	 * Constructor of the class.
	 * 
	 * @param grid
	 *            The grid.
	 */
	public PaneGame(Game grid) {
		this.grid = grid;
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			java.net.URL urlBack = getClass().getResource("/image/back.jpg");
			imgBack = ImageIO.read(urlBack);
		} catch (IOException e) {
			e.printStackTrace();
		}
		nbrLigne = grid.getLine() + 1;
		nbrCol = grid.getRow() + 1;
		// Draws the background image.
		g.drawImage(imgBack, 0, 0, this.getWidth(), this.getHeight(), this);
		// Draws the lines.
		for (int lig = 1; lig < nbrCol; lig++) {
			g.drawLine((this.getWidth() / nbrCol) * lig, 0,
					(this.getWidth() / nbrCol) * lig, this.getHeight());
		}
		for (int lig = 1; lig < nbrLigne; lig++) {
			g.drawLine(0, (this.getHeight() / nbrLigne) * lig, this.getWidth(),
					(this.getHeight() / nbrLigne) * lig);
		}

		// Draws the images of the pawns according to the grid.
		String link = "/image/red/hide.png";
		for (int i = 0; i < (nbrLigne); i++) {
			for (int j = 0; j < (nbrCol); j++) {
				if (grid.getPawn(i, j) != null) {
					APawn pawn = grid.getPawn(i, j);
					if (pawn.getKnow() && showKnow) {
						link = pawn.getURI();
					} else if (pawn.getShow()) {
						link = pawn.getURI();
					} else if (pawn.getTeam() == view || view == 0
							|| pawn.getTeam() == 0) {
						link = pawn.getURI();
					} else if (pawn.getTeam() == view || view == 0
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

	/**
	 * Draws the arrows of the possible moves for a focused pawn.
	 * 
	 * @param g
	 *            A 'Graphics' object.
	 */
	public void paintArrow(Graphics g) {
		int line = arrow[4];
		int row = arrow[5];

		// Paints the 'last move' arrow.
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
		// Draws the arrows from the focus.
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
	 * Retrieves the game in parameter.
	 * 
	 * @param game
	 *            The game.
	 */
	public void recupGame(Game game) {
		grid = game;
	}

	/**
	 * Sets the view to know which team has to be visible.
	 * 
	 * @param view
	 *            0 if all pawns are visible,<br/>
	 *            1 if the pawns of team 1 are visible,<br/>
	 *            2 if the pawns of team 2 are visible,<br/>
	 *            3 if all the pawns are hidden.
	 */
	public void setView(int view) {
		this.view = view;
	}

	/**
	 * Sets the 'showKnow' value from the 'know' value given in parameter.
	 * 
	 * @param know
	 *            true if the known pawns are visible,<br/>
	 *            false otherwise.
	 */
	public void setShowKnow(boolean know) {
		this.showKnow = know;
	}
}
