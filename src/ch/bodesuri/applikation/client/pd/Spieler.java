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


package ch.bodesuri.applikation.client.pd;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ch.bodesuri.pd.regelsystem.Regelsystem;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.pd.spiel.spieler.Figur;
import ch.bodesuri.pd.spiel.spieler.SpielerFarbe;


/**
 * Dekoriert einen Spieler aus der PD.
 * Speichert zusätzlich zustandsabhängige Daten, wie ob der Spieler am Zug ist
 * und ob er Aufgegeben hat.
 * Verfügt ausserdem über eigene Karten welche die Karten aus der PD dekorieren.
 */
public class Spieler extends Observable implements Observer {
	private ch.bodesuri.pd.spiel.spieler.Spieler spieler;
	private Karten karten;

	private boolean amZug;
	private boolean hatAufgegeben;

	public Spieler(ch.bodesuri.pd.spiel.spieler.Spieler spieler) {
		this.spieler = spieler;
		this.karten = new Karten(spieler.getKarten());
		this.amZug = false;
		this.hatAufgegeben = false;
		spieler.addObserver(this);
	}

	public boolean kannZiehen() {
		return Regelsystem.kannZiehen(spieler);
	}

	public void setAmZug(boolean amZug) {
		this.amZug = amZug;
		setChanged();
		notifyObservers(amZug);
	}

	public boolean hatAufgegeben() {
		return hatAufgegeben;
	}

	public void setHatAufgebeben(boolean hatAufgebeben) {
		this.hatAufgegeben = hatAufgebeben;
		setChanged();
		notifyObservers();
	}

	/** 
	 *  for junit tests
	 */
	public void setKarten(Karten c) {
		this.karten = c; 
		setChanged();
		notifyObservers();
		
	}
	
	public Karten getKarten() {
		return karten;
	}

	public ch.bodesuri.pd.spiel.spieler.Spieler getSpieler() {
		return spieler;
	}

	public String getName() {
		return spieler.getName();
	}

	public List<Figur> getFiguren() {
		return spieler.getFiguren();
	}

	public SpielerFarbe getFarbe() {
		return spieler.getFarbe();
	}

	public boolean amZug() {
		return amZug;
	}

	public List<ZugEingabe> getMoeglicheZuege() {
		return Regelsystem.getMoeglicheZuege(spieler);
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}

	public String toString() {
		return spieler.toString();
	}
}
