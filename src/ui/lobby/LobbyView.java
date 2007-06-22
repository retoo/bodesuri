package ui.lobby;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.spiel.brett.SpielerView;
import ui.spiel.chat.ChatView;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Chat;
import applikation.client.pd.Spieler;

/**
 * JFrame für das Lobby, in dem die Spieler auf den Spielstart warten.
 */
public class LobbyView extends JFrame implements Observer {
	// Konstanten und Vorgabewerte
	private final static int FRAME_WIDTH = 450;
	private final static int FRAME_HEIGHT = 400;
	private final static String introTitel = "Willkommen in der Bodesuri-Lobby";
	private final static String introText = "<html>Hier siehst Du die verbundenen " +
			"Spieler, mit denen Du<br>bereits chatten kannst.</html>";
	private HashMap<Object, SpielerView> spielerViews;
	private Vector<pd.spieler.Spieler> pdSpieler;
	private JPanel spielerPanel;
	private JLabel introTextLabel;
	private JLabel introTitelLabel;

	public LobbyView(List<Spieler> spieler, Steuerung steuerung, Chat chat) {
		// Initialisierung
		spielerViews = new HashMap<Object, SpielerView>();
		pdSpieler = new Vector<pd.spieler.Spieler>();
		introTextLabel = new JLabel(introText);
		introTitelLabel = new JLabel(introTitel);
		introTitelLabel.setFont(introTitelLabel.getFont().deriveFont(Font.BOLD));
		spielerPanel = new JPanel();
		
		for (Spieler s : spieler) {
			SpielerView sv = new SpielerView(s);	// SpielverViews erstellen
			spielerViews.put(s.getSpieler().hashCode(), sv);
			s.getSpieler().addObserver(this); 		// Observer auf PD.Spieler
			pdSpieler.add(s.getSpieler());
		}

		setTitle("Bodesuri - Lobby");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setResizable(false);

		// View auf Monitor zentrieren
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (monitor.width - getSize().width - FRAME_WIDTH) / 2;
		int y = (monitor.height - getSize().height - FRAME_HEIGHT) / 2;
		setLocation(x, y);

		spielerPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
		spielerPanel.setLayout(new BoxLayout(spielerPanel, BoxLayout.Y_AXIS));
		// Panels dem Frame hinzufügen
		spielerPanel.add(introTitelLabel);
		spielerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		spielerPanel.add(introTextLabel);
		spielerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		for (Entry<Object, SpielerView> sv : spielerViews.entrySet() ) {
			spielerPanel.add(sv.getValue());
			spielerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			spielerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		add(spielerPanel);
		add(new ChatView(chat, steuerung));

		// View anzeigen
		pack();
	}

	public void update(Observable beigetretenerSpieler, Object arg1) {
		SpielerView sv = spielerViews.get( ((pd.spieler.Spieler)beigetretenerSpieler).hashCode() );
		if (sv != null) { // falls Spieler in HashMap gefunden wurde
			sv.setSpielerName( beigetretenerSpieler.toString() );
		}
	}
	
	public void dispose() {
		// Entferne Observer in PD.Spieler
		for (pd.spieler.Spieler s : pdSpieler) {
			s.deleteObserver(this);
		}
		super.dispose();
	}
}
