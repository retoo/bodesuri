package ui.spiel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ui.ClientController;
import ui.spiel.brett.SpielView;
import ui.spiel.info.InfoView;

/**
 * Das GUI des Spieles...
 *
 */
public class BodesuriView extends JFrame {
	public BodesuriView(ClientController controller, String spielerName) {
		setTitle("Bodesuri - Spiel (" + spielerName + ")");
		setName("Bodesuri");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setNativeLookAndFeel();
		setLayout(new BorderLayout());

		// Layout aus den diversen JPanels zusammensetzen
		SpielView spielView = new SpielView(controller);
		InfoView infoView = new InfoView(controller);
		getContentPane().add(spielView, BorderLayout.CENTER);
		getContentPane().add(infoView, BorderLayout.EAST);

		// GUI anzeigen
		pack();
	}

    /**
     * Setzt den nativen Look & Feel fŸr das Windows Betriebssystem. Auf allen anderen wird
     * eine Exception geworfen, die ignoriert wird.
     */
    private static void setNativeLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch( Exception e ) { }
    }
}
