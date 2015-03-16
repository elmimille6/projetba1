package gui;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Game;
import pawn.APawn;
import util.Dic;

public class PaneGamePawn extends JPanel {
	Dic startTeam;
	Game grid;
	int side;
	Dic count;
	
	public PaneGamePawn(Dic dic,Game game,int side){
		startTeam=dic;
		this.side=side;
		upGame(game);
		JLabel lTeam;
		if(side==1){
		 lTeam= new JLabel("Pion rouge");
		}
		else{
			lTeam = new JLabel("Pion bleu");
		}
		this.add(lTeam);
		
	}
	public void upGame(Game game){
		grid=game;
		count=count();
		this.repaint();
	}
	
	public Dic count(){
		Dic dic=new Dic();
		for(int i=0; i<grid.getLine();i++){
			for(int j=0; j<grid.getRow();j++){
				if(grid.getPawn(i, j) instanceof APawn){
					APawn pawn= grid.getPawn(i, j);
					if(pawn.getTeam()==side){
						if(dic.isIn(pawn)){
							dic.set(pawn, dic.get(pawn)+1);
						}
					}
				}
			}
		}
		
		return dic;
	}
	
	public void paintComponent(Graphics g) {
		for (int i=0;i<startTeam.getSize();i++){
			JLabel label=new JLabel((String) startTeam.getObject(i)+" : "+count.get(startTeam.getObject(i))+"/"+startTeam.get(i));
			this.add(label);
		}
	}
}
