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


package applikation.client.pd;

import java.util.Observable;

import pd.regelsystem.Regel;
import pd.regelsystem.karten.Joker;

/**
 * Dekoriert eine Karte aus der PD.
 * Speichert zusätzlich zustandsabhängige Daten, wie ob die Karte ausgewählt ist.
 */
public class Karte extends Observable {
	private pd.regelsystem.Karte karte;
	
	private boolean ausgewaehlt;

	public Karte(pd.regelsystem.Karte karte) {
		this.karte = karte;
		ausgewaehlt = false;
	}

	public boolean istAusgewaehlt() {
		return ausgewaehlt;
	}

	public void setAusgewaehlt(boolean ausgewaehlt) {
		this.ausgewaehlt = ausgewaehlt;
		setChanged();
		notifyObservers(ausgewaehlt);
	}

	public String getIconName() {
		if (karte instanceof Joker) {
			return "joker";
		} else {
			String name = karte.getClass().getSimpleName() + "_" + karte.getFarbe();
			return name.toLowerCase();
		}
	}

	public String getRegelBeschreibung() {
		return karte.getRegelBeschreibung();
	}

	public pd.regelsystem.Karte getKarte() {
		return karte;
	}

	public Regel getRegel() {
		return karte.getRegel();
    }

	public String toString() {
		return karte.toString();
	}
}
