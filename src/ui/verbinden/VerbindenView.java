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

import applikation.client.events.VerbindenEvent;

import dienste.automat.EventQueue;


public class VerbindenView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField hostname;
	private JTextField port;
	private JTextField spielerName;
	private EventQueue queue;

	public VerbindenView(EventQueue queue) {
		this.queue = queue;
		
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
		spielerName = new JTextField("Spieler");
		add(spielerName);

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String host = hostname.getText();
				String spieler = spielerName.getText();
				Integer port_raw = Integer.valueOf(port.getText());
				
				VerbindenEvent e = new VerbindenEvent(host, port_raw, spieler);
				
				VerbindenView.this.queue.enqueue(e);
            }
		});
		
		add(ok);
		
		pack();
	}


}
