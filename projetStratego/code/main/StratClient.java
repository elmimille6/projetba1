package main;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;

public class StratClient extends JFrame{
	Client client;
	String adIp = "127.0.0.1";
	
	
	
	public StratClient(){
		askIp();
		connect();
		System.out.println("connect "+client.isConnected());
	}
	
	private void connect() {
		client =new Client();// crée l'objet
	    client.start();
	    try {
			client.connect(5000,adIp,54555,54777);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
