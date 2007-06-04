package ui.verbinden;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ui.ClientController;


/**
 * JFrame, dient zur Eingabe der Informationen für den Server auf den verbindet werden soll,
 * sowie auch zur Erfassung der Spielerinformationen.
 */
public class VerbindenView extends JFrame {
	private JTextField hostname;
	private JTextField port;
	private JTextField spielerName;
	private ClientController controller;

	public VerbindenView(ClientController controller, String defaultName) {
		this.controller = controller;

		setTitle("Bodesuri - Verbinden");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

		setLayout(new GridLayout(4, 2));
		Dimension groesse = new Dimension(300, 150);
		setPreferredSize(groesse);

		add(new JLabel("Server:"));
		hostname = new JTextField("localhost");
		add(hostname);
		add(new JLabel("Port:"));
		port = new JTextField("7788");
		add(port);
		add(new JLabel("Spieler:"));
		spielerName = new JTextField(defaultName);
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

		add(ok);

		pack();
	}


}
