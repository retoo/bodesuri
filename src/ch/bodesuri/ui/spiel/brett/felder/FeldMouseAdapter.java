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


package ch.bodesuri.ui.spiel.brett.felder;


import java.awt.event.MouseEvent;

import ch.bodesuri.applikation.client.controller.Steuerung;
import ch.bodesuri.applikation.client.pd.Feld;
import ch.bodesuri.ui.geteiltes.ClickMouseAdapter;




/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
public class FeldMouseAdapter extends ClickMouseAdapter {
	private Steuerung steuerung;

	public FeldMouseAdapter(Steuerung steuerung) {
		this.steuerung = steuerung;
	}

	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		steuerung.hoverStart(((Feld2d) e.getComponent()).getFeld());
	}

	/**
	 * Hover-Ende-Effekt wird hier implementiert.
	 * 
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void mouseExited(MouseEvent e) {
		super.mouseExited(e);
		steuerung.hoverEnde(((Feld2d) e.getComponent()).getFeld());
	}

	/**
	 * Aus dem {@link Feld2d} das {@link Feld} extrahieren und an die
	 * {@link Steuerung} weiterleiten.
	 * 
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void clicked(MouseEvent e) {
		Feld feld = ((Feld2d) e.getComponent()).getFeld();
		steuerung.feldAuswaehlen(feld);
	}
}
