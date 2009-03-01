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

import java.awt.event.MouseEvent;

import ch.bodesuri.applikation.client.controller.Steuerung;
import ch.bodesuri.ui.geteiltes.ClickMouseAdapter;



/**
 * Erweiterung des ClickMouseAdapter. Bei einem einfachen Klick der Karte wird
 * sie ausgewählt markiert. Bei einem Doppelklick wird die Karte ausgetausch,
 * sofern der richtige Zustand vorhanden ist.
 */
public class KarteMouseAdapter extends ClickMouseAdapter {
	private Steuerung steuerung;

	public KarteMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	/**
	 * Bei einem einfachen Klick wird die Karte ausgewählt markiert.
	 */
	public void clicked(MouseEvent evt) {
		KarteView karteView = (KarteView) evt.getComponent();
		steuerung.karteAuswaehlen(karteView.getKarte());
	}

	/**
	 * Bei einem Doppelklick wird die Karte mit dem Partner ausgetauscht, sofern
	 * sich der Spieler im richtigen Zustand befindet.
	 */
	public void mouseClicked(MouseEvent evt) {
		super.mouseClicked(evt);
		if (evt.getClickCount() == 2) {
			steuerung.kartenTauschBestaetigen();
		}
	}
}
