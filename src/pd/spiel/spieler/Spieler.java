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


package pd.spiel.spieler;

import java.util.List;
import java.util.Vector;

import pd.regelsystem.Karte;
import pd.serialisierung.BodesuriCodierbaresObjekt;
import pd.spiel.Spiel;
import dienste.observer.ObservableList;

/**
 * Spieler, der bei einem {@link Spiel} mitspielt, und 4 {@link Figur Figuren}
 * hat.
 */
public class Spieler extends BodesuriCodierbaresObjekt {
	private int nummer;
	private String name;
	private SpielerFarbe farbe;
	private Spiel spiel;
	private Vector<Figur> figuren = new Vector<Figur>();
	private ObservableList<Karte> karten = new ObservableList<Karte>();
	private Spieler partner;

	/**
	 * Erstellt einen Spieler.
	 *
	 * @param nummer
	 *            Eindeutige Spielernummer
	 * @param spiel
	 *            Spiel, bei dem der Spieler mitspielt
	 * @param farbe
	 */
	public Spieler(int nummer, Spiel spiel, SpielerFarbe farbe) {
		super("Spieler " + nummer);
		this.nummer = nummer;
		this.spiel = spiel;
		this.farbe = farbe;
		for (int i = 0; i < 4; ++i) {
			figuren.add(new Figur(this));
		}
	}

	/**
	 * Sieht zum Beispiel so aus: "Zoë"
	 */
	public String toString() {
		return getName();
	}

	/**
	 * @return Name des Spielers
	 */
	public String getName() {
		return name;
	}

	/**
     * @return die farbe
     */
    public SpielerFarbe getFarbe() {
    	return farbe;
    }

	/**
	 * @param name
	 *            Name des Spielers
	 */
	public void setName(String name) {
		this.name = name;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return Liste von Figuren, die dem Spieler gehören
	 */
	public List<Figur> getFiguren() {
		return figuren;
	}

	/**
	 * Figuren, mit denen der Spieler spielt. Dies sind die eigenen oder die des
	 * Partners, falls alle eigenen im Himmel sind.
	 * 
	 * @return Liste von Figuren
	 */
	public List<Figur> getZiehbareFiguren() {
		return istFertig() ? partner.getFiguren() : getFiguren();
	}

	/**
	 * @return Spielernummer
	 */
	public int getNummer() {
		return nummer;
	}

	/**
	 * @return Spiel, bei dem der Spieler mitspielt
	 */
	public Spiel getSpiel() {
		return spiel;
	}

	public ObservableList<Karte> getKarten() {
		return karten;
	}

	/**
	 * @return true, wenn alle Figuren des Spielers im Himmel sind
	 */
	public boolean istFertig() {
		for (Figur figur : figuren) {
			if (!figur.getFeld().istHimmel()) {
				return false;
			}
		}

		return true;
	}

	public Spieler getPartner() {
		return partner;
	}

	public void setPartner(Spieler partner) {
		this.partner = partner;
	}
}
