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


package ch.bodesuri.ui.spiel.brett;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.bodesuri.applikation.client.pd.Spieler;
import ch.bodesuri.ui.ressourcen.Icons;


/**
 * Dient zur Darstellung des Zustandes eines Spielers, ob er am Zug ist oder
 * aufgeben musste.
 */
public class SpielerZustandView extends JPanel implements Observer {
	private Spieler spieler;
	private JLabel icon;

	public SpielerZustandView(Spieler spieler, Point position) {
		this.spieler = spieler;

		setLayout(new GridBagLayout());
		setOpaque(false);
		setSize(70, 70);
		setLocation(position.x - 35, position.y - 35);

		icon = new JLabel();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		add(icon, c);

		spieler.addObserver(this);
	}

	public void update(Observable o, Object arg) {
		if (spieler.amZug()) {
			icon.setIcon(Icons.AM_ZUG);
		} else if (spieler.hatAufgegeben()) {
			icon.setIcon(Icons.FAHNE);
		} else {
			icon.setIcon(null);
		}
	}
}
