package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Game;
import main.GridIA;
import main.GridStart;
import pawn.APawn;
import pawn.Bomb;
import pawn.Captain;
import pawn.Colonel;
import pawn.Flag;
import pawn.General;
import pawn.Lieutenant;
import pawn.Major;
import pawn.Marshal;
import pawn.Miner;
import pawn.NoPawn;
import pawn.Scout;
import pawn.Sergeant;
import pawn.Spy;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class InitWindow extends WindowInitPawn {

	private static final long serialVersionUID = 1L;
	public Game gridPane2 = new Game(6, 2, 0);
	public PaneInitPawn pane1, pane2;
	public Vector<APawn> pawns;
	public APawn spyInit, scoutInit, minerInit, sergeantInit, lieutenantInit,
			captainInit, majorInit, colonelInit, generalInit, marshalInit,
			bombInit, flagInit, noPawn;
	public int posX, posY, x = 0, y = 0, northHeight, southHeight, xSize,
			ySize;
	public APawn currentPawn, pawnShow;
	public JOptionPane jop1, jop2;
	public String link, nom;
	
	public Client client;
	public boolean online=false,send=false;
	public int Oplayer=0;

	public InitWindow(Client client, int Oplayer){
		this.client = client;
		this.online=true;
		this.Oplayer=Oplayer;
		init();
	}
	
	public InitWindow() {
		init();
	}

	public void init(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Initialisation de la grille");
		this.setSize(1024, 650);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();

		xSize = ((int) this.getWidth());
		ySize = ((int) this.getHeight());
		this.setSize(xSize, ySize);

		northHeight = (int) (Math.round(ySize * 0.55));
		southHeight = (int) (Math.round(ySize * 0.35));

		initPane2();

		this.setLocationRelativeTo(null);
		// On definit le layout a utiliser sur le content pane
		this.setLayout(new BorderLayout());
		// On ajoute le bouton au content pane de la JFrame

		pane1 = new PaneInitPawn(gridPane1);
		pane1.setPreferredSize(new Dimension(0, northHeight));

		JPanel Center = new JPanel();
		JButton play = new JButton("Jouer");
		JButton save = new JButton("Sauvegarder");
		JButton load = new JButton("Charger");
		JButton auto = new JButton("Automatique");

		if (nbPawns == 40) {
			// Center.add(auto); // Add an auto init.
			Center.add(save); // Save the grid in the 'Saves' folder
			if (toInit == 0 && game.getPlayer() != 2) {
				// If 2 players or grid loaded
				Center.add(load); // Search save in the 'Saves' folder
			}
			if (toInit != 2) {
				Center.add(play);// Launches the game with the grid
			}
		} else {
			Center.add(play);// Launches the game with the grid
		}

		final JFrame fen = this;

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (verifTheGrid()) {
					GridStart ngrid = new GridStart();
					ngrid.setGrid(gridPane1.getGrid());

					Date myDate = new Date(); // Default name
					DateFormat shortDateFormat = DateFormat
							.getDateTimeInstance(DateFormat.SHORT,
									DateFormat.SHORT);
					nom = (String) JOptionPane.showInputDialog(null,
							"Veuillez entrer un nom pour cette grille",
							"Sauvegarde", JOptionPane.QUESTION_MESSAGE, null,
							null, shortDateFormat.format(myDate));

					if (nom != null) { // null if cancel
						ngrid.setName(nom);
						ngrid.save();
						JOptionPane.showMessageDialog(null,
								"Votre grille est bien sauvegardee au nom de "
										+ nom, "Grille sauvee",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (verifTheGrid()) {
					if (!online) {
						if (toInit == 2) {
							toInit = 0;
						} else if (toInit == 1) {
							System.out.println("toInit = " + toInit);
						}
						game.setComplete(game.getComplete() + 1);
						Game gridPlayer = createGrid();
						gridPlayer.showGrid();
						game.placeTeam(gridPlayer.getGrid(), side);
						fen.dispose();
						initGame();
					} else {
						Game gridPlayer = createGrid();
						GridIA grid = new GridIA(gridPlayer.getGrid());
						client.sendTCP(grid);
						send = true;
						System.out.println("sended");
						client.addListener(new Listener() {
							public void received(Connection connection, Object object) {
								if(send=true){
									if(object instanceof Game){
										new WindowGame(game,client,Oplayer);
									}
								}
							}
						});
					}
				}
			}
		});

		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new WinManager();
				fen.dispose();
			}
		});

		// auto.addActionListener(new ActionListener() {// TODO //AUTO button
		// public void actionPerformed(ActionEvent arg0) {
		// GridIA gridIA = new GridIA(team);
		// for (int i = 0; i < 4; i++) {
		// for (int j = 0; j < 10; j++) {
		// gridPane1.set(i, j, gridIA.getPawn(i, j));
		// }
		// }
		// toInit = 1;
		// gridPane2.showGrid();
		// initPane2();
		// gridPane2.showGrid();
		// fen.repaint();
		// }
		// });

		pane2 = new PaneInitPawn(gridPane2);
		pane2.setPreferredSize(new Dimension(0, southHeight));

		pane1.setBorder(BorderFactory.createLineBorder(Color.black));
		pane2.setBorder(BorderFactory.createLineBorder(Color.black));
		add(pane1, BorderLayout.CENTER);

		add(Center, BorderLayout.NORTH);

		add(pane2, BorderLayout.SOUTH);

		this.setVisible(true);

		if (toInit != 1) {
			addPawnOfGrid();
		} else {
			deletePawnOfGrid();
		}
	}
	
	/**
	 * Verifies if the grid is complete and if the player can move at least one
	 * pawn.
	 * 
	 * @return true if the grid is complete and if the player can move at least
	 *         one pawn, <br/ >
	 *         false otherwise.
	 */
	private boolean verifTheGrid() {
		if (!pawns.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Tous les pions ne sont pas dans la grille.", "Attention",
					JOptionPane.WARNING_MESSAGE);
		} else if (nbPawns != 10 && !canPlay()) {
			JOptionPane.showMessageDialog(null,
					"Assurez-vous que vous pouvez au moins deplacer un pion.",
					"Attention", JOptionPane.WARNING_MESSAGE);
		} else {
			return true;
		}
		return false;
	}

	/**
	 * Initializes the gridPane1 and gridPane2.
	 */
	public void initPane2() {
		System.out.println("ini");
		if (nbPawns == 40) {
			gridPane2 = new Game(6, 2, 0);
			if (toInit != 1) {
				System.out.println("init1");
				gridPane2.set(0, 0, spyInit);
				gridPane2.set(0, 1, scoutInit);
				gridPane2.set(0, 2, minerInit);
				gridPane2.set(0, 3, sergeantInit);
				gridPane2.set(0, 4, lieutenantInit);
				gridPane2.set(0, 5, captainInit);
				gridPane2.set(1, 0, majorInit);
				gridPane2.set(1, 1, colonelInit);
				gridPane2.set(1, 2, generalInit);
				gridPane2.set(1, 3, marshalInit);
				gridPane2.set(1, 4, bombInit);
				gridPane2.set(1, 5, flagInit);
			} else {
				System.out.println("init2");
				gridPane2.set(0, 0, new NoPawn("spy"));
				gridPane2.set(0, 1, new NoPawn("scout"));
				gridPane2.set(0, 2, new NoPawn("miner"));
				gridPane2.set(0, 3, new NoPawn("sergeant"));
				gridPane2.set(0, 4, new NoPawn("lieutenant"));
				gridPane2.set(0, 5, new NoPawn("captain"));
				gridPane2.set(1, 0, new NoPawn("major"));
				gridPane2.set(1, 1, new NoPawn("colonel"));
				gridPane2.set(1, 2, new NoPawn("general"));
				gridPane2.set(1, 3, new NoPawn("marshal"));
				gridPane2.set(1, 4, new NoPawn("bomb"));
				gridPane2.set(1, 5, new NoPawn("flag"));
			}
		} else if (nbPawns == 10) {
			gridPane1 = new Game(8, 3, 0);
			gridPane2 = new Game(7, 1, 0);
			northHeight = (int) (Math.round(ySize * 0.65));
			southHeight = (int) (Math.round(ySize * 0.25));
			gridPane2.set(0, 0, spyInit);
			gridPane2.set(0, 1, scoutInit);
			scoutInit.setNbPawn(2);
			gridPane2.set(0, 2, minerInit);
			minerInit.setNbPawn(2);
			gridPane2.set(0, 3, generalInit);
			gridPane2.set(0, 4, marshalInit);
			gridPane2.set(0, 5, bombInit);
			bombInit.setNbPawn(2);
			gridPane2.set(0, 6, flagInit);
		}
	}

	/**
	 * Initializes the vector and the pawns.
	 */
	public void initialize() {
		pawns = APawn.createTeam(toInit, team, nbPawns);
		if (nbPawns == 40) {
			sergeantInit = new Sergeant(team);
			lieutenantInit = new Lieutenant(team);
			captainInit = new Captain(team);
			majorInit = new Major(team);
			colonelInit = new Colonel(team);
		}
		spyInit = new Spy(team);
		scoutInit = new Scout(team);
		minerInit = new Miner(team);
		generalInit = new General(team);
		marshalInit = new Marshal(team);
		bombInit = new Bomb(team);
		flagInit = new Flag(team);
		if (toInit == 1) {
			spyInit.setNbPawn(0);
			scoutInit.setNbPawn(0);
			minerInit.setNbPawn(0);
			sergeantInit.setNbPawn(0);
			lieutenantInit.setNbPawn(0);
			captainInit.setNbPawn(0);
			majorInit.setNbPawn(0);
			colonelInit.setNbPawn(0);
			generalInit.setNbPawn(0);
			marshalInit.setNbPawn(0);
			bombInit.setNbPawn(0);
			flagInit.setNbPawn(0);
		}
	}

	/**
	 * Verifies if the player can move at least one pawn at the beginning of the
	 * game.
	 * 
	 * @return true if the player can move, <br/>
	 *         false otherwise.
	 */
	public boolean canPlay() {
		int i = 0, j = 0;
		while (i < 10) {
			if (i != 0 && (i % 2 == 0)) {
				i += 2;
			}
			if (gridPane1.getPawn(0, i).getClass() == bombInit.getClass()) {
				j++;
			}
			i++;
		}
		return j != 6;
	}

	/**
	 * Returns the chosen pawn: this pawn is used for the count of each class of
	 * pawn.
	 * 
	 * @param pawn
	 *            A pawn of the class of the pawnInit expected.
	 * 
	 * @return The pawnInit expected.
	 */
	public APawn chosenPawn(APawn pawn) {
		APawn pawnInit = spyInit;
		if (pawn.getClass() == scoutInit.getClass()) {
			pawnInit = scoutInit;
		} else if (pawn.getClass() == minerInit.getClass()) {
			pawnInit = minerInit;
		} else if (nbPawns == 40 && pawn.getClass() == sergeantInit.getClass()) {
			pawnInit = sergeantInit;
		} else if (nbPawns == 40
				&& pawn.getClass() == lieutenantInit.getClass()) {
			pawnInit = lieutenantInit;
		} else if (nbPawns == 40 && pawn.getClass() == captainInit.getClass()) {
			pawnInit = captainInit;
		} else if (nbPawns == 40 && pawn.getClass() == majorInit.getClass()) {
			pawnInit = majorInit;
		} else if (nbPawns == 40 && pawn.getClass() == colonelInit.getClass()) {
			pawnInit = colonelInit;
		} else if (pawn.getClass() == generalInit.getClass()) {
			pawnInit = generalInit;
		} else if (pawn.getClass() == marshalInit.getClass()) {
			pawnInit = marshalInit;
		} else if (pawn.getClass() == bombInit.getClass()) {
			pawnInit = bombInit;
		} else if (pawn.getClass() == flagInit.getClass()) {
			pawnInit = flagInit;
		}
		return pawnInit;
	}

	/**
	 * Chooses the square where the pawn will be placed.
	 * 
	 * @param pawnInit
	 *            The pawn to place.
	 */
	public void chooseSquare(final APawn pawnInit) {
		pane1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (pawnInit.getClass() == currentPawn.getClass()) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						posX = e.getX();
						posY = e.getY();
						int[] res = getRes(gridPane1, pane1, posX, posY);
						int line = res[0];
						int row = res[1];
						if (gridPane1.getPawn(line, row) == null) {
							placePawn(pawnInit, line, row);
							repaint();
						}
					} else {
						deletePawnOfGrid();
					}
				}
			}
		});
	}

	/**
	 * Deletes the chosen pawn of the gridPane1.
	 */
	public void deletePawnOfGrid() {
		pane1.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON3) {
					posX = e.getX();
					posY = e.getY();
					int[] res = getRes(gridPane1, pane1, posX, posY);
					int line = res[0];
					int row = res[1];
					if (gridPane1.getPawn(line, row) != null) {
						APawn pawn = gridPane1.getPawn(line, row);
						deletePawn(chosenPawn(pawn), line, row);
						repaint();
					}
				}
				addPawnOfGrid();

			}
		});
	}

	/**
	 * Adds the chosen pawn of the gridPane2 to the gridPane1.
	 */
	public void addPawnOfGrid() {
		pane2.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 6; j++) {
							gridPane2.getPawn(i, j).setSelected(false);
						}
					}
					repaint();
					posX = e.getX();
					posY = e.getY();
					int[] res = getRes(gridPane2, pane2, posX, posY);
					int line = res[0];
					int row = res[1];
					APawn pawn = gridPane2.getPawn(line, row);
					pawn.setSelected(true);
					currentPawn = pawn;
					chooseSquare(chosenPawn(pawn));
					repaint();
				}

			}
		});
	}

	/**
	 * Places the pawn at the given coordinates.
	 * 
	 * @param pawnInit
	 *            The type of the pawn to place.
	 * 
	 * @param line
	 *            The line of the pawn.
	 * 
	 * @param row
	 *            The row of the pawn.
	 */
	public void placePawn(APawn pawnInit, int line, int row) {
		for (int i = 0; i < pawns.size(); i++) {

			if (!pawns.isEmpty() && pawnInit.getNbPawn() > 0
					&& pawns.elementAt(i).getLevel() == pawnInit.getLevel()) {
				gridPane1.set(line, row, pawns.elementAt(i));
				pawns.removeElementAt(i);
				nbPawn = pawnInit.getNbPawn();
				pawnInit.setNbPawn(--nbPawn);
				if (pawnInit.getNbPawn() == 0) {
					if (nbPawns == 40) {
						for (int x = 0; x < 2; x++) {
							for (int y = 0; y < 6; y++) {
								pawnShow = gridPane2.getPawn(x, y);
								if (pawnShow.getClass() == pawnInit.getClass()) {
									showPawn(x, y, pawnShow, true);
								}
							}
						}
					} else if (nbPawns == 10) {
						for (int y = 0; y < 7; y++) {
							pawnShow = gridPane2.getPawn(x, y);
							if (pawnShow.getClass() == pawnInit.getClass()) {
								showPawn(0, y, pawnShow, true);
							}
						}
					}
				}
				break;
			}
		}
	}

	/**
	 * Deletes the pawn at the given coordinates.
	 * 
	 * @param pawnInit
	 *            The type of the pawn to delete.
	 * 
	 * @param line
	 *            The line of the pawn.
	 * 
	 * @param row
	 *            The row of the pawn.
	 */
	public void deletePawn(APawn pawn, int line, int row) {
		if (pawn.getNbPawn() == 0) {
			if (nbPawns == 40) {
				for (int x = 0; x < 2; x++) {
					for (int y = 0; y < 6; y++) {
						pawnShow = gridPane2.getPawn(x, y);
						if (pawnShow.getNamePawn() == pawn.getNamePawn()) {
							showPawn(x, y, pawnShow, false);
						}
					}
				}
			} else if (nbPawns == 10) {
				for (int y = 0; y < 7; y++) {
					pawnShow = gridPane2.getPawn(0, y);
					if (pawnShow.getNamePawn() == pawn.getNamePawn()) {
						showPawn(0, y, pawnShow, false);
					}
				}
			}
		}

		gridPane1.set(line, row, null);
		pawns.add(newPawn(pawn));
		nbPawn = pawn.getNbPawn();
		pawn.setNbPawn(++nbPawn);
	}

	/**
	 * This method shows or hides the pawn given in parameter in the gridPane2.
	 * 
	 * @param x
	 *            The row of the grid.
	 * 
	 * @param y
	 *            The line of the grid.
	 * 
	 * @param pawnShow
	 *            The pawn who will be show or won't be.
	 * 
	 * @param show
	 *            The boolean who says if the pawn is shown or isn't.
	 */
	public void showPawn(int x, int y, APawn pawnShow, boolean show) {
		if (show) {
			gridPane2.set(x, y, noPawn = new NoPawn(pawnShow.getNamePawn()));
			pawnShow.setSelected(false);
		} else {
			gridPane2.set(x, y, initPawnIs(pawnShow));
		}
		repaint();
	}

	/**
	 * Returns the initPawn that fits with the one given.
	 * 
	 * @param pawn
	 *            The pawn of the same class of the wanted initPawn.
	 * 
	 * @return The wanted initPawn.
	 */
	public APawn initPawnIs(APawn pawn) {

		if (pawn.getNamePawn() == spyInit.getNamePawn()) {
			return spyInit;
		} else if (pawn.getNamePawn() == scoutInit.getNamePawn()) {
			return scoutInit;
		} else if (pawn.getNamePawn() == minerInit.getNamePawn()) {
			return minerInit;
		} else if (pawn.getNamePawn() == generalInit.getNamePawn()) {
			return generalInit;
		} else if (pawn.getNamePawn() == marshalInit.getNamePawn()) {
			return marshalInit;
		} else if (pawn.getNamePawn() == bombInit.getNamePawn()) {
			return bombInit;
		} else if (pawn.getNamePawn() == flagInit.getNamePawn()) {
			return flagInit;
		} else if (pawn.getNamePawn() == sergeantInit.getNamePawn()) {
			return sergeantInit;
		} else if (pawn.getNamePawn() == lieutenantInit.getNamePawn()) {
			return lieutenantInit;
		} else if (pawn.getNamePawn() == captainInit.getNamePawn()) {
			return captainInit;
		} else if (pawn.getNamePawn() == majorInit.getNamePawn()) {
			return majorInit;
		} else {
			return colonelInit;
		}
	}

	/**
	 * Returns a new instance of the given pawn.
	 * 
	 * @param pawn
	 *            The pawn which we want a new instance.
	 * 
	 * @return The new instance of the pawn.
	 */
	public APawn newPawn(APawn pawn) {
		APawn newPawn;

		if (pawn.getClass() == spyInit.getClass()) {
			newPawn = new Spy(team);
		} else if (pawn.getClass() == scoutInit.getClass()) {
			newPawn = new Scout(team);
		} else if (pawn.getClass() == minerInit.getClass()) {
			newPawn = new Miner(team);
		} else if (pawn.getClass() == generalInit.getClass()) {
			newPawn = new General(team);
		} else if (pawn.getClass() == marshalInit.getClass()) {
			newPawn = new Marshal(team);
		} else if (pawn.getClass() == bombInit.getClass()) {
			newPawn = new Bomb(team);
		} else if (pawn.getClass() == flagInit.getClass()) {
			newPawn = new Flag(team);
		} else if (pawn.getClass() == sergeantInit.getClass()) {
			newPawn = new Sergeant(team);
		} else if (pawn.getClass() == lieutenantInit.getClass()) {
			newPawn = new Lieutenant(team);
		} else if (pawn.getClass() == captainInit.getClass()) {
			newPawn = new Captain(team);
		} else if (pawn.getClass() == majorInit.getClass()) {
			newPawn = new Major(team);
		} else {
			newPawn = new Colonel(team);
		}

		return newPawn;
	}

	/**
	 * Returns the created grid.
	 * 
	 * @return The created grid from gridPane1.
	 */
	public Game createGrid() {
		return gridPane1;
	}
}
