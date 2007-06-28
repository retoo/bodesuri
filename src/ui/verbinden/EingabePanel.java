package ui.verbinden;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Das Input-Panel beinhaltet die Textfeler und Labels für die Eingabe der
 * Verbindungsdaten.
 */
class EingabePanel extends JPanel {
	/**
	 * Erstellt ein InputPanel, welches als Container für die Labels und
	 * TestFields im VerbindenView dient.
	 * 
	 * @param hostname
	 *            TextField für Eingabe des Hostnamens.
	 * @param port
	 *            TextField für Eingabe des Ports.
	 * @param spielerName
	 *            TextField für Eingabe des Spielernamens.
	 */
	public EingabePanel(JTextField hostname, JTextField port,
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
