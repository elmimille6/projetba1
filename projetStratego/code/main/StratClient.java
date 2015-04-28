package main;

import gui.WindowGame;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class StratClient extends JFrame{
	private Client client=new Client();
	private String adIp = "127.0.0.1";
	private int state;
	
	
	public StratClient(){
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		askIp();
		connect();
		System.out.println("connect "+client.isConnected());
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
		if(state==1){
			JLabel lab = new JLabel("Vous êtes le joueur 1, veuillez attendre votre adversaire");
			lab.setFont(new Font(" TimesRoman ",Font.BOLD,13));
			this.add(lab);
		}
		if(state==2){
			JLabel lab = new JLabel("Partie en cours, veuillez ne pas fermer cette fenêtre");
			lab.setFont(new Font(" TimesRoman ",Font.BOLD,13));
			this.add(lab);
		}
		
	}

	private void close() {
		client.close();
		System.out.println("close");
	}

	private void connect() {
		// crée l'objet
		client.addListener(new Listener(){
			public void received(Connection connection,Object object){
				if(object instanceof String){
					System.out.println(object);
					if((String)object == "FULL"){
						JOptionPane jop = new JOptionPane();
						jop.showMessageDialog(null, "Connexion impossible, le serveur est plein, veuillez patienter", "Serveur plein", JOptionPane.INFORMATION_MESSAGE);
					}
					if((String)object == "GO"){
						System.out.println("GOGOGOGOGO");
						Game game = new Game();
						GridIA gr1 = new GridIA(1,1);
						GridIA gr2 = new GridIA(2,1);
						game.placeTeam(gr1.getGrid(), 1);
						game.placeTeam(gr2.getGrid(), 2);
						game.setPlayer(3);
						new WindowGame();
					}
					
				}
				if(object instanceof Integer){
					System.out.println("integer");
					if((Integer)object == 1){
						state = 1;
						System.out.println("state=1");
						state();
					}
					if((Integer)object == 2){
						state = 2;
						state();
					}
				}
			}
		});
		client.start();
	    try {
			client.connect(5000,adIp,54555,54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Connexion impossible, veuillez verifier l'adresse.", "Erreur", JOptionPane.ERROR_MESSAGE);
			askIp();
			connect();
		}
		
	}

	public void askIp(){
		JOptionPane jop = new JOptionPane();
		String adIp2 = jop.showInputDialog(null, "Veuillez taper l'adresse ip du serveur", "Adresse IP", JOptionPane.QUESTION_MESSAGE);
		if(!adIp2.isEmpty()){
			adIp=adIp2;
		}
	}
}
