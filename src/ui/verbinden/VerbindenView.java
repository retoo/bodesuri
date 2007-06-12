package ui.verbinden;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import ui.GUIController;

/**
 * JFrame, dient zur Eingabe der Informationen für den Server auf den verbindet
 * werden soll, sowie auch zur Erfassung der Spielerinformationen.
 */
public class VerbindenView extends JFrame {
	private JTextField hostname;
	private JTextField port;
	private JTextField spielerName;
	private GUIController controller;
	
	private final int FRAME_WIDTH 		= 300;
	private final int FRAME_HEIGHT 		= 150;
	private final String DEFAULT_PORT	= "7788";
	private final String DEFAULT_HOST	= "localhost";

	public VerbindenView(GUIController controller, final String DEFAULT_NAME) {
		this.controller = controller;

		setTitle("Bodesuri - Verbinden");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

		setLayout(new GridLayout(4, 2));
		Dimension groesse = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
		setPreferredSize(groesse);
		
		// zentriere View
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize(); 
		int x = (monitor.width - getSize().width - FRAME_WIDTH) / 2; 
		int y = (monitor.height - getSize().height - FRAME_HEIGHT) / 2; 
		setLocation(x, y);

		add(new JLabel("Server:"));
		hostname = new JTextField( DEFAULT_HOST );
		add(hostname);
		add(new JLabel("Port:"));
		port = new JTextField( DEFAULT_PORT );
		add(port);
		add(new JLabel("Spieler:"));
		spielerName = new JTextField(DEFAULT_NAME);
		add(spielerName);

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String host = hostname.getText();
				String spieler = spielerName.getText();
				Integer port_raw = Integer.valueOf(port.getText());

				VerbindenView.this.controller.verbinde(host, port_raw, spieler);
			}
		});
		ok.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "pressed");

		add(ok);
		getRootPane().setDefaultButton(ok);
		pack();
	}
}
