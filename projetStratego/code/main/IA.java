package main;

public class IA {
	public static int[] IA(Game game,int lvl){
		int[] res = {-1,-1};
		if(lvl==1){
			
		}
		return res;
	}
}


/*
si c est eclaireur, il attaque que si le pion n est pas connu, ia differente pour eclaireur

si c est espion, n attaque que si c est le marechal connu
A VOIR si demineur va chercher bombe plus proche connue
mettre valeursur  les pions qui ont bougé comme ca on sait que c est pas une bombe
mettre valeur sur le pion pour savoir ssi il est connu ou pas

pour tous(sauf eclaireur):attaque que si il connait la valeur de l autre pion et <= a lui
n attaque jamais un pion qui n as pas bougé(sauf demineur)
quand il trouve un pion avec 1/4 que cela soit un flag il l attaque

1 verifier si un de ses pions et collé a un pion adverse
	
	si oui
		si plusieurs
			random prendre 1 pion
		bool = mouvement() du pion si true: attaque si false: recule
	else si non
		bouge un pion random
	


doitonattaquer():
	si eclaireur
		mouvement de taille random
			si pion inconnu ou spy
				return true
			si pion connu:
				return false
	else
			si victime connu:
				si attaquant perds
					return false
				si attaquant gagne ou egalite
					attaque
					return true
			else
				si victime a bougé
					calcul proba que la victime soit le flag
					calcul proba du niveau de la victime
					si proba flag <=4
						attaque
					else si selon proba attaquant gagne
						attaque
					else
						return false
					
					
proba flag:
	return compte les pions qui non pas bougé - les bombes connu/detruite
					
proba(bool abougé:
	parcours tableau
		si pion team adverse et pion inconnu et abougé:
			si pion !=bombe et pion != flag
				nbrpion++
				sommelevel+=leveldupion
		else si pion teamadverse et pion inconnu
			si pion !=flag
				nbrpion++
				sommelevel+=levelpion
	return sommelevel/nbrpion
	
	



*/