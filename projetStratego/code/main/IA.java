package main;

import java.util.Random;
import java.util.Vector;

import pawn.APawn;
import pawn.Scout;
import util.Dic;

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

				// System.out.println("boucle ia");
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
								int[][] m = { { line, row }, { line + max, row } };
								move = m;
								return move;
							}

						}
						if (dir == 1) {
							if (pawn.movePoss(game, line - max, row)) {
								int[][] m = { { line, row }, { line - max, row } };
								move = m;
								return move;
							}
						}
						if (dir == 2) {
							if (pawn.movePoss(game, line, row + max)) {
								int[][] m = { { line, row }, { line, row + max } };
								move = m;
								return move;
							}
						}
						if (dir == 3) {
							if (pawn.movePoss(game, line, row - max)) {
								int[][] m = { { line, row }, { line, row - max } };
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
			return iaL1(game);
		}
		return iaL0(game);
	}

	private int[][] iaL1(Game game) {
		int[][] move = { { -1, -1 }, { -1, -1 } };
		if (team == ((game.getTurn() + 1) % 2) + 1) {
			Vector<APawn> pawnSide = getSidedPawn(game);
			if (pawnSide.size() != 0) {
				iaL1Side(game,pawnSide);
			}

			// TODO quand aucun pion n est collé
		}
		System.out.println("pas de mouvement interessant, passage en aleatoire");
		return iaL0(game);
	}

	
	private int[][] iaL1Side(Game game, Vector<APawn> pawnSide){
		int[][] move = { { -1, -1 }, { -1, -1 } };
		Random rand = new Random();
		int ind = rand.nextInt(pawnSide.size());
		APawn pawn = pawnSide.get(ind);
		if (pawn.getLevel() == 2) { // si notre pion est un eclaireur
			APawn pawnside = game.getPawn(pawn.posX - 1, pawn.posY);
			if (pawnside != null) {
				if (pawnside.getTeam() == (team % 2) + 1 && !pawnside.getKnow()) {
					int[][] moveI = { { pawn.posX, pawn.posY }, { pawn.posX - 1, pawn.posY } };
					move = moveI;
					return move;
				}
			}
			pawnside = game.getPawn(pawn.posX + 1, pawn.posY);
			if (pawnside != null) {
				if (pawnside.getTeam() == (team % 2) + 1 && !pawnside.getKnow()) {
					int[][] moveI = { { pawn.posX, pawn.posY }, { pawn.posX + 1, pawn.posY } };
					move = moveI;
					return move;
				}
			}
			pawnside = game.getPawn(pawn.posX, pawn.posY - 1);
			if (pawnside != null) {
				if (pawnside.getTeam() == (team % 2) + 1 && !pawnside.getKnow()) {
					int[][] moveI = { { pawn.posX, pawn.posY }, { pawn.posX, pawn.posY - 1 } };
					move = moveI;
					return move;
				}
			}
			pawnside = game.getPawn(pawn.posX, pawn.posY + 1);
			if (pawnside != null) {
				if (pawnside.getTeam() == (team % 2) + 1 && !pawnside.getKnow()) {
					int[][] moveI = { { pawn.posX, pawn.posY }, { pawn.posX, pawn.posY + 1 } };
					move = moveI;
					return move;
				}
			}
			// TODO mouvement pour se "sauver"

		} // fin pion eclaireur
		else if (pawn.getLevel() == -6 && pawn.getLevel() == 11) { // debut pion n est pas eclaireur,flag ou bomb
			APawn pawnside = game.getPawn(pawn.posX - 1, pawn.posY);
			if (pawnside != null) {
				int[][] res = iaL1SideM(game,pawn,pawnside,pawn.posX - 1, pawn.posY,pawn.posX + 1, pawn.posY);
				if(res!=null){
					return res;
				}
			}
			pawnside = game.getPawn(pawn.posX + 1, pawn.posY);
			if (pawnside != null) {
				int[][] res = iaL1SideM(game,pawn,pawnside,pawn.posX + 1, pawn.posY,pawn.posX - 1, pawn.posY);
				if(res!=null){
					return res;
				}
			}
			pawnside = game.getPawn(pawn.posX , pawn.posY-1);
			if (pawnside != null) {
				int[][] res = iaL1SideM(game,pawn,pawnside,pawn.posX , pawn.posY-1,pawn.posX , pawn.posY+1);
				if(res!=null){
					return res;
				}
			}
			pawnside = game.getPawn(pawn.posX , pawn.posY+1);
			if (pawnside != null) {
				int[][] res = iaL1SideM(game,pawn,pawnside,pawn.posX , pawn.posY+1,pawn.posX , pawn.posY-1);
				if(res!=null){
					return res;
				}
			}
			
		}
		if (pawnSide.size() > 1) {
			return iaL1(game);
		}
		return move;
	}
	/**
	 * 
	 * @param game
	 * @param pawn
	 * @param pawnside
	 * @param x1 coord final si attack
	 * @param y1 coord final si attack
	 * @param x2 coord fianl si !attack
	 * @param y2 coord final si !attack
	 * @return
	 */
	
	
	private int[][] iaL1SideM(Game game,APawn pawn,APawn pawnside,int x1,int y1, int x2,int y2 ){
		int[][] move = { { -1, -1 }, { -1, -1 } };
		if (pawnside.getTeam() == (team % 2) + 1) {
			if (pawnside.getKnow()) {
				if (pawn.attack(pawnside)==1) {
					int[][] moveI = { { pawn.posX, pawn.posY }, { x1, y1 } };
					move = moveI;
					return move;
				} else {
					if (game.getPawn(x2, y2) == null && x2 <= game.getLine() && x2 >=0 && y2 <= game.getRow() && y2 >=0) {
						int[][] moveI = { { pawn.posX, pawn.posY }, { x2, y2 } };
						move = moveI;
						return move;
					}
				}
			} else {
				// si pion ennemi inconnu
				int probLvl;
				if (pawnside.getMoved()) { // si pion ennemi a
											// bougé
					probLvl = probMoved(game);
				} else { // si pion ennemi n as pas encore bougé
					probLvl = probUnmoved(game);
				}
				if (pawn.getLevel() > probLvl) {
					int[][] moveI = { { pawn.posX, pawn.posY }, { x1, y1 } };
					move = moveI;
					return move;
				} else {
					if (game.getPawn(x2, y2) == null && x2 <= game.getLine() && x2 >=0 && y2 <= game.getRow() && y2 >=0) {
						int[][] moveI = { { pawn.posX, pawn.posY }, { x2, y2 } };
						move = moveI;
						return move;
					}

				}

			}
		}
		
		return null;
		
	}
	
	private Vector<APawn> getSidedPawn(Game game) {
		Vector<APawn> pawnSide = null;
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				boolean added = false;
				APawn pawn = game.getPawn(i, j);
				APawn pawnside;
				if (pawn != null) {
					if (pawn.getTeam() == team) {
						pawnside = game.getPawn(i - 1, j);
						if (isPawnSide(pawn) && !added) {
							pawnSide.add(pawn);
							added = true;
						}
						pawnside = game.getPawn(i + 1, j);
						if (isPawnSide(pawn) && !added) {
							added = true;
							pawnSide.add(pawn);
						}
						pawnside = game.getPawn(i, j - 1);
						if (isPawnSide(pawn) && !added) {
							pawnSide.add(pawn);
							added = true;
						}
						pawnside = game.getPawn(i, j + 1);
						if (isPawnSide(pawn) && !added) {
							pawnSide.add(pawn);
							added = true;
						}

					}
				}
			}
		}
		return pawnSide;
	}

	/**
	 * calculate the probablity level of the pawn for unmoved pawn
	 * 
	 * @param game
	 * @return
	 */
	private int probUnmoved(Game game) {
		int allLvl = 0, count = 0;
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				APawn pawn = game.getPawn(i, j);
				if (pawn.getTeam() == (team % 2) + 1 && !pawn.getKnow() && pawn.getLevel() != -6) {
					allLvl += pawn.getLevel();
					count++;
				}
			}
		}

		return allLvl / count;
	}

	/**
	 * calculate the probablity level of the pawn for moved pawn
	 * 
	 * @param game
	 * @return
	 */
	private int probMoved(Game game) {
		int allLvl = 0, count = 0;
		for (int i = 0; i <= game.getLine(); i++) {
			for (int j = 0; j <= game.getRow(); j++) {
				APawn pawn = game.getPawn(i, j);
				if (pawn.getTeam() == (team % 2) + 1 && !pawn.getKnow()) {
					if (pawn.getLevel() != -6 && pawn.getLevel() != 11) {
						allLvl += pawn.getLevel();
						count++;
					}
				}
			}
		}

		return allLvl / count;
	}

	private boolean isPawnSide(APawn pawnside) {
		if (pawnside != null) {
			if (pawnside.getTeam() == (team % 2) + 1) {
				return true;
			}
		}
		return false;
	}

	public static String[] getListLvl() {
		return listLvl;
	}

}

