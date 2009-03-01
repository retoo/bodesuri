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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import ch.bodesuri.applikation.client.pd.Karte;
import ch.bodesuri.ui.ressourcen.Icons;



/**
 * JLabel, dient zur Darstellung der einzelnen Karten, die im DeckView verwaltet
 * werden.
 */
public class KarteView extends JLabel implements Observer {
	private Karte karte;
	private Point position;
	private MouseListener mouseListener;
	private KartenAuswahl kartenAuswahl;

	public KarteView(Point position, MouseListener mouseListener,
	                 KartenAuswahl kartenAuswahl) {
		this.position = position;
		this.mouseListener = mouseListener;
		this.kartenAuswahl = kartenAuswahl;

		Dimension groesse = new Dimension(80, 100);

		setBounds(position.x, position.y, groesse.width, groesse.height);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);
	}

	public void update(Observable o, Object arg) {
		if (karte.istAusgewaehlt()) {
			kartenAuswahl.setPosition(position);
			kartenAuswahl.setVisible(true);
		} else {
			kartenAuswahl.setVisible(false);
		}
	}

	public void setKarte(Karte karte) {
		if (this.karte != null) {
			this.karte.deleteObserver(this);
		}
		this.karte = karte;
		if (karte != null) {
			setIcon(Icons.getIcon(karte));
			addMouseListener(mouseListener);
			karte.addObserver(this);
		} else {
			removeMouseListener(mouseListener);
			setIcon(Icons.KARTEN_PLATZHALTER);
		}
	}

	public Karte getKarte() {
		return karte;
	}
}
