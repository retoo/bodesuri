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


package ch.bodesuri.ui.geteiltes;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Diese Klasse kümmert sich um die Positionierung der Bildelemente auf dem Spielfeld.
 * Es ermöglicht ein Bild zentriert auf einen Koordinatenpunkt zu setzten.
 */
public class BLabel extends JLabel {
	private final int dxFinal;
	private final int dyFinal;
	private final Point relativerMittelpunkt;
	private Point absoluterMittelpunkt;

	public BLabel(Icon icon) {
		this(icon, 0, 0);
	}

	/**
	 * Konstrukor mit Icon, X-Koordinaten und Y-Koordinaten
	 * 
	 * @param icon Bild des Labels
	 * @param dx X-Koordinate des Labels
	 * @param dy Y-Koordinate des Labels
	 */
	public BLabel(Icon icon, int dx, int dy) {
		super(icon);
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();

		this.dxFinal = (width / 2) + dx;
		this.dyFinal = (height / 2) + dy;

		setSize(width, height); /* TONIGHT WE SWING IN HELL */

		this.absoluterMittelpunkt = getLocation();
		this.relativerMittelpunkt = new Point(width / 2, height /2);
	}

	/**
	 * Konstruktor mit Icon und Point(x,y)
	 * 
	 * @param icon Bild des Labels
	 * @param p Koordinatenpunkt als Point(x,y)
	 */
	public BLabel(Icon icon, Point p) {
	    this(icon, 0, 0);
	    zentriereAuf(p);
    }

	/**
	 * Das Labeld wird auf diesen Punkt zentriert platziert.
	 * 
	 * @param position Position, auf dem das Bild zentriert wird.
	 */
	public void zentriereAuf(Point position) {
		absoluterMittelpunkt = position;
		setLocation(position.x - dxFinal, position.y - dyFinal);
	}

	/**
	 * Das Label wird auf diesem Punkt zentriert platziert.
	 * 
	 * @param x X-Koordinate der Positionierung.
	 * @param y Y-Koordinate der Positionierung.
	 */
	private void zentriereAuf(int x, int y) {
		absoluterMittelpunkt = new Point(x, y);
		setLocation(x - dxFinal, y - dyFinal);
    }

	/**
	 * Das Label wird zur Koordinate Point(x,y) bewegt.
	 * 
	 * @param ziel Koordinatenpunkt, wohin das Label bewegt wird.
	 */
	public void bewegeNach(Point ziel) {
		Point start = absoluterMittelpunkt;

		double distanz = start.distance(ziel);

		int steps = (int) Math.round(distanz / 20);
		double dx = ziel.x - start.x;
		double dy = ziel.y - start.y;

		double stepX = dx / steps;
		double stepY = dy / steps;

		for (int i = 1; i <= steps; i++) {
			int curX = start.x + (int) Math.round(i * stepX);
			int curY = start.y + (int) Math.round(i * stepY);

			zentriereAuf(curX, curY);
			try {
	            Thread.sleep(20);
            } catch (InterruptedException e) {
            	throw new RuntimeException(e);
            }
		}

		zentriereAuf(ziel);
	}
	
	public Point getRelativerMittelpunkt() {
		return relativerMittelpunkt;
	}
}
