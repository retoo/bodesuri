package ui.spiel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import ui.spiel.info.InfoView;
import applikation.client.BodesuriClient;

/**
 * Das GUI des Spieles...
 *
 */
public class BodesuriView extends JFrame {

	private static final long serialVersionUID = 1L;

	public BodesuriView(BodesuriClient automat) {
		setTitle("Bodesuri - Spiel (" + automat.spielerName + ")");
		setName("Bodesuri");
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setNativeLookAndFeel();
		setLayout(new BorderLayout());
		
		// Layout aus den diversen JPanels zusammensetzen
		SpielView spielView = new SpielView(automat);
		InfoView infoView = new InfoView(automat);
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
