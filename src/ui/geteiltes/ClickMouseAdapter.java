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


package ui.geteiltes;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MouseAdapter, der das gleiche Klickverhalten wie ein JButton hat.
 * 
 * Bei einem JButton ist es egal, ob zwischen dem Drücken der Maustaste
 * (mousePressed) und dem Loslassen (mouseReleased) eine Bewegung stattfindet,
 * oder sogar der ursprünglich angeklickte Bereich verlassen wird (mouseExited),
 * sofern er wieder betreten wird (mouseEntered).
 * 
 * Diese Klasse implementiert das JButton-Verhalten. Eine abgeleitete Klasse
 * muss die Methode {@link #clicked} überschreiben, die bei Klicks aufgerufen
 * wird. Wenn andere Methoden wie {@link #mouseEntered} überschrieben werden,
 * sollte super aufgerufen werden.
 */
public abstract class ClickMouseAdapter extends MouseAdapter {
	private boolean aktiv;
	private Component component;

	/**
	 * Methode, die in der abgeleiteten Klasse überschrieben werden muss und bei
	 * Klicks (nach JButton-Art) aufgerufen wird.
	 * 
	 * @param e
	 *            MouseEvent des Klicks
	 */
	public abstract void clicked(MouseEvent e);

	public void mouseExited(MouseEvent e) {
		aktiv = false;
	}

	public void mouseEntered(MouseEvent e) {
		if (e.getComponent() == component) {
			aktiv = true;
		}
	}

	public void mousePressed(MouseEvent e) {
		aktiv = true;
		component = e.getComponent();
	}

	public void mouseReleased(MouseEvent e) {
		if (aktiv) {
			clicked(e);
		}
		aktiv = false;
		component = null;
	}
}
