package online;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.esotericsoftware.kryonet.Client;

public class GetIp extends JDialog{
	private String ip="";
	private boolean sendData;
	private final static JFrame nn = null;
	private StratClient parent;
	JTextField text;
	
	
	public GetIp(StratClient parent, String title, boolean modal){
		super(nn,title,modal);
		this.parent = parent;
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}

	public String showDialog(){
		this.sendData = false;
		this.setVisible(true);
		String ipp="";
		for(int i=0;i<this.ip.length();i++){
			if(ip.charAt(i)!='/'){
				ipp+=ip.charAt(i);
			}
		}
		return ipp;
	}

	private void initComponent() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		JLabel lab = new JLabel("Entrez l'adresse ip de l'hebergeur ou scanner le reseau local");
		panel.add(lab);
		text = new JTextField();
		text.setPreferredSize(new Dimension(150,30));;
		JPanel p=new JPanel();
		p.add(text);
		JPanel panel2= new JPanel();
		panel2.setLayout(new GridLayout(1,2));
		panel2.add(p);
		JButton scan = new JButton("scan local");
		JPanel p1=new JPanel();
		p1.add(scan);
		panel2.add(p1);
		JButton goBtn = new JButton("ok");
		JButton cancel = new JButton("annuler");
		JPanel panel3=new JPanel();
		panel3.setLayout(new GridLayout(1,2));
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();
		p2.add(goBtn);
		p3.add(cancel);
		panel3.add(p2);
		panel3.add(p3);
		
		goBtn.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0) {        
		        ip = text.getText();
		        setVisible(false);
		      }
		});
	    cancel.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  System.exit(0);
	        
	      }      
	    });
	    
	    
	    scan.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0) {
		    	Client cli = new Client();
		    	InetAddress address = cli.discoverHost(54777, 5000);
		  	    System.out.println(address + "scan ip");
		  	    text.setText(address.toString());
		        
		      }      
		    });
	    
	    
	    
	    
	    
	    
	    panel.add(lab);
	    panel.add(panel2);
	    panel.add(panel3);
	    this.setContentPane(panel);
	}

}
