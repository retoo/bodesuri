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


package pd.karten;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import pd.regelsystem.Karte;

/**
 * KartenGeber, der einen KartenStapel verwaltet und Karten verteilt.
 */
public class KartenGeber {
	/**
	 * 13 * 4 Farben + 3 Joker = 55 Karten = ein Deck
	 * 55 Karten * 2 = 110 Karten
	 */
	private Stack<Karte> kartenStapel = new Stack<Karte>();
	private List<Karte> deck;

	public KartenGeber() {
		deck = Deck.erstelleKarten();
		mischen();
	}

	/**
	 * Initialisert Kartenstapel und Karten. Eine Karten wird über die
	 * Farbe erzeugt. Die Farbe ist nachher nur noch als Attribut in den
	 * Karten vorhanden. Das Deck wird gemischelt.
	 */
	private void mischen() {
		for (int i = 0; i < 2; ++i) {
			kartenStapel.addAll(deck);
		}
		Collections.shuffle(kartenStapel);
	}

	/**
	 * @return Oberste Karte vom Stapel
	 */
	public Karte getKarte() {
		Karte obersteKarte;
		if (kartenStapel.isEmpty()) {
			mischen();
		}
		obersteKarte = kartenStapel.pop();
		return obersteKarte;
	}

	/**
	 * @param anzahl Wie viele Karten?
	 * @return Liste von Karten von Stapel
	 */
	public List<Karte> getKarten(int anzahl) {
	    Vector<Karte> karten = new Vector<Karte>();
	    
	    for (int i = 0; i < anzahl; i++) {
	    	karten.add(getKarte());
	    }
	    
	    return karten;
    }
}
