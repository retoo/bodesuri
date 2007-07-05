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


package ui.spiel.steuerung;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Karte;
import applikation.client.pd.Karten;
import dienste.observer.ListChangeEvent;
import dienste.observer.ListChangeEvent.ListChangeType;

/**
 * Informationen über die ausgewählte Karte.
 */
public class KarteGewaehltView extends JPanel implements Observer {
	private JLabel name;
	private JTextArea beschreibung;

	public KarteGewaehltView(Steuerung steuerung, Karten karten) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);

		name = new JLabel();
		Font nameFont = name.getFont().deriveFont(Font.BOLD, 16);
		name.setFont(nameFont);
		name.setForeground(Color.white);
		name.setOpaque(false);

		beschreibung = new JTextArea();
		// Dialog-Schrift setzen. Unter Windows haben wir sonst Courier als
		// Schriftart.
		Font beschreibungFont = new Font("Dialog", beschreibung.getFont()
				.getStyle(), beschreibung.getFont().getSize());
		beschreibung.setFont(beschreibungFont);
		beschreibung.setLineWrap(true);
		beschreibung.setOpaque(false);
		beschreibung.setEnabled(false);
		beschreibung.setWrapStyleWord(true);
		beschreibung.setDisabledTextColor(Color.white);
		beschreibung.setRows(3);

		zeigeKeineKarte();

		name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		beschreibung.setAlignmentX(JTextArea.LEFT_ALIGNMENT);

		add(name);
		add(beschreibung);

		karten.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		if (arg instanceof ListChangeEvent) {
			ListChangeEvent change = (ListChangeEvent) arg;
			if (change.changeType == ListChangeType.CHANGED) {
				Karte karte = (Karte) change.changedObject;
				if (karte.istAusgewaehlt()) {
					zeigeKarte(karte);
				} else {
					zeigeKeineKarte();
				}
			}
		}
	}

	/**
	 * Name und Beschreibung der jeweiligen ausgewählten Karte darstellen.
	 * 
	 * @param karte Karte, die ausgewählt wurde.
	 */
	private void zeigeKarte(Karte karte) {
		name.setText(karte.toString());
		beschreibung.setText(karte.getRegelBeschreibung());
	}

	/**
	 * Wenn keine Karte ausgewählt wurde, wird ein leerer Informationsbereich
	 * dargestellt.
	 */
	private void zeigeKeineKarte() {
		// Zeilenumbrüche sind ein Hack, damit die Grösse richtig berechnet
		// wird.
		name.setText("\n");
		beschreibung.setText("\n\n");
	}
}
