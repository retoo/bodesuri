package ui.spiel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ui.GUIController;
import ui.spiel.brett.SpielView;
import ui.spiel.chat.ChatView;

/**
 * Das GUI des Spiels.
 */
public class BodesuriView extends JFrame {
	public BodesuriView(GUIController controller, String spielerName) {
		// Layout setzen
		setTitle("Bodesuri - Spiel (" + spielerName + ")");
		setName("Bodesuri");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setNativeLookAndFeel();
		setLayout(new BorderLayout());

		// Views
		SpielView spielView = new SpielView(controller);
		ChatView chatView = new ChatView(controller);
		
		// Layout zusammenstellen
		getContentPane().add(spielView, BorderLayout.NORTH);
		getContentPane().add(chatView, BorderLayout.CENTER);

		// GUI anzeigen
		pack();
	}

	/**
	 * Setzt den nativen Look & Feel für Windows. Auf allen anderen Plattformen
	 * wird eine Exception geworfen, die ignoriert wird.
	 */
	private static void setNativeLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
	}
}
