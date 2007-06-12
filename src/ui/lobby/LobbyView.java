package ui.lobby;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import pd.spieler.Spieler;

/**
 * JFrame für das Lobby, in dem die Spieler auf den Spielstart warten.
 */
public class LobbyView extends JFrame {
	public LobbyView(List<Spieler> spieler) {
		setTitle("Bodesuri - Lobby");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new JLabel("Hier kommt mal eine Lobby..."));
	}
}
