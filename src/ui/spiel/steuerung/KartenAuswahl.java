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

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JLabel;

import ui.ressourcen.Icons;

/**
 * JLabel, dient zur Darstellung einer ausgewählten Karte. Dazu wird ein
 * weiteres JLabel benötigt, um die Karte von den anderen unterscheiden zu
 * lassen.
 */
public class KartenAuswahl extends JLabel {
	public void setPosition(Point pos) {
		Icon icon = Icons.KARTEN_AUSWAHL;
		setIcon(icon);
		setBounds(pos.x - 10, pos.y - 10, icon.getIconWidth(), icon
				.getIconHeight());
		setPreferredSize(new Dimension(100, 125));
		setMaximumSize(new Dimension(100, 125));
		setMinimumSize(new Dimension(100, 125));
	}
}
