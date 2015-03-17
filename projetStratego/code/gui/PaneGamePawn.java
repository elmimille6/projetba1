package gui;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;
import util.Dic;

public class PaneGamePawn extends JPanel {
	Dic startTeam;
	Game grid;
	int side,width,height;
	Dic count;
	JPanel pane;
	boolean move;
	
	public PaneGamePawn(Dic dic,Game game,int side){
		this.setLayout(new GridLayout(15,1 ));
		startTeam=dic;
		this.side=side;
		upGame(game);
		
//		System.out.println(startTeam.getSize()+"here");
//		this.setSize(10, 10);
		
	}
	public void fixSize(int width,int heigth){
		this.width=width;
		this.height=heigth;
	}
	public void upGame(Game game){
		move=true;
		grid=game;
		count=count();
		this.removeAll();
		System.out.println("DELETE");
		this.affichage();
		this.validate();;
	}
	
	public Dic count(){
		Dic dic=new Dic();
		for(int i=0; i<=grid.getLine();i++){
			for(int j=0; j<=grid.getRow();j++){
				if(grid.getPawn(i, j) instanceof APawn){
					APawn pawn= grid.getPawn(i, j);
					if(pawn.getTeam()==side){
						if(dic.isIn(pawn)){
							dic.set(pawn, dic.get(pawn)+1);
						}
						else{
							dic.add(pawn, 1);
						}
					}
				}
			}
		}
		System.out.println("HERE"+dic);
		return dic;
	}
	
	public void affichage() {
//		if (move) 
			JLabel lTeam;
			if (side == 1) {
				lTeam = new JLabel("  Pion rouge");
			} else {
				lTeam = new JLabel("  Pion bleu");
			}
			this.add(lTeam);
			for (int i = 0; i < startTeam.getSize(); i++) {
				JLabel label = new JLabel("  "+
						((APawn) startTeam.getObject(i)).getNamePawn() + " : "
								+ count.get(startTeam.getObject(i)) + "/"
								+ startTeam.get(i)+"  ");
				this.add(label);
				System.out.println("affichage");
			}
			System.out.println("fin affichage");
//		}
		move=false;
//		this.repaint();
		
	}
}
