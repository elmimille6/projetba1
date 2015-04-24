package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import main.Game;
import main.GridIA;
import main.IA;
import pawn.APawn;

public class ProgressTestIa extends JFrame {

	private Thread t;
	private JProgressBar bar;
	private JButton launch;
	private int nbr,dispose=0;;
	private String lvl1, lvl2;
	private int win1,win2;
	
	public ProgressTestIa(int nbr, String lvl1, String lvl2){
		this.nbr=nbr;
		this.lvl1=lvl1;
		this.lvl2=lvl2;
		
		this.setSize(300, 80);
		this.setTitle("test de l'ia en cours");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		t = new Thread(new Traitement());
		
		bar = new JProgressBar();
		bar.setMaximum(nbr - 1);
		bar.setMinimum(0);
		bar.setStringPainted(true);
		this.getContentPane().add(bar, BorderLayout.CENTER);
		
		launch = new JButton("Relancer");
		
		launch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				t = new Thread(new Traitement());
				t.start();
			}
		});
		
		this.getContentPane().add(launch, BorderLayout.SOUTH);
		this.setVisible(true);
		t.start();
		
		
	}
	
	public void changeClose() {
		if (dispose == 0) {
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		} else if (dispose == 1) {
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
	
	class Traitement implements Runnable {
		@SuppressWarnings("static-access")
		public void run() {
			dispose = 1;
			changeClose();
			launch.setEnabled(false);
			System.out.println(lvl1+"   "+lvl2);
			GridIA grid1=new GridIA(1,0);
			GridIA grid2=new GridIA(2,0);
			for(int i=0;i<nbr;i++){
//				System.out.println("debut");
				try {
					Thread.sleep(10L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				bar.setValue(i);
				IA ia1=new IA(lvl1, 1);
				IA ia2=new IA(lvl2, 2);
				Game game = new Game(10,1);
				if(lvl1==IA.getListLvl()[0]){
					grid1=new GridIA(1,0);
				}
				else if(lvl1==IA.getListLvl()[1]){
					grid1=new GridIA(1,1);
				}
				if(lvl2==IA.getListLvl()[0]){
					grid2=new GridIA(2,0);
				}
				else if(lvl2==IA.getListLvl()[1]){
					grid2=new GridIA(2,1);
				}
				
				game.placeTeam(grid1.getGrid(), 1);
				game.placeTeam(grid2.getGrid(), 2);
				game.showGrid();
				int gameWin = 0;
				while(gameWin==0){
//					System.out.println("boucle");
					if((((game.getTurn() + 1) % 2) + 1) == 1){
//						System.out.println("1if");
						int[][] next =ia1.getNext(game);
//						System.out.println("afternext1");
						APawn pa = game.getPawn(next[0][0],
								next[0][1]);
						pa.move(game, next[1][0], next[1][1]);
						game.addTurn();
//						System.out.println("fin if1");
					}
					else if((((game.getTurn() + 1) % 2) + 1) == 2){
//						System.out.println("2if");
						int[][] next =ia2.getNext(game);
						APawn pa = game.getPawn(next[0][0],
								next[0][1]);
						pa.move(game, next[1][0], next[1][1]);
						game.addTurn();
					}
					gameWin=game.win();
				}
				game.showGrid();
				System.out.println("WIN GAME  "+gameWin);
				if(gameWin==1){
					win1++;
				}
				else if(gameWin==2){
					win2++;
				}
				
				
			}
			double imoy1= (100/(win1+win2))*win1;
			double imoy2= (100/(win1+win2))*win2;
			int moy1 = (int) imoy1;
			int moy2 = (int) imoy2;
			JOptionPane jop1 = new JOptionPane();
			jop1.showMessageDialog(null, "Victoire de l'ia 1: " + win1
					+" soit "+moy1+ " %\nVictoire de l'ia 2 : " + win2 +" soit "+moy2+" %",
					"Resultat", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("ia1= "+win1+"  ia2= "+win2);
			launch.setEnabled(true);
			dispose = 0;
			changeClose();
		}
	}
}
