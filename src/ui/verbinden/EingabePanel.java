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
