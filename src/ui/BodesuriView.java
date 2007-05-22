package ui;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import applikation.client.StateMachineClient;

import ui.chat.ChatView;
import ui.info.DeckView;
import ui.info.SpielerView;
import ui.lobby.LobbyView;
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
		setTitle("Bodesuri");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

		// Panels
		// Box scheint das beste Layout zu sein.
		// - Border geht nicht wegen dem Resize.
		// - GridBag ist zu kompliziert, weil wir unabhängige Spalten haben.
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		Box links = Box.createVerticalBox();
		links.add(Box.createVerticalGlue());
		links.add(new ChatView());

		Box rechts = Box.createVerticalBox();
		rechts.add(new SpielerView());
		rechts.add(new DeckView());
		rechts.add(Box.createVerticalGlue());

		add(links);
		add(rechts);
		add(Box.createHorizontalGlue());
		pack();
		
		LobbyView lobbyView = new LobbyView(queue);
		lobbyView.setVisible(true);
	}

	public static void main(String[] args) throws UnknownHostException,
	                                      IOException, VerbindungWegException {
		
		EventQueue queue = new EventQueue();
		
		StateMachine sync = new StateMachineClient(queue);
		
		new BodesuriView(queue).setVisible(true);
		
		sync.run();
	}
}
