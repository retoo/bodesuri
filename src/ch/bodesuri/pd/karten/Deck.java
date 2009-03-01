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


package ch.bodesuri.pd.karten;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ch.bodesuri.pd.regelsystem.Karte;


public class Deck {
	private static Map<KartenFarbe, List<Karte>> kartenFuerFarbe = 
		new HashMap<KartenFarbe, List<Karte>>();

	public static List<Karte> erstelleKarten() {
		List<Karte> karten = new Vector<Karte>();

		for (KartenFarbe farbe : KartenFarbe.values()) {
			kartenFuerFarbe.put(farbe, erstelleKartenFuerFarbe(farbe));
			karten.addAll(kartenFuerFarbe.get(farbe));
		}

		/* Karten müssen für Serialisierung unterscheidbar sein. */
		karten.add(new Joker(KartenFarbe.Herz));
		karten.add(new Joker(KartenFarbe.Karo));
		karten.add(new Joker(KartenFarbe.Kreuz));

		return karten;
	}

	public static List<Karte> getKartenFuerFarbe(KartenFarbe farbe) {
		return kartenFuerFarbe.get(farbe);
	}

	private static List<Karte> erstelleKartenFuerFarbe(KartenFarbe farbe) {
		List<Karte> karten = new Vector<Karte>();
		karten.add(new Ass(farbe));
		karten.add(new Koenig(farbe));
		karten.add(new Dame(farbe));
		karten.add(new Bube(farbe));
		karten.add(new Zehn(farbe));
		karten.add(new Neun(farbe));
		karten.add(new Acht(farbe));
		karten.add(new Sieben(farbe));
		karten.add(new Sechs(farbe));
		karten.add(new Fuenf(farbe));
		karten.add(new Vier(farbe));
		karten.add(new Drei(farbe));
		karten.add(new Zwei(farbe));
		return karten;
	}
}
