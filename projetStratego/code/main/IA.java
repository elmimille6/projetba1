package main;

import java.util.Random;

import pawn.APawn;
import pawn.Scout;

public class IA {
	int level = 0, team = 0;
	final static String[] listLvl = { "Niveau facile", "Niveau moyen" };

	public IA(String lvl, int team) {
		for (int i = 0; i < listLvl.length; i++) {
			if (lvl == listLvl[i]) {
				this.level = i;
			}
		}
		this.team = team;
	}

	public IA(String lvl) {
		this(lvl, 2);
	}

	private int[][] iaL0(Game game) {
		int[][] move = { { -1, -1 }, { -1, -1 } };
		if (team == ((game.getTurn() + 1) % 2) + 1) {
			boolean moved = false;
			Random rand = new Random();
			while (!moved) {

				System.out.println("boucle ia");
				int row = rand.nextInt(game.getRow()+1);
				int line = rand.nextInt(game.getLine()+1);
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

	public int[][] getNext(Game game) {
		if (level == 1) {

		}
		return iaL0(game);
	}

	public static String[] getListLvl() {
		return listLvl;
	}

}

/*
 * si c est eclaireur, il attaque que si le pion n est pas connu, ia differente
 * pour eclaireur
 * 
 * si c est espion, n attaque que si c est le marechal connu A VOIR si demineur
 * va chercher bombe plus proche connue mettre valeursur les pions qui ont bouge
 * comme ca on sait que c est pas une bombe mettre valeur sur le pion pour
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