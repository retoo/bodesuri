package ui.lobby;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * JFrame für das Lobby, in dem die Spieler auf den Spielstart warten.
 */
public class LobbyView extends JFrame {
	private static final long serialVersionUID = 1L;

	public LobbyView() {
		setTitle("Bodesuri - Lobby");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new JLabel("Hier kommt mal eine Lobby..."));
	}
}
