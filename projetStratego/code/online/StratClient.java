package online;

import gui.InitWindow;
import gui.WindowGame;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.Game;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * This class is the frame with the client to join an online game.
 * 
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 */
public class StratClient extends JFrame {

	private static final long serialVersionUID = -1140319931747839365L;
	public Client client = new Client(6000, 6000);
	private String adIp = "127.0.0.1";
	private int state, Oplayer;
	private JLabel lab = new JLabel();
	private StratClient stratclient = this;
	private boolean local = false;

	/**
	 * The constructor of the class.
	 */
	public StratClient() {
		init();
	}

	/**
	 * Initializes the client.
	 */
	public void init() {
		Log.set(Log.LEVEL_NONE);
		Kryo kryo = client.getKryo();
		kryo.register(main.Game.class);
		kryo.register(main.GridAI.class);
		kryo.register(pawn.APawn.class);
		kryo.register(pawn.APawn[].class);
		kryo.register(pawn.APawn[][].class);
		kryo.register(pawn.Bomb.class);
		kryo.register(pawn.Captain.class);
		kryo.register(pawn.Colonel.class);
		kryo.register(pawn.Flag.class);
		kryo.register(pawn.General.class);
		kryo.register(pawn.Lake.class);
		kryo.register(pawn.Lieutenant.class);
		kryo.register(pawn.Major.class);
		kryo.register(pawn.Marshal.class);
		kryo.register(pawn.Miner.class);
		kryo.register(pawn.NoPawn.class);
		kryo.register(pawn.Scout.class);
		kryo.register(pawn.Sergeant.class);
		kryo.register(pawn.Spy.class);
		kryo.register(java.util.Vector.class);
		kryo.register(int[].class);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (this.local == false) {
			askIp();
		}
		connect();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				client.close();
			}
		});

		this.setSize(450, 450);
		this.setTitle("Client");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		state();

	}

	/**
	 * The constructor of the class when this computer hosts the game.
	 * 
	 * @param b
	 *            true if this computer hosts the game so don't ask the ip but
	 *            us "127.0.0.1".
	 */
	public StratClient(boolean b) {
		this.local = b;
		init();
	}

	/**
	 * Method called to check and change the text display on the frame.
	 */
	private void state() {
		if (state == 1) {
			lab.setText("Vous etes le joueur " + Oplayer
					+ ", veuillez attendre votre adversaire");
			lab.setFont(new Font(" TimesRoman ", Font.BOLD, 13));
			this.add(lab);
			this.repaint();
		}
		if (state == 2) {
			lab.setText("<html>Partie en cours, veuillez ne pas fermer cette fenetre <br>Vous etes le joueur "
					+ Oplayer + "</html>");
			lab.setFont(new Font(" TimesRoman ", Font.BOLD, 13));
			this.add(lab);
			this.repaint();
		}
		if (state == 3) {
			lab.setText("Veuillez attendre, votre adversaire cree sa grille");
			lab.setFont(new Font(" TimesRoman ", Font.BOLD, 13));
			this.add(lab);
			this.repaint();
		}

	}

	/**
	 * Method to close the client and the frame.
	 */
	public void close() {
		client.close();
		this.dispose();
	}

	/**
	 * Method used to try to connect the client at the server.
	 */
	private void connect() {
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (state == 3) {
					if (object instanceof Game) {
						Game game = (Game) object;
						if (game.getGameN() == 0) {
							game.setGameN(1);
							new WindowGame((Game) object, client, Oplayer);
							state = 2;
							state();
						}
					}
				}
				if (object instanceof String) {
					if (((String) object).equals("FULL")) {
						JOptionPane
								.showMessageDialog(
										null,
										"Connexion impossible, le serveur est plein, veuillez patienter",
										"Serveur plein",
										JOptionPane.INFORMATION_MESSAGE);
						close();
					}
					if (((String) object).equals("GO")) {
						state = 2;
						state();
						new InitWindow(stratclient, Oplayer);

					}

				}
				if (object instanceof Integer) {
					if ((Integer) object == 1) {
						state = 1;
						state();
					}
					if ((Integer) object == 2) {
						state = 2;
						state();
					}
					if ((Integer) object == -1) {
						Oplayer = 1;
					}
					if ((Integer) object == -2) {
						Oplayer = 2;
					}
				}
			}

		});
		client.start();

		try {
			client.connect(5000, adIp, 54555, 54777);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Connexion impossible, veuillez verifier l'adresse.",
					"Erreur", JOptionPane.ERROR_MESSAGE);
			askIp();
			connect();
		}

	}

	/**
	 * Method to use to ask with a GetIp dialog box the IP address.
	 */
	public void askIp() {
		try {
			GetIp jop = new GetIp(this, "IP", true);
			String adIp2 = jop.showDialog();
			if (!adIp2.isEmpty()) {
				adIp = adIp2;
			}
		} catch (Exception e) {

		}
	}

	/**
	 * Method to set the state of the frame to change the displayed text.
	 * 
	 * @param state
	 *            The state of the window:<br/>
	 *            1 if waiting after second player,<br/>
	 *            2 if game is running or<br/>
	 *            3 if waiting after the initialization of the game from the
	 *            opponent.
	 */
	public void setState(int state) {
		this.state = state;
		state();
	}

}
