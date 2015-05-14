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
import com.esotericsoftware.minlog.Log;
/**
 * The class of the dialog box which ask you for the IP address when you want to connect on a server
 * @author CAREDDA Giuliano, DUCOBU Alexandre
 *
 */
public class GetIp extends JDialog {

	private static final long serialVersionUID = 5101917374232225862L;
	private String ip = "";
	private final static JFrame nn = null;
	JTextField text;
/**
 * The constructor of the class.
 * @param parent  the Frame from which the dialog is displayed but unused just here to override the JDialog constructor
 * @param title the String to display in the dialog's title bar
 * @param modal specifies whether dialog blocks user input to other top-level windows when shown. If true, the modality type property is set to DEFAULT_MODALITY_TYPE otherwise the dialog is modeless
 */
	public GetIp(JFrame parent, String title, boolean modal) {
		super(nn, title, modal);
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}
/**
 * Get the String ip which is in the JTextField and clear it 
 * @return The ip which is the JTextField
 */
	public String showDialog() {
		this.setVisible(true);
		String ipp = "";
		for (int i = 0; i < this.ip.length(); i++) {
			if (ip.charAt(i) != '/') {
				ipp += ip.charAt(i);
			}
		}
		return ipp;
	}
/**
 * initialize the component of the dialog box
 */
	private void initComponent() {
		Log.set(Log.LEVEL_NONE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		JLabel lab = new JLabel(
				"Entrez l'adresse ip de l'hebergeur ou scanner le reseau local");
		panel.add(lab);
		text = new JTextField();
		text.setPreferredSize(new Dimension(150, 30));
		;
		JPanel p = new JPanel();
		p.add(text);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1, 2));
		panel2.add(p);
		JButton scan = new JButton("scan local");
		JPanel p1 = new JPanel();
		p1.add(scan);
		panel2.add(p1);
		JButton goBtn = new JButton("ok");
		this.getRootPane().setDefaultButton(goBtn);
		goBtn.requestFocus();
		JButton cancel = new JButton("annuler");
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 2));
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		p2.add(goBtn);
		p3.add(cancel);
		panel3.add(p2);
		panel3.add(p3);

		goBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ip = text.getText();
				setVisible(false);
			}
		});
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}
		});

		scan.addActionListener(new ActionListener() {
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
