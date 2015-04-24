package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Game;
import main.GridIA;
import main.IA;
import pawn.APawn;

public class TestIa extends JFrame{

	public JComboBox ia1=new JComboBox(),ia2=new JComboBox();
	public JSlider nbr = new JSlider();
	public JButton goBtn=new JButton("Lancer les tests");
	public String[] listLvl;
	JLabel lab1;
	
	
	@SuppressWarnings("unchecked")
	public TestIa(){
		this.setResizable(true);
		this.setSize(300, 320);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Tester l'IA");
		this.setLayout(new GridLayout(4,1));
		this.setVisible(true);
		listLvl=IA.getListLvl();
		
		JPanel panel1=new JPanel();
		JPanel panel2=new JPanel();
		JPanel panel3=new JPanel();
		JPanel panel4=new JPanel();
		
		nbr.setMaximum(100);
	    nbr.setMinimum(0);
	    nbr.setPaintTicks(true);
	    nbr.setPaintLabels(true);
	    nbr.setMinorTickSpacing(10);
	    nbr.setMajorTickSpacing(20);
		
		lab1=new JLabel("Nombre de test à effectuer: "+nbr.getValue());
		panel1.add(lab1);
		panel1.add(nbr);
		this.add(panel1);
		
		
		for(int i=0;i<listLvl.length;i++){
			ia1.addItem(listLvl[i]);
			ia2.addItem(listLvl[i]);
		}
		 JLabel lab2=new JLabel("Niveau de l'IA 1");
		 JLabel lab3=new JLabel("Niveau de l'IA 2");
		 
		 panel2.add(lab2);
		 panel2.add(ia1);
		 
		 panel3.add(lab3);
		 panel3.add(ia2);
		 
		 panel4.add(goBtn);
		 
		 this.add(panel2);
		 this.add(panel3);
		 this.add(panel4);
		 
		 nbr.addChangeListener(new ChangeListener(){
		      public void stateChanged(ChangeEvent event){
		        lab1.setText("Nombre de test à effectuer: " + ((JSlider)event.getSource()).getValue());
		      }
		    });    
		 
		 goBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
//					System.out.println(nbr.getText()+"  herer");
					if(nbr.getValue()!=0){
						new ProgressTestIa(nbr.getValue(),(String)ia1.getSelectedItem(),(String)ia2.getSelectedItem());
					}
					else{
						JOptionPane jop1 = new JOptionPane();
						jop1.showMessageDialog(null, "Veuillez entrer un nombre valable", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		
	}
	
//	public void doTest(final int nbr, final String lvl1,final String lvl2){
//		new Thread(new Runnable(){
//			
//			@Override
//			public void run() {
//				int w1=0,w2=0;
//				System.out.println(lvl1+"   "+lvl2);
//				GridIA grid1=new GridIA(1,0);
//				GridIA grid2=new GridIA(2,0);
//				for(int i=0;i<nbr;i++){
////					System.out.println("debut");
//					IA ia1=new IA(lvl1, 1);
//					IA ia2=new IA(lvl2, 2);
//					Game game = new Game(10,1);
//					if(lvl1==IA.getListLvl()[0]){
//						grid1=new GridIA(1,0);
//					}
//					else if(lvl1==IA.getListLvl()[1]){
//						grid1=new GridIA(1,1);
//					}
//					if(lvl2==IA.getListLvl()[0]){
//						grid2=new GridIA(2,0);
//					}
//					else if(lvl2==IA.getListLvl()[1]){
//						grid2=new GridIA(2,1);
//					}
//					
//					game.placeTeam(grid1.getGrid(), 1);
//					game.placeTeam(grid2.getGrid(), 2);
//					game.showGrid();
//					int gameWin = 0;
//					while(gameWin==0){
////						System.out.println("boucle");
//						if((((game.getTurn() + 1) % 2) + 1) == 1){
////							System.out.println("1if");
//							int[][] next =ia1.getNext(game);
////							System.out.println("afternext1");
//							APawn pa = game.getPawn(next[0][0],
//									next[0][1]);
//							pa.move(game, next[1][0], next[1][1]);
//							game.addTurn();
////							System.out.println("fin if1");
//						}
//						else if((((game.getTurn() + 1) % 2) + 1) == 2){
////							System.out.println("2if");
//							int[][] next =ia2.getNext(game);
//							APawn pa = game.getPawn(next[0][0],
//									next[0][1]);
//							pa.move(game, next[1][0], next[1][1]);
//							game.addTurn();
//						}
//						gameWin=game.win();
//					}
//					game.showGrid();
//					System.out.println("WIN GAME  "+gameWin);
//					if(gameWin==1){
//						w1++;
//					}
//					else if(gameWin==2){
//						w2++;
//					}
//					
//					
//				}
//				System.out.println("ia1= "+w1+"  ia2= "+w2);
//			}
//			
//		}).start();
//	}
}
