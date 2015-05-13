package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import main.Game;
import main.GridAI;
import main.AI;
import pawn.APawn;

/**
 * Class of the frame whith the progress bar when a testAI is on progress
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 *
 */
public class ProgressTestAI extends JFrame {

	private static final long serialVersionUID = 8642526437577440017L;
	private Thread t;
	private JProgressBar bar;
	private JButton launch;
	private int nbr, dispose = 0;;
	private String lvl1, lvl2;
	private int win1, win2;

	/**
	 * Main constructor of the class
	 * 
	 * @param nbr
	 *            Number of tests to run.
	 * @param lvl1
	 *            Level of the first AI.
	 * @param lvl2
	 *            Level of the second AI.
	 */
	public ProgressTestAI(int nbr, String lvl1, String lvl2) {
		this.nbr = nbr;
		this.lvl1 = lvl1;
		this.lvl2 = lvl2;

		this.setSize(300, 80);
		this.setTitle("Test de l'IA en cours...");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);

		t = new Thread(new Processing());

		bar = new JProgressBar();
		bar.setMaximum(nbr - 1);
		bar.setMinimum(0);
		bar.setStringPainted(true);
		this.getContentPane().add(bar, BorderLayout.CENTER);

		launch = new JButton("Relancer");

		launch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				t = new Thread(new Processing());
				t.start();
			}
		});

		this.getContentPane().add(launch, BorderLayout.SOUTH);
		this.setVisible(true);
		t.start();

	}

	/**
	 * This method is called at the beginning and the end of the tests to change the
	 * default close operation.
	 */
	public void changeClose() {
		if (dispose == 0) {
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		} else if (dispose == 1) {
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}

	/**
	 * The thread who run the tests.
	 * 
	 * @author CAREDDA Giuliano, DUCOBU Alexandre
	 */
	class Processing implements Runnable {

		/**
		 * Runs the tests.
		 */
		@Override
		@SuppressWarnings("static-access")
		public void run() {
			win1 = 0;
			win2 = 0;
			dispose = 1;
			changeClose();
			launch.setEnabled(false);
			GridAI grid1 = new GridAI(1, 1);
			GridAI grid2 = new GridAI(2, 1);
			for (int i = 0; i < nbr; i++) {
				try {
					Thread.sleep(10L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				bar.setValue(i);
				AI ia1 = new AI(lvl1, 1);
				AI ia2 = new AI(lvl2, 2);
				Game game = new Game(10, 1);
				grid1 = new GridAI(1, AI.getIntLevel(lvl1));
				grid2 = new GridAI(2, AI.getIntLevel(lvl2));
				game.placeTeam(grid1.getGrid(), 1);
				game.placeTeam(grid2.getGrid(), 2);
				int gameWin = 0;
				while (gameWin == 0) {
					if ((((game.getTurn() + 1) % 2) + 1) == 1) {
						int[][] next = ia1.getNext(game);
						APawn pa = game.getPawn(next[0][0], next[0][1]);
						pa.move(game, next[1][0], next[1][1]);
						game.addTurn();
					} else if ((((game.getTurn() + 1) % 2) + 1) == 2) {
						int[][] next = ia2.getNext(game);
						APawn pa = game.getPawn(next[0][0], next[0][1]);
						pa.move(game, next[1][0], next[1][1]);
						game.addTurn();
					}
					gameWin = game.win();
				}
				if (gameWin == 1) {
					win1++;
				} else if (gameWin == 2) {
					win2++;
				}

			}
			double imoy1 = (100 / (win1 + win2)) * win1;
			double imoy2 = (100 / (win1 + win2)) * win2;
			int moy1 = (int) imoy1;
			int moy2 = (int) imoy2;
			JOptionPane jop1 = new JOptionPane();
			jop1.showMessageDialog(null, "Victoire de l'IA 1: " + lvl1 + ": "
					+ win1 + " soit " + moy1 + " %\nVictoire de l'IA 2 : "
					+ lvl2 + ": " + win2 + " soit " + moy2 + " %", "Resultat",
					JOptionPane.INFORMATION_MESSAGE);
			launch.setEnabled(true);
			dispose = 0;
			changeClose();
		}
	}
}
