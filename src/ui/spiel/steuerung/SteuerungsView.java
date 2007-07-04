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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.ressourcen.Icons;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Spiel;

/**
 * Dient zur Darstellung der erweiteren Steuerung des Spielverlaufs. Die Karten,
 * Informationstext und die Buttons zum "Tauschen" und "Aufgeben" werden in
 * dieser Klasse instanziert.
 */
public class SteuerungsView extends JPanel {
	public SteuerungsView(Steuerung steuerung, Spiel spiel) {
		setLayout(new GridBagLayout());
		setOpaque(false);

		// Views
		DeckView deckView = new DeckView(steuerung, spiel.spielerIch
				.getKarten());
		KarteGewaehltView karteGewaehltView = new KarteGewaehltView(steuerung,
				spiel.spielerIch.getKarten());
		SteuerungsButtonView steuerungsButtonView = new SteuerungsButtonView(
				steuerung, spiel);
		JLabel logo = new JLabel(Icons.LOGO);

		// Layout zusammenstellen
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.weighty = 0.0;
		add(deckView, c);

		c.weighty = 0.0;
		add(karteGewaehltView, c);

		c.weighty = 1.0;
		add(steuerungsButtonView, c);

		c.weighty = 0.0;
		add(logo, c);
	}
}
