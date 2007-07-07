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

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import dienste.observer.ObservableList;

/**
 * Speichert Karten sowohl aus der PD als auch aus der Applikaktionsschicht.
 * Speichert zusätzlich zustandsabhängige Daten, wie ob die Kartenauswahl
 * aktiviert ist.
 */
public class Karten extends Observable implements Observer, Iterable<Karte> {
	private boolean aktiv;
	private ObservableList<pd.regelsystem.Karte> pdKarten;
	private ObservableList<Karte> appKarten;

	public Karten(ObservableList<pd.regelsystem.Karte> karten) {
		this.pdKarten = karten;
		this.aktiv = false;
		this.appKarten = new ObservableList<Karte>();
		appKarten.addObserver(this);
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
		setChanged();
		notifyObservers();
	}

	public boolean getAktiv() {
		return aktiv;
	}

	public Karte get(int i) {
		return appKarten.get(i);
	}

	public int size() {
		return appKarten.size();
	}

	public Iterator<Karte> iterator() {
		return appKarten.iterator();
	}

	public void add(Karte karte) {
		pdKarten.add(karte.getKarte());
		appKarten.add(karte);
	}

	public void remove(Karte karte) {
		pdKarten.remove(karte.getKarte());
		appKarten.remove(karte);
	}

	public void clear() {
		for (Karte karte : appKarten) {
			if (karte.istAusgewaehlt()) {
				karte.setAusgewaehlt(false);
			}
		}
		pdKarten.clear();
		appKarten.clear();
	}
	
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}
}
