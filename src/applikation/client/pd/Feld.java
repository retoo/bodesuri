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
import java.util.Observer;

import pd.spiel.spieler.Figur;

/**
 * Dekoriert ein Feld aus der PD.
 * Speichert zusätzlich zustandsabhängige Daten, wie ob das Feld ausgewählt ist,
 * das Feld gehovert ist, ein Geist auf dem Feld ist oder das Feld Teil eines
 * markierten Weges ist, und wie lange der Weg ist.
 */
public class Feld extends Observable implements Observer {
	private pd.spiel.brett.Feld feld;

	private boolean ausgewaehlt;
	private boolean hover;
	private boolean geist;
	private int wegLaenge;

	public Feld(pd.spiel.brett.Feld feld) {
		this.feld = feld;

		this.hover = false;
		this.ausgewaehlt = false;
		this.geist = false;
		this.wegLaenge = 0;

		feld.addObserver(this);
	}

	public pd.spiel.brett.Feld getFeld() {
		return feld;
	}

	public Figur getFigur() {
		return feld.getFigur();
	}

	public Object getNummer() {
		return feld.getNummer();
	}

	public boolean istBesetzt() {
		return feld.istBesetzt();
	}

	public boolean istNormal() {
		return feld.istNormal();
	}

	public boolean istLager() {
		return feld.istLager();
	}

	/* Feld-Zustände */

	public void setAusgewaehlt(boolean zustand) {
		this.ausgewaehlt = zustand;
		setChanged();
		notifyObservers(zustand);
	}

	public void setHover(boolean zustand) {
		this.hover = zustand;
		setChanged();
		notifyObservers(ausgewaehlt);
	}
	
	public void setWegLange(int wegLaenge){
		this.wegLaenge = wegLaenge;
		setChanged();
		notifyObservers();
	}

	public void setGeist(boolean zustand) {
		this.geist = zustand;
		setChanged();
		notifyObservers();
	}

	public boolean istAusgewaehlt() {
		return ausgewaehlt;
	}

	public boolean istHover() {
		return hover;
	}
	
	public int getWegLaenge() {
		return wegLaenge;
	}

	public boolean istGeist() {
		return geist;
	}
	
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
}
