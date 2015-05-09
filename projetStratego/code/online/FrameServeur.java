package online;

import java.awt.Font;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import main.Game;
import main.GridIA;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class FrameServeur extends JFrame {
	Server server;
	int nbrCon;
	Game game = new Game();
	Connection pl1, pl2;
	JLabel lab = new JLabel();

	public FrameServeur() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 400);
		this.setTitle("Serveur");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		String ip="";
		try {
			ip = InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		game.setPlayer(3);
//		Log.set(Log.LEVEL_DEBUG);
		server = new Server(6000, 6000);
		Kryo kryo = server.getKryo();
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
		server.start();
		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
//				System.out.println("received");
				if (object instanceof String) {
					System.out.println("string");
					String request = (String) object;
					System.out.println(request);
				} 
				else if (object instanceof int[]){
					int[] res = (int[]) object;
					if(connection==pl1){
						pl2.sendTCP(res);
					}else if(connection==pl2){
						pl1.sendTCP(res);
					}
				}
				
				
				else if (object instanceof Game) {
					System.out.println("received game serveur");
					game = (Game) object;
					game.setGameN(1);
					if (game.getNextTeam() == 1) {
						pl1.sendTCP(game);
						System.out.println("send game pl1");
					} else if (game.getNextTeam() == 2) {
						pl2.sendTCP(game);
						System.out.println("send game pl2");
					}
					if(game.win()!=0){
						pl1.close();
						pl2.close();
						game=new Game();
						game.setPlayer(3);
					}
				} 
				else if (object instanceof int[][]){
					
				}
				else if (object instanceof GridIA) {

					System.out.println("received grid ia");
					if (game.getComplete() != 2) {
						if (connection == pl1) {
							game.placeTeam(((GridIA) object).getGrid(), 1);
							game.setComplete(game.getComplete() + 1);
							System.out.println("complete pl1");
							game.showGrid();
						}

						else if (connection == pl2) {
							game.placeTeam(((GridIA) object).getGrid(), 2);
							game.setComplete(game.getComplete() + 1);
							System.out.println("complete pl2");
							game.showGrid();
						}
					}
					if (game.getComplete() == 2) {
						server.sendToAllTCP(game);
						System.out.println("send game");
						game.showGrid();
					}
				} else {
					// System.out.println("else");
					// System.out.println(object);
				}
			}

			public void connected(Connection connection) {
				if(nbrCon==0){
					connection.sendTCP(-1);
				}
				else if(nbrCon == 1){
					connection.sendTCP(-2);
				}
				nbrCon++;
				System.out.println("connecté");
				// connection.sendTCP("serveur atteint");
				if (nbrCon == 3) {
					System.out.println("full close");
					connection.sendTCP("FULL");
					connection.close();
				}

				else if (nbrCon == 1) {
					pl1 = connection;
					pl1.sendTCP(1);
				} else if (nbrCon == 2) {
					pl2 = connection;
					pl1.sendTCP(2);
					server.sendToAllTCP("GO");
					// game=new Game();
					// game.setPlayer(3);
					// GridIA grid1 = new GridIA(1);
					// GridIA grid2 = new GridIA(2);
					// game.placeTeam(grid1.getGrid(), 1);
					// game.placeTeam(grid2.getGrid(), 2);
					// server.sendToAllTCP(game);
				}
				connection.sendTCP(nbrCon);
			}

			public void disconnected(Connection connection) {
				System.out.println("deconnecté");

				if (nbrCon == 2) {
					server.sendToAllTCP("STOP");
					game = new Game();
					game.setPlayer(3);
					if (connection == pl1 && pl2 != null) {
						pl2.close();
					} else {
						pl1.close();
					}
				}

				nbrCon--;
			}
		});
		System.out.println("serveur OK");
	
	
		lab.setText("Votre adresse ip locale est "+ip);
		lab.setFont(new Font(" TimesRoman ", Font.BOLD, 13));
		this.add(lab);
	}

	

	private void close() {
		System.out.println("serveur close");
		server.close();
		System.exit(0);
	}
	
	

}