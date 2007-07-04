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


package pd.regelsystem;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import pd.regelsystem.karten.Karte;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;

/**
 * Enthält Eingaben für Validierung und anschliessende Erstellung des Zuges.
 */
public class ZugEingabe implements Serializable {
	private Spieler spieler;
	private Karte karte;
	private Karte konkreteKarte;
	private List<Bewegung> bewegungen;

	private ZugEingabe(Spieler spieler, Karte karte) {
		this.spieler = spieler;
		this.karte = karte;
		this.konkreteKarte = karte;
	}

	/**
	 * Erstellt eine Zugeingabe.
	 *
	 * @param spieler Spieler, der diesen Zug ausführt
	 * @param karte Karte, mit der gespielt wird
	 * @param bewegung Bewegung (Start, Ziel) des Zuges
	 */
	public ZugEingabe(Spieler spieler, Karte karte, Bewegung bewegung) {
		this(spieler, karte);
		this.bewegungen = new Vector<Bewegung>();
		this.bewegungen.add(bewegung);
	}

	/**
	 * Erstellt eine Zugeingabe mit mehreren Bewegungen.
	 * 
	 * @param spieler Spieler, der diesen Zug ausführt
	 * @param karte Karte, mit der gespielt wird
	 * @param bewegungen Liste von Bewegungen des Zuges
	 */
	public ZugEingabe(Spieler spieler, Karte karte, List<Bewegung> bewegungen) {
		this(spieler, karte);
		this.bewegungen = bewegungen;
	}

	/**
	 * Validiert Zugeingabe.
	 *
	 * @see Regel#validiere(ZugEingabe)
	 *
	 * @return Zug, der ausgeführt werden kann
	 * @throws RegelVerstoss
	 *             Bei Regelwidrigkeit geworfen
	 */
	public Zug validiere() throws RegelVerstoss {
		Regel regel = konkreteKarte.getRegel();
		if (regel == null) {
			throw new RuntimeException("Der Karte " + konkreteKarte +
			                           " ist keine Regel zugewiesen.");
		}
		return regel.validiere(this);
	}

	public boolean equals(Object obj) {
		if (obj instanceof ZugEingabe) {
			ZugEingabe o = (ZugEingabe) obj;
			return (areEqual(spieler, o.spieler) &&
			        areEqual(karte, o.karte) &&
			        areEqual(konkreteKarte, o.konkreteKarte) &&
			        areEqual(bewegungen, o.bewegungen));
		}

		return false;
	}

	private boolean areEqual(Object o1, Object o2) {
		return (o1 == null ? o2 == null : o1.equals(o2));
	}

	/*
	 * Ist nur beim Joker nötig.
	 */
	public void setKonkreteKarte(Karte konkreteKarte) {
		this.konkreteKarte = konkreteKarte;
	}

	/**
	 * @return Spieler, der diesen Zug ausführt
	 */
	public Spieler getSpieler() {
		return spieler;
	}
	
	public Spieler getBetroffenerSpieler() {
		return spieler.istFertig() ? spieler.getPartner() : spieler;
	}

	/**
	 * @return Karte, mit der gespielt wird
	 */
	public Karte getKarte() {
		return karte;
	}
	
	public Karte getKonkreteKarte() {
		return konkreteKarte;
	}

	/**
	 * @return Bewegung (Start, Ziel) des Zuges
	 */
	public Bewegung getBewegung() {
		return bewegungen.get(0);
	}

	public List<Bewegung> getBewegungen() {
		return bewegungen;
	}

	public int getAnzahlBewegungen() {
		return bewegungen.size();
	}

	public String toString() {
		return "ZugEingabe: " + getSpieler() + " mit " + getKarte() + " "
		       + getBewegung();
	}
	
	public String getKurzBeschreibung() {
		return getSpieler().getName() + " spielt " + getKarte();
	}
}
