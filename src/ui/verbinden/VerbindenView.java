package ui.verbinden;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import ui.ressourcen.Icons;
import applikation.client.controller.Steuerung;
import applikation.client.konfiguration.Konfiguration;

/**
 * JFrame, dient zur Eingabe der Informationen für den Server auf den verbindet
 * werden soll, sowie auch zur Erfassung der Spielerinformationen.
 */
public class VerbindenView extends JFrame {
	// Konstanten und Vorgabewerte
	private final static int FRAME_WIDTH 	= 400;
	private final static int FRAME_HEIGHT 	= 216;
	private final String DEFAULT_PORT;
	private final String DEFAULT_HOST;
	private final String DEFAULT_NAME;

	// Components
	private LimitiertesTextField hostname;
	private LimitiertesTextField port;
	private LimitiertesTextField spielerName;
	private JProgressBar progressBar;
	private JPanel inputpanel;
	private JPanel buttonpanel;
	private JButton verbindenButton;
	private JButton beendenButton;
	private JButton abbrechenButton;
	private JLabel verbindenIcon;
	private JLabel bodesuriIcon;
	private Steuerung steuerung;

	public VerbindenView(final Steuerung steuerung, final Konfiguration konfiguration) {
		// Instanzvariabeln initialisieren
		this.steuerung 		= steuerung;
		DEFAULT_PORT 		= String.valueOf( konfiguration.defaultPort );
		DEFAULT_HOST 		= konfiguration.defaultHost;
		DEFAULT_NAME 		= konfiguration.defaultName;
		
		hostname 			= new LimitiertesTextField(DEFAULT_HOST, 15);
		spielerName 		= new LimitiertesTextField( DEFAULT_NAME, 20);
		port 				= new LimitiertesTextField(DEFAULT_PORT, 5);
	
		buttonpanel 		= new JPanel();
		inputpanel 			= new EingabePanel(hostname, port, spielerName);
		progressBar 		= new JProgressBar(0, 100);
		verbindenButton 	= new JButton("Verbinden");
		beendenButton 		= new JButton("Beenden");
		abbrechenButton		= new JButton("Abbrechen");
		verbindenIcon 		= new JLabel(Icons.VERBINDEN);
		bodesuriIcon 		= new JLabel(Icons.BODESURI_START);
		
		// View initialisieren
		progressBar.setVisible(false);
		progressBar.setIndeterminate(true);

		setTitle("Bodesuri - Verbinden");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setResizable(false);

		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.LINE_AXIS));
		buttonpanel.setBorder(new EmptyBorder(0, 0, 15, 15));

		// Actions definieren und Tastenbefehle binden
		verbindenButton.setAction( new VerbindenAction("Verbinden", this) );
		verbindenButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
									KeyStroke.getKeyStroke("ENTER"), "Verbinden");
		verbindenButton.getActionMap().put( "Verbinden", verbindenButton.getAction() );
		getRootPane().setDefaultButton(verbindenButton);

		beendenButton.setAction( new BeendenAction("Beenden", this) );
		beendenButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
									KeyStroke.getKeyStroke("ESCAPE"), "Beenden");
		beendenButton.getActionMap().put( "Beenden", beendenButton.getAction() );
		
		abbrechenButton.setEnabled(false);
		abbrechenButton.setAction( new AbbrechenAction("Abbrechen", this) );

		// Components hinzufügen und layouten
		buttonpanel.add(Box.createHorizontalGlue());
		buttonpanel.add(progressBar);
		buttonpanel.add(Box.createRigidArea(new Dimension(15, 0)));
		buttonpanel.add(beendenButton);
		buttonpanel.add(Box.createRigidArea(new Dimension(15, 0)));
		buttonpanel.add(verbindenButton);
		add(bodesuriIcon, BorderLayout.NORTH);
		add(inputpanel, BorderLayout.CENTER);
		add(buttonpanel, BorderLayout.SOUTH);
		add(verbindenIcon, BorderLayout.WEST);

		// Debug: Autologin auslösen, fals erwünscht
		if (konfiguration.debugAutoLogin)
			verbindenButton.doClick();

		// View anordnen und zentrieren
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Beendet den Controller und somit die gesamte Applikation.
	 */
	public void beenden() {
		steuerung.beenden();
	}
	
	public void abbrechen() {
		setzeEingabeSperre(false);
	}
	
	/**
	 * Versucht die Verbindung zum Server herzustellen mit den angegebenen Parametern
	 * Hostname, Port und Spielername.
	 */
	public void verbinden() {
		String host = hostname.getText();
		String spieler = spielerName.getText();
		int port_raw = Integer.valueOf(port.getText());

		setzeEingabeSperre(true);
		
		// Verbindung herstellen. Der Controller führt die Verbindung aus.
		if (port_raw > 1024 && port_raw < 65536) {
			VerbindenView.this.steuerung.verbinde(host, port_raw, spieler);
		} else {
			JOptionPane.showMessageDialog(null, "Es sind nur Ports zwischen 1024 und\n65535 zugelassen.");
		}
	}
	
	public void verbindungFehlgeschlagen() {
		setzeEingabeSperre(false);
	}
	
	/**
	 * Das View in den Modus "Gesperrt" versetzen, indem keine Buttons und
	 * Felder mehr aktiviert sind und eine Statusanzeige angezeigt wird oder
	 * diese Sperre aufheben.
	 * 
	 * @param istGesperrt 
	 * 				Falls true, wird das View gesperrt, bei false entsperrt.
	 */
	private void setzeEingabeSperre(boolean istGesperrt) {
		Component zuWechseln = istGesperrt ? abbrechenButton : beendenButton;
		buttonpanel.remove(3);
		buttonpanel.add(zuWechseln, 3);
		
		verbindenButton.setEnabled( !istGesperrt );
		hostname.setEnabled( !istGesperrt );
		port.setEnabled( !istGesperrt );
		spielerName.setEnabled( !istGesperrt );
		progressBar.setVisible( istGesperrt );
		
		hostname.processFocusEvent( new FocusEvent(hostname, getDefaultCloseOperation(), istGesperrt) );
	}
}
