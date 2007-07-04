/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package ui.verbinden;

import java.awt.BorderLayout;
import java.awt.Dimension;

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
	private final Konfiguration konfiguration;

	private class EingabeInfo {
		public String host = "";
		public int port = 0;
		public String spieler = "";
		public boolean istEingabeGueltig = true;
		public String hinweis = "";
		public EingabeInfo(String hinweis, boolean istEingabeGueltig,
							String host, String spieler, int port) {
			this.hinweis = hinweis;
			this.istEingabeGueltig = istEingabeGueltig;
			this.host = host;
			this.spieler = spieler;
			this.port = port;
		}
	}

	// Components
	private LimitiertesTextField hostTextField;
	private LimitiertesTextField portTextField;
	private LimitiertesTextField spielerTextField;
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
		this.konfiguration	= konfiguration;

		hostTextField 		= new LimitiertesTextField(konfiguration.defaultHost, 15);
		spielerTextField 	= new LimitiertesTextField( konfiguration.defaultName, 20);
		portTextField 		= new LimitiertesTextField(String.valueOf( konfiguration.defaultPort ), 5);

		buttonpanel 		= new JPanel();
		inputpanel 			= new EingabePanel(hostTextField, portTextField, spielerTextField);
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

		beendenButton.setAction( new BeendenAction("Beenden", steuerung) );
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

	/**
	 * Versucht die Verbindung zum Server herzustellen mit den angegebenen Parametern
	 * Hostname, Port und Spielername.
	 */
	public void verbinden() {
		EingabeInfo info = validiereEingaben( hostTextField.getText(),
											  spielerTextField.getText(),
											  portTextField.getText());

		if ( info.istEingabeGueltig ) {
			setzeEingabeSperre(true);	// wird im GUIController wieder aufgehoben, falls
										// nicht erfolgreich verbunden wurde.
			konfiguration.defaultHost = info.host;
			konfiguration.defaultName = info.spieler;
			konfiguration.defaultPort = info.port;

			// Verbindung herstellen. Der Controller führt die Verbindung aus.
			VerbindenView.this.steuerung.verbinde(info.host, info.port, info.spieler);
		} else {
			JOptionPane.showMessageDialog(null, info.hinweis);
		}
	}

	/**
	 * Validiert die Eingabe, der einzelnen Felder.
	 * 
	 * @param host Hostname oder IP-Adresse des Servers.
	 * @param spieler Spielername
	 * @param port_raw Port auf dem der Server horcht.
	 * @return Eingaben als EingabeInfo
	 */
	private EingabeInfo validiereEingaben(String host, String spieler, String port_raw) {
		String hinweis = "";
		boolean istGueltig = true;
		int port = 0;

		// Überprüfung des Hostnamens
		if (host.equals("")) {
			istGueltig = false;
			hinweis += "\n - Der Hostname darf nicht leer sein.";
		}

		// Überprüfung der Port-Eingabe
		try {
			port = Integer.parseInt(port_raw);
			if (port < 1 || port > 65535)
				throw new NumberFormatException();
		} catch (NumberFormatException ex) {
			istGueltig = false;
			hinweis += "\n - Der Port muss zwischen 1 und 65535 liegen.";
		}

		// Überprüfung des Spielernamens
		if (spieler.equals("")) {
			istGueltig = false;
			hinweis += "\n - Der Spielername darf nicht leer sein.";
		}

		if ( !istGueltig )
			hinweis = "Folgende Eingaben waren nicht korrekt:" + hinweis;

		return new EingabeInfo(hinweis, istGueltig, host, spieler, port);
	}

	/**
	 * Das View in den Modus "Gesperrt" versetzen, indem keine Buttons und
	 * Felder mehr aktiviert sind und eine Statusanzeige angezeigt wird oder
	 * diese Sperre aufheben.
	 *
	 * @param istGesperrt
	 * 				Falls true, wird das View gesperrt, bei false entsperrt.
	 */
	public void setzeEingabeSperre(boolean istGesperrt) {
//		Component zuWechseln = istGesperrt ? abbrechenButton : beendenButton;
//		buttonpanel.remove(3);
//		buttonpanel.add(zuWechseln, 3);
		beendenButton.setEnabled(!istGesperrt);

		verbindenButton.setEnabled( !istGesperrt );
		hostTextField.setEnabled( !istGesperrt );
		portTextField.setEnabled( !istGesperrt );
		spielerTextField.setEnabled( !istGesperrt );
		progressBar.setVisible( istGesperrt );
	}
}
