package ui.verbinden;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
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
	private JTextField hostname;
	private JTextField port;
	private JTextField spielerName;
	private JProgressBar progressBar;
	private JPanel inputpanel;
	private JPanel buttonpanel;
	private JButton verbindenButton;
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
		abbrechenButton 	= new JButton("Abbrechen");
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
		AbstractAction abbrechenAction = new AbbrechenAction("Abbrechen", this);
		AbstractAction verbindenAction = new VerbindenAction("Verbinden", this);
		
		verbindenButton.setAction( verbindenAction );
		verbindenButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
									KeyStroke.getKeyStroke("ENTER"), "Verbinden");
		verbindenButton.getActionMap().put("Verbinden", verbindenAction);
		getRootPane().setDefaultButton(verbindenButton);

		abbrechenButton.setAction( abbrechenAction );
		abbrechenButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
									KeyStroke.getKeyStroke("ESCAPE"), "Abbrechen");
		abbrechenButton.getActionMap().put("Abbrechen", abbrechenAction);

		// Components hinzufügen
		buttonpanel.add(Box.createHorizontalGlue());
		buttonpanel.add(progressBar);
		buttonpanel.add(Box.createRigidArea(new Dimension(15, 0)));
		buttonpanel.add(abbrechenButton);
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
	public void abbrechen() {
		steuerung.beenden();
	}
	
	/**
	 * Verbindet zum Server.
	 */
	public void verbinden() {
		String host = hostname.getText();
		String spieler = spielerName.getText();
		int port_raw = Integer.valueOf(port.getText());

		// GUI in "Beschäftigt" Modus versetzen
		verbindenButton.setEnabled(false);
		hostname.setEnabled(false);
		port.setEnabled(false);
		spielerName.setEnabled(false);
		progressBar.setVisible(true);
		
		// Verbindung herstellen
		if (port_raw > 1024 && port_raw < 65536) {
			VerbindenView.this.steuerung.verbinde(host, port_raw, spieler);
		} else {
			JOptionPane.showMessageDialog(null, "Es sind nur Ports zwischen 1024 und\n65535 zugelassen.");
		}
		
		// GUI wieder "aufwecken"
		hostname.setEnabled(true);
		port.setEnabled(true);
		spielerName.setEnabled(true);
		progressBar.setVisible(false);
		verbindenButton.setEnabled(true);
	}
}
