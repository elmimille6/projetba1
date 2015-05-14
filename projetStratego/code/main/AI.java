package main;

import java.util.Random;
import java.util.Vector;

import pawn.APawn;
import pawn.Scout;

/**
 * This class contains an artificial intelligence (AI).
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class AI {
	int level = 0, team = 0;
	final static String[] listLevel = { "Niveau facile", "Niveau moyen" };

	/**
	 * Constructor of the class.
	 * 
	 * @param level
	 *            The level of the AI.
	 * 
	 * @param team
	 *            The team of the AI.
	 */
	public AI(String level, int team) {
		for (int i = 0; i < listLevel.length; i++) {
			if (level == listLevel[i]) {
				this.level = i;
			}
		}
		this.team = team;
	}

	/**
	 * Constructor of the class.
	 * 
	 * @param level
	 *            The level of the AI.
	 */
	public AI(String level) {
		this(level, 2);
	}

	/**
	 * This is the easy AI.
	 * 
	 * @param game
	 *            The current game.
	 * 
	 * @return The game after the move of the AI.
	 */
	private int[][] aiL0(Game game) {
		int[][] move = { { -1, -1 }, { -1, -1 } };
		if (team == ((game.getTurn() + 1) % 2) + 1) {
			boolean moved = false;
			Random rand = new Random();
			while (!moved) {

				int row = rand.nextInt(game.getRow() + 1);
				int line = rand.nextInt(game.getLine() + 1);
				APawn pawn = game.getPawn(line, row);
				if (pawn != null) {
					if (pawn.getTeam() == team) {
						int dir = rand.nextInt(4);
						int max = 1;
						if (pawn instanceof Scout) {
							max = rand.nextInt(3) + 1;
						}
						if (dir == 0) {
							if (pawn.movePoss(game, line + max, row)) {
								int[][] m = { { line, row },
										{ line + max, row } };
								move = m;
								return move;
							}

						}
						if (dir == 1) {
							if (pawn.movePoss(game, line - max, row)) {
								int[][] m = { { line, row },
										{ line - max, row } };
								move = m;
								return move;
							}
						}
						if (dir == 2) {
							if (pawn.movePoss(game, line, row + max)) {
								int[][] m = { { line, row },
										{ line, row + max } };
								move = m;
								return move;
							}
						}
						if (dir == 3) {
							if (pawn.movePoss(game, line, row - max)) {
								int[][] m = { { line, row },
										{ line, row - max } };
								move = m;
								return move;
							}
						}
					}
				}
			}
		}

		return move;
	}

	/**
	 * Gets the grid after the move of the AI,<br/>
	 * this accessor choose the right AI.
	 * 
	 * @param game
	 *            The current game.
	 * 
	 * @return The game after the move of the AI.
	 */
	public int[][] getNext(Game game) {
		if (level == 1) {
			return aiL1(game);
		}
		return aiL0(game);
	}

	/**
	 * This is the strong AI.
	 * 
	 * @param game
	 *            The current game.
	 * 
	 * @return The game after the move of the AI.
	 */
	private int[][] aiL1(Game game) {
		if (team == ((game.getTurn() + 1) % 2) + 1) {
			Vector<APawn> pawnSide = getSidePawn(game);
			if (pawnSide.size() != 0) {
				// Checks all the pawns that are close to an enemy.
				int[][] move = { { -1, -1 }, { -1, -1 }, { 0 } };
				int[][] res = iaL1Side(game, pawnSide, move);
				if (res[2][0] > 0) {
					return res;
				}
			}
		}
		return aiL0(game);
	}

	/**
	 * This methods choose the best move of all the pawn who are close to an enemies. 
	 * 
	 * @param game
	 *            The current game.
	 * 
	 * @param pawnSide
	 *            The list of all the pawns that are close to an enemy.
	 * 
	 * @param move
	 *            An array containing all the possible moves for this turn of
	 *            the game.
	 * 
	 * @return The chosen move.
	 */
	private int[][] iaL1Side(Game game, Vector<APawn> pawnSide, int[][] move) {
		int[][] res = { { -1, -1 }, { -1, -1 }, { 0 } };
		APawn pawn = pawnSide.get(0);

		if (pawn.getLevel() == 2) {
			// If the pawn is a scout.
			APawn pawnside = game.getPawn(pawn.posX - 1, pawn.posY);
			if (pawnside != null) {
				if (pawnside.getTeam() == (team % 2) + 1 && !pawnside.getKnow()) {
					int[][] moveI = { { pawn.posX, pawn.posY },
							{ pawn.posX - 1, pawn.posY }, { 1 } };
					res = moveI;
				}
			}
			if (res[2][0] > move[2][0]) {
				move = res;
			}
			pawnside = game.getPawn(pawn.posX + 1, pawn.posY);
			if (pawnside != null) {
				if (pawnside.getTeam() == (team % 2) + 1 && !pawnside.getKnow()) {
					int[][] moveI = { { pawn.posX, pawn.posY },
							{ pawn.posX + 1, pawn.posY }, { 1 } };
					res = moveI;
				}
			}
			if (res[2][0] > move[2][0]) {
				move = res;
			}
			pawnside = game.getPawn(pawn.posX, pawn.posY - 1);
			if (pawnside != null) {
				if (pawnside.getTeam() == (team % 2) + 1 && !pawnside.getKnow()) {
					int[][] moveI = { { pawn.posX, pawn.posY },
							{ pawn.posX, pawn.posY - 1 }, { 1 } };
					res = moveI;
				}
			}
			if (res[2][0] > move[2][0]) {
				move = res;
			}
			pawnside = game.getPawn(pawn.posX, pawn.posY + 1);
			if (pawnside != null) {
				if (pawnside.getTeam() == (team % 2) + 1 && !pawnside.getKnow()) {
					int[][] moveI = { { pawn.posX, pawn.posY },
							{ pawn.posX, pawn.posY + 1 }, { 1 } };
					res = moveI;
				}
			}
			if (res[2][0] > move[2][0]) {
				move = res;
			}
		}

		else if (pawn.getLevel() != -6 && pawn.getLevel() != 11) {
			// If the pawn isn't a scout, a flag or a bomb.
			APawn pawnside = game.getPawn(pawn.posX - 1, pawn.posY);
			if (pawnside != null && team % 2 + 1 == pawnside.getTeam()) {
				res = iaL1SideM(game, pawn, pawnside, pawn.posX - 1, pawn.posY,
						pawn.posX + 1, pawn.posY);

			}
			if (res[2][0] > move[2][0]) {
				move = res;
			}
			pawnside = game.getPawn(pawn.posX + 1, pawn.posY);
			if (pawnside != null && team % 2 + 1 == pawnside.getTeam()) {
				res = iaL1SideM(game, pawn, pawnside, pawn.posX + 1, pawn.posY,
						pawn.posX - 1, pawn.posY);

			}
			if (res[2][0] > move[2][0]) {
				move = res;
			}
			pawnside = game.getPawn(pawn.posX, pawn.posY - 1);
			if (pawnside != null && team % 2 + 1 == pawnside.getTeam()) {
				res = iaL1SideM(game, pawn, pawnside, pawn.posX, pawn.posY - 1,
						pawn.posX, pawn.posY + 1);

			}
			if (res[2][0] > move[2][0]) {
				move = res;
			}
			pawnside = game.getPawn(pawn.posX, pawn.posY + 1);
			if (pawnside != null && team % 2 + 1 == pawnside.getTeam()) {
				res = iaL1SideM(game, pawn, pawnside, pawn.posX, pawn.posY + 1,
						pawn.posX, pawn.posY - 1);

			}
			if (res[2][0] > move[2][0]) {
				move = res;
			}
		}

		if (pawnSide.size() > 1) {
			pawnSide.remove(0);
			return iaL1Side(game, pawnSide, move);
		}
		return move;
	}

	/**
	 * methods to evaluate the move.
	 * 
	 * @param game
	 *            The current game.
	 * 
	 * @param pawn
	 *            The current pawn.
	 * 
	 * @param pawnside
	 * @param x1
	 *            coord final si attack
	 * @param y1
	 *            coord final si attack
	 * @param x2
	 *            coord fianl si !attack
	 * @param y2
	 *            coord final si !attack
	 * @return
	 */
	private int[][] iaL1SideM(Game game, APawn pawn, APawn pawnside, int x1,
			int y1, int x2, int y2) {

		if (pawnside.getKnow()) {
			if (pawn.attack(pawnside) == 1
					&& team % 2 + 1 == pawnside.getTeam()) {
				int[][] move = { { pawn.posX, pawn.posY }, { x1, y1 },
						{ pawnside.getLevel() * 4 } };
				return move;
			}
			if (pawn.attack(pawnside) == 2
					&& team % 2 + 1 == pawnside.getTeam()) {
				if (game.getPawn(x2, y2) == null && x2 <= game.getLine()
						&& x2 >= 0 && y2 <= game.getRow() && y2 >= 0
						&& team % 2 + 1 == pawnside.getTeam()) {
					int[][] move = { { pawn.posX, pawn.posY }, { x2, y2 },
							{ pawn.getLevel() * 2 } };
					return move;
				}
				int[][] move = { { pawn.posX, pawn.posY }, { x1, y1 }, { -400 } };
				return move;
			}

		} else if (pawn.getLevel() == 3) {
			if (!pawnside.getMoved()) {
				int prob = probFlagBomb(game);
				if (prob < 10) {
					int[][] move = { { pawn.posX, pawn.posY }, { x1, y1 },
							{ 20 } };
					return move;
				}
			}
		} else {
			// If the enemy pawn is unknown.
			int probLvl;
			if (pawnside.getMoved()) {
				// If the enemy pawn has moved.
				probLvl = probMoved(game);
			} else {
				// If the enemy pawn hasn't yet moved.
				probLvl = probUnmoved(game);
				if (probFlagBomb(game) < 3
						&& team % 2 + 1 == pawnside.getTeam()) {
					int[][] move = { { pawn.posX, pawn.posY }, { x1, y1 },
							{ 20 } };
					return move;
				}
			}
			if (pawn.getLevel() > probLvl && team % 2 + 1 == pawnside.getTeam()) {
				int[][] move = { { pawn.posX, pawn.posY }, { x1, y1 }, { 15 } };
				return move;
			} else {
				if (game.getPawn(x2, y2) == null && x2 <= game.getLine()
						&& x2 >= 0 && y2 <= game.getRow() && y2 >= 0
						&& team % 2 + 1 == pawnside.getTeam()) {
					int[][] move = { { pawn.posX, pawn.posY }, { x2, y2 },
							{ 15 } };
					return move;
				}

			}
		}
		int[][] moveNull = { { -1, -1 }, { -1, -1 }, { -15 } };
		return moveNull;

	}

	/**
	 * Calculates the probability to find the flag or a bomb.
	 * 
	 * @param game
	 *            The current game.
	 * 
	 * @return The probability to find the flag or a bomb.
	 */
	private int probFlagBomb(Game game) {
		int nbpawn = 0;
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				APawn pawn = game.getPawn(i, j);
				if (pawn != null) {
					if (pawn.getTeam() == (team % 2) + 1 && !pawn.getKnow()
							&& !pawn.getMoved()) {
						nbpawn++;
					}
				}
			}
		}
		return nbpawn;
	}

	/**
	 * Gets all the pawns that are close to an enemy.
	 * 
	 * @param game
	 *            The current game.
	 * 
	 * @return A vector containing all the pawns that are close to an enemy.
	 */
	private Vector<APawn> getSidePawn(Game game) {
		Vector<APawn> pawnSide = new Vector<APawn>();
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				boolean added = false;
				APawn pawn = game.getPawn(i, j);
				APawn pawnside;
				if (pawn != null) {
					if (pawn.getTeam() == team) {
						pawnside = game.getPawn(i - 1, j);
						if (isPawnSide(pawnside) && !added) {
							pawnSide.addElement(pawn);
							added = true;
						}
						pawnside = game.getPawn(i + 1, j);
						if (isPawnSide(pawnside) && !added) {
							added = true;
							pawnSide.addElement(pawn);
						}
						pawnside = game.getPawn(i, j - 1);
						if (isPawnSide(pawnside) && !added) {
							pawnSide.addElement(pawn);
							added = true;
						}
						pawnside = game.getPawn(i, j + 1);
						if (isPawnSide(pawnside) && !added) {
							pawnSide.addElement(pawn);
							added = true;
						}

					}
				}
			}
		}
		return pawnSide;
	}

	/**
	 * Calculates the probable level of an unmoved pawn.
	 * 
	 * @param game
	 *            The current game.
	 * 
	 * @return The probable level of an unmoved pawn.
	 */
	private int probUnmoved(Game game) {
		int allLvl = 0, count = 0;
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				APawn pawn = game.getPawn(i, j);
				if (pawn != null) {
					if (pawn.getTeam() == (team % 2) + 1 && !pawn.getKnow()
							&& pawn.getLevel() != -6) {
						allLvl += pawn.getLevel();
						count++;
					}
				}
			}
		}
		if (count != 0) {
			return allLvl / count;
		} else {
			return 0;
		}
	}

	/**
	 * Calculates the probability level of the an moved pawn.
	 * 
	 * @param game
	 *            The current game.
	 * 
	 * @return The probable level of an moved pawn.
	 */
	private int probMoved(Game game) {
		int allLvl = 0, count = 0;
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				APawn pawn = game.getPawn(i, j);
				if (pawn != null) {
					if (pawn.getTeam() == (team % 2) + 1 && !pawn.getKnow()) {
						if (pawn.getLevel() != -6 && pawn.getLevel() != 11) {
							allLvl += pawn.getLevel();
							count++;
						}
					}
				}
			}
		}

		if (count != 0) {
			return allLvl / count;
		} else {
			return 0;
		}
	}

	/**
	 * Returns if the given 'pawnside' value is right.
	 * 
	 * @param pawnside
	 *            The expected side of the grid where the AI would be.
	 * 
	 * @return true if the 'pawnside' is right,<br/>
	 *         false otherwise.
	 */
	private boolean isPawnSide(APawn pawnside) {
		if (pawnside != null) {
			if (pawnside.getTeam() == (team % 2) + 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the list of the level of the AI in the game.
	 * 
	 * @return The list of the level of the AI in the game.
	 */
	public static String[] getListLevel() {
		return listLevel;
	}

	/**
	 * Gets the level of the AI at the 'level' index in the listlLevel.
	 * 
	 * @param level
	 *            The index of the wanted AI in the listlLevel.
	 * 
	 * @return The level of the AI.
	 */
	public static int getIntLevel(String level) {
		for (int i = 0; i < listLevel.length; i++) {
			if (level == listLevel[i]) {
				return i;
			}
		}
		return 0;
	}

}

/*
 * si c est eclaireur, il attaque que si le pion n est pas connu, ia differente
 * pour eclaireur
 * 
 * si c est espion, n attaque que si c est le marechal connu A VOIR si demineur
 * va chercher bombe plus proche connue mettre valeur sur les pions qui ont
 * bouge comme ca on sait que c est pas une bombe mettre valeur sur le pion pour
 * savoir ssi il est connu ou pas
 * 
 * pour tous(sauf eclaireur):attaque que si il connait la valeur de l autre pion
 * et <= a lui n attaque jamais un pion qui n as pas bouge (sauf demineur) quand
 * il trouve un pion avec 1/4 que cela soit un flag il l attaque
 * 
 * 1 verifier si un de ses pions et colle a un pion adverse
 * 
 * si oui si plusieurs random prendre 1 pion bool = mouvement() du pion si true:
 * attaque si false: recule else si non bouge un pion random
 * 
 * 
 * 
 * doitonattaquer(): si eclaireur mouvement de taille random si pion inconnu ou
 * spy return true si pion connu: return false else si victime connu: si
 * attaquant perds return false si attaquant gagne ou egalite attaque return
 * true else si victime a bouge calcul proba que la victime soit le flag calcul
 * proba du niveau de la victime si proba flag <=4 attaque else si selon proba
 * attaquant gagne attaque else return false
 * 
 * 
 * proba flag: return compte les pions qui non pas bouge - les bombes
 * connu/detruite
 * 
 * proba(bool abouge: parcours tableau si pion team adverse et pion inconnu et
 * abouge: si pion !=bombe et pion != flag nbrpion++ sommelevel+=leveldupion
 * else si pion teamadverse et pion inconnu si pion !=flag nbrpion++
 * sommelevel+=levelpion return sommelevel/nbrpion
 */