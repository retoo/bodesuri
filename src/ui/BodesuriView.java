package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pd.Spiel;
import pd.spieler.Spieler;
import ui.brett.BrettView;
import ui.chat.ChatView;
import ui.info.DeckView;
import ui.info.SpielerView;
import applikation.client.BodesuriClient;
import dienste.netzwerk.VerbindungWegException;
import dienste.statemachine.EventQueue;
import dienste.statemachine.StateMachine;

/**
 * Das GUI des Spieles...
 * 
 */
public class BodesuriView extends JFrame {

	private static final long serialVersionUID = 1L;

	public BodesuriView(EventQueue queue) throws UnknownHostException,
	        IOException, VerbindungWegException {
		setTitle("Bodesuri - Spiel");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

		Spiel spiel = new Spiel();
		Spieler spieler = new Spieler(1, spiel);

		// Panels
		// Box scheint das beste Layout zu sein.
		// - Border geht nicht wegen dem Resize.
		// - GridBag ist zu kompliziert, weil wir unabhängige Spalten haben.
		setLayout(new GridBagLayout());

		// Box links = Box.createVerticalBox();
		// links.add(new BrettView(spiel, spieler, null));
		// links.add(new ChatView());
		//
		// Box rechts = Box.createVerticalBox();
		// rechts.add(new SpielerView());
		// rechts.add(new DeckView());
		// rechts.add(Box.createVerticalGlue());
		//
		// add(links);
		// add(rechts);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		add(new BrettView(spiel, spieler, null), gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		add(new ChatView(), gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(new SpielerView(), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		add(new DeckView(), gbc);

		pack();

	}

	public static void main(String[] args) throws UnknownHostException,
	                                      IOException, VerbindungWegException {
		EventQueue queue = new EventQueue();
		StateMachine sync = new BodesuriClient(queue);

		sync.run();
	}
}
