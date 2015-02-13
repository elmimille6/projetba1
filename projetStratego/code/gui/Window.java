package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import main.Main;

@SuppressWarnings("rawtypes")
/**
 * This class creates a window.
 *
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu1 = new JMenu("Fichier");

	private JMenuItem quit = new JMenuItem("Quitter");
	private JMenuItem testia = new JMenuItem("Tester l'IA");
	private JMenuItem startSave = new JMenuItem("Utiliser la sauvegarde");

	private JLabel labJeu = new JLabel("Jeu");

	private JComboBox comboJeu = new JComboBox();
	private JLabel labPlayer = new JLabel("Nombre de joueur");
	private JComboBox comboPlayer = new JComboBox();

	private JLabel labIa1 = new JLabel("Niveau de la première ia");
	private JComboBox comboIa1 = new JComboBox();

	private JLabel labIa2 = new JLabel("Niveau de la seconde ia");
	private JComboBox comboIa2 = new JComboBox();
	private JButton goBtn = new JButton("Lancer la partie!");

	private JPanel container = new JPanel();

	public int game = 1, nbrPlayer = 2, lvl1 = 0, lvl2 = 0, test = 1,
			modifGrid = 0;

	private JCheckBoxMenuItem changeGrid = new JCheckBoxMenuItem(
			"modifier la taille de la grille");

	@SuppressWarnings("unchecked")
	public Window() {
		this.setResizable(true);
		this.setSize(500, 554);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Projet");
		this.setBackground(Color.GREEN);
		this.setContentPane(container);

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		testia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// testIA

			}
		});
		startSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// start sauvegarde
			}
		});

		this.menu1.add(testia);
		this.menu1.addSeparator();
		this.menu1.add(startSave);
		this.menu1.addSeparator();
		this.menu1.add(quit);
		this.menuBar.add(menu1);

		this.setJMenuBar(menuBar);

		container.setBackground(Color.white);
		container.setLayout(new GridLayout(5, 1));

		comboJeu.setPreferredSize(new Dimension(100, 20));

//		JPanel panGame = new JPanel();
//		panGame.add(labJeu);
//		panGame.add(comboJeu);
//		comboJeu.addItem("");
//		comboJeu.addItem("Tic Tac Toe");
//		comboJeu.addItem("Puissance 4");
//		comboJeu.addItem("Othello");
//		comboJeu.addActionListener(new ItemActionGame());
//		comboJeu.setPreferredSize(new Dimension(100, 20));

		JPanel panPlayer = new JPanel();
		panPlayer.add(labPlayer);
		panPlayer.add(comboPlayer);
		comboPlayer.addItem("2 Joueur");
		comboPlayer.addItem("1 Joueur");
		comboPlayer.addItem("0 Joueur");
		comboPlayer.addActionListener(new ItemActionPlayer());
		comboPlayer.setPreferredSize(new Dimension(100, 20));
		comboPlayer.setEnabled(true);

		JPanel panSlide1 = new JPanel();

		panSlide1.add(labIa1);
		panSlide1.add(comboIa1);
		comboIa1.addItem("niveau facile");
		comboIa1.addItem("niveau moyen");
		comboIa1.addItem("niveau difficile");
		comboIa1.addActionListener(new ItemActionIa1());
		comboIa1.setPreferredSize(new Dimension(100, 20));
		comboIa1.setEnabled(false);

		JPanel panSlide2 = new JPanel();
		panSlide2.add(labIa2);
		panSlide2.add(comboIa2);
		comboIa2.addItem("niveau facile");
		comboIa2.addItem("niveau moyen");
		comboIa2.addItem("niveau difficile");
		comboIa2.addActionListener(new ItemActionIa2());
		comboIa2.setPreferredSize(new Dimension(100, 20));
		comboIa2.setEnabled(false);

		JPanel panBtn = new JPanel();
		JPanel panBtn2 = new JPanel();
		panBtn2.add(goBtn);
		panBtn.setLayout(new BorderLayout());
		panBtn.add(panBtn2, BorderLayout.CENTER);
		panBtn.add(changeGrid, BorderLayout.NORTH);
		changeGrid.setEnabled(false);
		changeGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (modifGrid == 1) {
					modifGrid = 0;
				} else if (modifGrid == 0) {
					modifGrid = 1;
				}

			}

		});

		goBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.play();
			}
		});
		goBtn.setEnabled(true);

//		container.add(panGame);
		container.add(panPlayer);
		container.add(panSlide1);
		container.add(panSlide2);
		container.add(panBtn);

		this.setVisible(true);
	}

	class ItemActionGame implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (comboJeu.getSelectedItem() == "Tic Tac Toe") {
				game = 1;
				changeGrid.setEnabled(false);
			} else if (comboJeu.getSelectedItem() == "Puissance 4") {
				game = 2;
				changeGrid.setEnabled(true);
			} else if (comboJeu.getSelectedItem() == "Othello") {
				game = 3;
				changeGrid.setEnabled(true);
			} else {
				game = 0;
				changeGrid.setEnabled(false);
			}
			tryEnable();
		}
	}

	class ItemActionPlayer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (comboPlayer.getSelectedItem() == "2 Joueur") {
				nbrPlayer = 2;
			} else if (comboPlayer.getSelectedItem() == "1 Joueur") {
				nbrPlayer = 1;
			} else if (comboPlayer.getSelectedItem() == "0 Joueur") {
				nbrPlayer = 0;
			} else {
				nbrPlayer = 2;
			}
			tryEnable();
		}
	}

	class ItemActionIa1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (comboIa1.getSelectedItem() == "nivreau facile") {
				lvl1 = 0;
			} else if (comboIa2.getSelectedItem() == "niveau moyen") {
				lvl1 = 3;
			} else if (comboIa2.getSelectedItem() == "niveau difficile") {
				lvl1 = 7;
			} else {
				lvl1 = 0;
			}
			tryEnable();
		}
	}

	class ItemActionIa2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (comboIa2.getSelectedItem() == "nivreau facile") {
				lvl2 = 0;
			} else if (comboIa2.getSelectedItem() == "niveau moyen") {
				lvl2 = 3;
				changeGrid.setEnabled(true);
			} else if (comboIa2.getSelectedItem() == "niveau difficile") {
				lvl2 = 7;
			} else {
				lvl2 = 0;
			}
			tryEnable();
		}
	}

	public void tryEnable() {
		if (game != 0) {
			comboPlayer.setEnabled(true);
			goBtn.setEnabled(true);
		} else {
			comboPlayer.setEnabled(false);
			goBtn.setEnabled(false);
		}
		if (nbrPlayer == 1 || nbrPlayer == 0) {
			comboIa1.setEnabled(true);
		}
	}
}