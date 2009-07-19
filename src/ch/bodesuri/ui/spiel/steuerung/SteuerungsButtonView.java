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


package ch.bodesuri.ui.spiel.steuerung;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.bodesuri.applikation.client.controller.Steuerung;
import ch.bodesuri.applikation.client.pd.Spiel;
import ch.bodesuri.applikation.client.pd.SteuerungsZustand;


/**
 * Diese Klasse dient zur Darstellung der erweiterten Spielsteuerung. Es werden
 * Buttons dargestellt, die zum "Aufgeben" oder "Tauschen" der Karten dienen.
 */
public class SteuerungsButtonView extends JPanel implements Observer {
	Steuerung steuerung;
	Spiel spiel;
	CardLayout layout;
	JButton aufgeben;
	JButton tauschen;

	public SteuerungsButtonView(Steuerung steuerung, Spiel spiel) {
		this.steuerung = steuerung;
		this.spiel = spiel;

		layout = new CardLayout();
		setLayout(layout);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);

		spiel.addObserver(this);

		aufgeben = new JButton("Aufgeben");
		aufgeben
				.setToolTipText("Alle Karten ablegen und diese Runde aussetzen.");
		aufgeben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SteuerungsButtonView.this.steuerung.aufgeben();
			}
		});

		tauschen = new JButton("Tauschen");
		tauschen.setToolTipText("Karte dem Partner geben");
		tauschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SteuerungsButtonView.this.steuerung.kartenTauschBestaetigen();
			}
		});

		// Auf dem Mac sollen die Buttons transpartent sein (sie haben sonst
		// weisse Ecken). Unter Windows sieht dies aber nicht gut aus.
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.startsWith("mac os x")) {
			aufgeben.setOpaque(false);
			tauschen.setOpaque(false);
		}

		add(new JLabel(), "");
		add(aufgeben, "aufgeben");
		add(tauschen, "tauschen");
	}

	public void update(Observable o, Object arg) {
		SteuerungsZustand sz = spiel.getSteuerungsZustand();
		if (sz == SteuerungsZustand.AUFGEBEN) {
			layout.show(this, "aufgeben");
		} else if (sz == SteuerungsZustand.TAUSCHEN) {
			layout.show(this, "tauschen");
		} else {
			layout.show(this, "");
		}
	}
}
