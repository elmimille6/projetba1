package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import online.FrameServer;
import online.StratClient;
import main.Game;
import main.AI;
import main.Main;

/**
 * This class creates a menu window.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class MenuWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu1 = new JMenu("Options");
	private JMenu menu2 = new JMenu("En ligne");

	private JMenuItem quit = new JMenuItem("Quitter");
	private JMenuItem testia = new JMenuItem("Tester l'IA");
	private JMenuItem startSave = new JMenuItem("Utiliser la sauvegarde");
	private JMenuItem manager = new JMenuItem(
			"Gestionnaire de grilles");
	private JMenuItem client = new JMenuItem("Se connecter a un serveur");
	private JMenuItem serveur = new JMenuItem("Heberger une partie");

	private JComboBox<String> comboJeu = new JComboBox<String>();

	private JLabel labType = new JLabel("Type de jeu");
	private JComboBox<String> comboType = new JComboBox<String>();

	private JLabel labPlayer = new JLabel("Nombre de joueurs");
	private JComboBox<String> comboPlayer = new JComboBox<String>();

	private JLabel labInit = new JLabel("Initialisation des pions");
	private JComboBox<String> comboInit = new JComboBox<String>();

	private JLabel labIa1 = new JLabel("Niveau de la premiere IA");
	private JComboBox<String> comboIa1 = new JComboBox<String>();

	private JButton goBtn = new JButton("Lancer la partie !");

	private JPanel container = new JPanel();

	public int game = 1, nbrPlayer = 1, lvl2 = 0, test = 1, modifGrid = 0,
			typeOfGame = 40;
	public String lvl1 = "";

	public int initGridGame = 0;

	/**
	 * Creates the 'Menu' window.
	 */
	public MenuWindow() {
		this.setResizable(true);
		this.setSize(500, 554);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Menu");
		this.setContentPane(container);

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		testia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TestAI();
			}
		});
		manager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GridManager();
			}
		});
		startSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game grid = Game.load();
				if (grid != null) {
					new WindowGame(grid);
				} else {
					JOptionPane.showMessageDialog(null,
							"Aucune sauvegarde n'est disponible",
							"Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		client.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StratClient();
			}
		});

		serveur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrameServer();
			}
		});

		this.menu1.add(testia);
		this.menu1.addSeparator();
		this.menu1.add(manager);
		this.menu1.add(startSave);
		this.menu1.addSeparator();
		this.menu1.add(quit);
		this.menuBar.add(menu1);

		this.menu2.add(client);
		this.menu2.add(serveur);
		this.menuBar.add(menu2);

		this.setJMenuBar(menuBar);

		container.setBackground(Color.white);
		container.setLayout(new GridLayout(5, 1));

		comboJeu.setPreferredSize(new Dimension(100, 20));

		JPanel panPlayer = new JPanel();
		panPlayer.add(labPlayer);
		panPlayer.add(comboPlayer);
		comboPlayer.addItem("1 Joueur");
		comboPlayer.addItem("2 Joueurs");
		comboPlayer.addActionListener(new ItemActionPlayer());
		comboPlayer.setPreferredSize(new Dimension(130, 20));
		comboPlayer.setEnabled(true);

		JPanel panType = new JPanel();
		panType.add(labType);
		panType.add(comboType);
		comboType.addItem("Normal");
		comboType.addItem("Duel");
		comboType.addActionListener(new ItemActionType());
		comboType.setPreferredSize(new Dimension(100, 20));
		comboType.setEnabled(true);

		JPanel panInit = new JPanel();
		panInit.add(labInit);
		panInit.add(comboInit);
		comboInit.addItem("Automatique");
		comboInit.addItem("Manuelle Joueur Rouge");
		comboInit.addActionListener(new ItemActionInit());
		comboInit.setPreferredSize(new Dimension(200, 20));
		comboInit.setEnabled(true);

		JPanel panIA1 = new JPanel();
		panIA1.add(labIa1);
		panIA1.add(comboIa1);
		String[] listLevel = AI.getListLevel();
		for (int i = 0; i < listLevel.length; i++) {
			comboIa1.addItem(listLevel[i]);
		}
		comboIa1.addActionListener(new ItemActionIa1());
		comboIa1.setPreferredSize(new Dimension(150, 20));
		comboIa1.setEnabled(true);

		JPanel panBtn = new JPanel();
		JPanel panBtn2 = new JPanel();
		panBtn2.add(goBtn);
		panBtn.setLayout(new BorderLayout());
		panBtn.add(panBtn2, BorderLayout.CENTER);

		goBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.play();
			}
		});
		goBtn.setEnabled(true);

		container.add(panType);
		container.add(panPlayer);
		container.add(panInit);
		container.add(panIA1);
		container.add(panBtn);

		this.setVisible(true);
	}

	class ItemActionType implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (comboType.getSelectedItem() == "Normal") {
				typeOfGame = 40;
			} else {
				typeOfGame = 10;
				nbrPlayer = 2;
				initGridGame = 3;
			}
			tryEnable();
		}
	}

	class ItemActionPlayer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (comboPlayer.getSelectedItem() == "2 Joueurs") {
				nbrPlayer = 2;
				comboIa1.setEnabled(false);
				if (comboInit.getItemCount() == 2) {
					comboInit.addItem("Manuelle Joueur Bleu");
					comboInit.addItem("Manuelle");
				}
			} else if (comboPlayer.getSelectedItem() == "1 Joueur") {
				nbrPlayer = 1;
				if (comboInit.getItemCount() == 4) {
					comboInit.removeItem("Manuelle Joueur Bleu");
					comboInit.removeItem("Manuelle");
				}
				comboIa1.setEnabled(true);
			} else {
				nbrPlayer = 1;
			}
			tryEnable();
		}
	}

	class ItemActionInit implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (comboInit.getSelectedItem() == "Manuelle Joueur Rouge") {
				initGridGame = 1;
			} else if (comboInit.getSelectedItem() == "Manuelle Joueur Bleu") {
				initGridGame = 2;
			} else if (comboInit.getSelectedItem() == "Manuelle") {
				initGridGame = 3;
			} else {
				initGridGame = 0;
			}
			tryEnable();
		}
	}

	class ItemActionIa1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			lvl1 = (String) comboIa1.getSelectedItem();
			tryEnable();
		}
	}

	/**
	 * Enables the player(s) to move the pawns.
	 */
	public void tryEnable() {
		if (game != 0) {
			comboPlayer.setEnabled(true);
			comboInit.setEnabled(true);
			goBtn.setEnabled(true);
		} else if (game == 0 || typeOfGame == 10) {
			comboPlayer.setEnabled(false);
			goBtn.setEnabled(false);
		}
		if (nbrPlayer == 1 || nbrPlayer == 0) {
			comboIa1.setEnabled(true);
		}
		if (typeOfGame == 10) {
			comboPlayer.setEnabled(false);
			comboInit.setEnabled(false);
			comboIa1.setEnabled(false);
		}
	}

	/**
	 * Gets the number of players.
	 * 
	 * @return The number of players:<br/>
	 *         1 if it's against IA or<br/>
	 *         2 if it's player against player.
	 */
	public int getNbrPlayer() {
		return nbrPlayer;
	}

	/**
	 * Gets if the player wants to initialize its grid.
	 * 
	 * @return 0 if everything is automatic,<br/>
	 *         1 if the red player wants to initialize,<br/>
	 *         2 if the blue player wants to initialize or<br/>
	 *         3 if the two players want to initialize.
	 */
	public int getInitGridGame() {
		return initGridGame;
	}

	/**
	 * Gets the type of the game.
	 * 
	 * @return The type of the game.
	 */
	public int getTypeOfGame() {
		return typeOfGame;
	}
}