package main;

import gui.InitWindow;
import gui.WindowGame;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class StratClient extends JFrame {

	private static final long serialVersionUID = -1140319931747839365L;
	public Client client = new Client(6000, 6000);
	private String adIp = "127.0.0.1";
	private int state, Oplayer;
	private JLabel lab = new JLabel();
	private StratClient stratclient = this;
	

	public StratClient() {
//		Log.set(Log.LEVEL_DEBUG);
		Kryo kryo = client.getKryo();
		kryo.register(main.Game.class);
		kryo.register(main.GridIA.class);
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
//		Log.set(Log.LEVEL_DEBUG);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// askIp();
		connect();
		System.out.println("connect " + client.isConnected());
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				close();
			}
		});

		this.setSize(400, 400);
		this.setTitle("Client");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		state();

	}

	private void state() {
		if (state == 1) {
			lab.setText("Vous Ãªtes le joueur "+Oplayer+", veuillez attendre votre adversaire");
			lab.setFont(new Font(" TimesRoman ", Font.BOLD, 13));
			this.add(lab);
			this.repaint();
			// this.setContentPane(panel);
		}
		if (state == 2) {
			lab.setText("<html>Partie en cours, veuillez ne pas fermer cette fenètre <br>Vous êtes le joueur "+Oplayer+"</html>");
			lab.setFont(new Font(" TimesRoman ", Font.BOLD, 13));
			this.add(lab);
			this.repaint();
			// this.setContentPane(panel);
		}
		if (state == 3) {
			lab.setText("Veuillez attendre, votre adversaire crée sa grille");
			lab.setFont(new Font(" TimesRoman ", Font.BOLD, 13));
			this.add(lab);
			this.repaint();
		}

	}

	private void close() {
		client.close();
		System.out.println("closeClient");
		this.dispose();
	}

	private void connect() {
		// crï¿½e l'objet
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (state ==3) {
					if (object instanceof Game) {
						Game game = (Game) object;
						if (game.getGameN() == 0) {
							game.setGameN(1);
							new WindowGame((Game) object,client, Oplayer);
							state=2;
							state();
						}
					}
				}
				if (object instanceof String) {
					System.out.println("String");
					System.out.println(object);
					if (((String) object).equals("FULL")) {
						JOptionPane
								.showMessageDialog(
										null,
										"Connexion impossible, le serveur est plein, veuillez patienter",
										"Serveur plein",
										JOptionPane.INFORMATION_MESSAGE);
						System.out.println("client full close");
						close();
					}
					if (((String) object).equals("GO")) {
//						System.out.println("GOGOGOGOGO");
						state = 2;
						state();
						new InitWindow(stratclient, Oplayer);

					}

				}
				if (object instanceof Integer) {
					System.out.println("integer");
					System.out.println(object);
					if ((Integer) object == 1) {
						state = 1;
						// System.out.println("state=1");
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
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Connexion impossible, veuillez verifier l'adresse.",
					"Erreur", JOptionPane.ERROR_MESSAGE);
			askIp();
			connect();
		}

	}

	public void askIp() {
		String adIp2 = JOptionPane.showInputDialog(null,
				"Veuillez taper l'adresse ip du serveur", "Adresse IP",
				JOptionPane.QUESTION_MESSAGE);
		if (!adIp2.isEmpty()) {
			adIp = adIp2;
		}
	}
	
	public void setState(int state){
		this.state=state;
		state();
	}
	
	
}
