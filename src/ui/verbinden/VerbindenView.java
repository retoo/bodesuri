package ui.verbinden;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import ui.ressourcen.Icons;
import applikation.client.controller.Steuerung;
import applikation.client.konfiguration.Konfiguration;

/**
 * JFrame, dient zur Eingabe der Informationen für den Server auf den verbindet
 * werden soll, sowie auch zur Erfassung der Spielerinformationen.
 */
public class VerbindenView extends JFrame {
	// Konstanten und Vorgabewerte
	private final static int FRAME_WIDTH 		= 400;
	private final static int FRAME_HEIGHT 		= 216;
	private final static String DEFAULT_PORT 	= "7788";
	private final static String DEFAULT_HOST 	= "localhost";

	// Components
	private JTextField hostname 	= new InputTextField(DEFAULT_HOST, 15);
	private JTextField port 		= new InputTextField(DEFAULT_PORT, 5);
	private JTextField spielerName;
	private JProgressBar progressBar = new JProgressBar(0, 100);
	private JPanel inputpanel;
	private JPanel buttonpanel 		= new JPanel();
	private JButton verbindenButton = new JButton("Verbinden");
	private JButton abbrechenButton = new JButton("Abbrechen");
	private JLabel verbindenIcon = new JLabel(Icons.VERBINDEN);
	private JLabel bodesuriIcon = new JLabel(Icons.BODESURI_START);
	private Steuerung steuerung;

	/* TODO: Pascal: Willst du die defaultKonfiguration noch einbauen? (-reto) */
	public VerbindenView(final Steuerung steuerung, final Konfiguration konfiguration) {
		// Initialisierung
		this.steuerung = steuerung;
		spielerName = new InputTextField(konfiguration.defaultName, 20);
		progressBar.setVisible(false);
		progressBar.setIndeterminate(true);

		setTitle("Bodesuri - Verbinden");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setResizable(false);

		// View auf Monitor zentrieren
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (monitor.width - getSize().width - FRAME_WIDTH) / 2;
		int y = (monitor.height - getSize().height - FRAME_HEIGHT) / 2;
		setLocation(x, y);

		// Components modifizieren
		inputpanel = new InputPanel(hostname, port, spielerName);
		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.LINE_AXIS));
		buttonpanel.setBorder(new EmptyBorder(0, 0, 15, 15));

		// Actions definieren und Tastenbefehle binden
		AbstractAction abbrechenAction = new AbstractAction("Abbrechen") {
			public void actionPerformed(ActionEvent e) {
				steuerung.beenden();
			}
		};
		AbstractAction verbindenAction = new AbstractAction("Verbinden") {
			public void actionPerformed(ActionEvent e) {
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
		};
		verbindenButton.setAction(verbindenAction);
		verbindenButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
									KeyStroke.getKeyStroke("ENTER"), "Verbinden");
		verbindenButton.getActionMap().put("Verbinden", verbindenAction);

		abbrechenButton.setAction(abbrechenAction);
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


		/* Debug: Autlogin auslösen falls gewünscht */

		if (konfiguration.debugAutoLogin) {
			verbindenButton.doClick();
		}

		// View anzeigen
		getRootPane().setDefaultButton(verbindenButton);
		pack();
	}




	private static class InputTextField extends JTextField {
		private final Color FOCUS_GAINED 	= new Color(220, 223, 228);
		private final Color FOCUS_LOST 		= new Color(255, 255, 255);

		public InputTextField(String text, int limit) {
			super();
			setDocument(new MyDocument(limit));
			setText(text);
		}

		/**
		 * Beim betreten und verlassen eines TextField wird diese Methode
		 * ausgeführt.
		 *
		 * @see javax.swing.JFormattedTextField#processFocusEvent
		 * @param e
		 *            the focus event
		 */
		protected void processFocusEvent(FocusEvent e) {
			super.processFocusEvent(e);
			switch (e.getID()) {
			case FocusEvent.FOCUS_GAINED:
				setBackground(FOCUS_GAINED);
				break;
			case FocusEvent.FOCUS_LOST:
				setBackground(FOCUS_LOST);
				break;
			}

		}

		private static class MyDocument extends PlainDocument {
			private final int LIMIT;

			/**
			 * Erstellt ein neues Dokument
			 *
			 * @param limit
			 *            maximal erlaubte Anzahl Zeichen
			 */
			public MyDocument(int limit) {
				LIMIT = limit;
			}

			/**
			 * Ueberschriebene Methode, gibt eine Meldung aus, falls zuviele
			 * Zeichen eingegeben wurden.
			 */
			public void insertString(int offset, String text, AttributeSet attr)
					throws BadLocationException {
				if (text.length() == 0)
					return;
				if (getLength() + text.length() > LIMIT) {
					JOptionPane.showMessageDialog(null, "Zu viele Zeichen");
				} else {
					super.insertString(offset, text, attr);
				}
			}
		}
	}

	private static class InputPanel extends JPanel {
		public InputPanel(JTextField hostname, JTextField port,
				JTextField spielerName) {
			setLayout(new GridLayout(3, 2, 0, 5));
			setBorder(new EmptyBorder(5, 15, 15, 15));

			// Components hinzufügen
			add(new JLabel("Server:"));
			add(hostname);
			add(new JLabel("Port:"));
			add(port);
			add(new JLabel("Spieler:"));
			add(spielerName);
		}
	}
}