/*
 * si c est eclaireur, il attaque que si le pion n est pas connu, ia differente pour eclaireur
 * 
 * si c est espion, n attaque que si c est le marechal connu A VOIR si demineur va chercher bombe plus proche connue mettre valeur sur les pions qui ont bouge comme ca on sait que c est pas une bombe mettre valeur sur le pion pour savoir ssi il est connu ou pas
 * 
 * pour tous(sauf eclaireur):attaque que si il connait la valeur de l autre pion et <= a lui n attaque jamais un pion qui n as pas bouge (sauf demineur) quand il trouve un pion avec 1/4 que cela soit un flag il l attaque
 * 
 * 1 verifier si un de ses pions et colle a un pion adverse
 * 
 * si oui si plusieurs random prendre 1 pion bool = mouvement() du pion si true: attaque si false: recule else si non bouge un pion random
 * 
 * 
 * 
 * doitonattaquer(): si eclaireur mouvement de taille random si pion inconnu ou spy return true si pion connu: return false else si victime connu: si attaquant perds return false si attaquant gagne ou egalite attaque return true else si victime a bouge calcul proba que la victime soit le flag calcul
 * proba du niveau de la victime si proba flag <=4 attaque else si selon proba attaquant gagne attaque else return false
 * 
 * 
 * proba flag: return compte les pions qui non pas bouge - les bombes connu/detruite
 * 
 * proba(bool abouge: parcours tableau si pion team adverse et pion inconnu et abouge: si pion !=bombe et pion != flag nbrpion++ sommelevel+=leveldupion else si pion teamadverse et pion inconnu si pion !=flag nbrpion++ sommelevel+=levelpion return sommelevel/nbrpion
 */