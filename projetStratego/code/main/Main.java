package main;

import gui.MenuWindow;
import gui.WindowInitPawn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.FileChannel;

/**
 * This is the main class of this project.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Main {

	public static MenuWindow menu;
	public static int nbrPlayer, initGridGame, typeOfGame;
	public static WindowInitPawn initPawn;
	public static Game game;
	public static GridIA gridIA, gridIA2;
	public static String level;

	/**
	 * Method that launches the menu of the program.
	 *
	 * @param args
	 *            Number of arguments given.
	 */
	public static void main(String[] args) {
		checkFile();

		menu = new MenuWindow();
	}

	/**
	 * Launches the game or the initialization window.
	 */
	public static void play() {
		nbrPlayer = menu.getNbrPlayer();
		initGridGame = menu.getInitGridGame();
		typeOfGame = menu.getTypeOfGame();
		level = menu.lvl1;

		if (typeOfGame == 40) {
			game = new Game(10, 1);
			if (initGridGame == 1 || initGridGame == 2) {
				GridIA gridIA = new GridIA(((initGridGame) % 2) + 1);
				game.placeTeam(gridIA.getGrid(), ((initGridGame) % 2) + 1);
				game.setComplete(1);
			}
		} else {
			game = new Game(8, 2);
			initGridGame = 3;
		}

		game.setNbPawns(typeOfGame);
		game.setPlayer(nbrPlayer);
		game.setInitGridGame(initGridGame);
		game.setLevel(level);
		new WindowInitPawn(game);
	}

	/**
	 * This method verifies if the 'GridStart.save' file exists.<br/>
	 * If it doesn't, the method calls the 'copyFile' method.
	 */
	public static void checkFile() {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream("GridStart.save"));

			in.close();
		} catch (FileNotFoundException e) {
			copyFile();
		} catch (IOException e) {

		}
	}

	/**
	 * This method copies the 'GridStart.save' file located in 'ressources' to
	 * the root of the project.
	 */
	public static void copyFile() {
		FileChannel in = null; // canal d'entree
		FileChannel out = null; // canal de sortie

		try {
			// Init
			in = new FileInputStream("code/ressources/GridStart.save")
					.getChannel();
			out = new FileOutputStream("GridStart.save").getChannel();

			// Copie depuis le in vers le out
			in.transferTo(0, in.size(), out);
		} catch (Exception e) {
			e.printStackTrace(); // n'importe quelle exception
		} finally { // finalement on ferme
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
