package ui.lobby;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.spiel.chat.ChatView;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Chat;
import applikation.client.pd.Spieler;
import applikation.geteiltes.SpielerInfo;

/**
 * JFrame für das Lobby, in dem die Spieler auf den Spielstart warten.
 */
public class LobbyView extends JFrame {
	// Konstanten und Vorgabewerte
	private final static int FRAME_WIDTH 		= 600;
	private final static int FRAME_HEIGHT 		= 400;
	private final static JPanel lobbyPanel = new JPanel();
	private static JLabel verbundeneSpielerLabel = new JLabel();
	private Vector<String> verbundeneSpieler = new Vector<String>();
	
	public LobbyView(List<Spieler> spieler, Steuerung steuerung, Chat chat) {
		setTitle("Bodesuri - Lobby");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setResizable(false);
		
		for (Spieler s : spieler) {
			verbundeneSpieler.add(s.getName());
		}
		aktualisiereSpielerListe();
		
		lobbyPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		lobbyPanel.add(new JLabel("Dies ist erst das Layout, der Chat wirft noch eine Exception, da noch nicht richtig eigebaut!"));
		lobbyPanel.add( verbundeneSpielerLabel );
		
		// View auf Monitor zentrieren
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (monitor.width - getSize().width - FRAME_WIDTH) / 2;
		int y = (monitor.height - getSize().height - FRAME_HEIGHT) / 2;
		setLocation(x, y);

		add(lobbyPanel, BorderLayout.CENTER);
		add(new ChatView(chat, steuerung), BorderLayout.SOUTH);
		
		// View anzeigen
		pack();
	}

	public void zeigeSpieler(Vector<SpielerInfo> spielers) {
		for (SpielerInfo spielerInfo : spielers) {
			if ( !verbundeneSpieler.contains(spielerInfo.name) ) {
				verbundeneSpieler.add(spielerInfo.name);
			}
		}
		aktualisiereSpielerListe();
	}
	
	private void aktualisiereSpielerListe() {
		String aktuelleSpielerListe = "Verbundene Spieler sind: ";
		for (String spieler : verbundeneSpieler) {
			aktuelleSpielerListe += spieler + " | ";
		}
		verbundeneSpielerLabel.setText(aktuelleSpielerListe);
	}
}
