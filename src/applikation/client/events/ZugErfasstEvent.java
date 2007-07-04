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


package applikation.client.events;

import java.util.List;
import java.util.Vector;

import pd.regelsystem.ZugEingabe;
import pd.zugsystem.Bewegung;
import applikation.client.pd.Karte;
import applikation.client.pd.Spieler;
import dienste.eventqueue.Event;

/**
 * Event mit dem der ZugAutomat den fertig erfassten Zug an dem Automaten
 * zurückgibt.
 */
public class ZugErfasstEvent extends Event {
	Spieler spieler;
	Karte karte;
	Karte konkreteKarte;
	List<Bewegung> bewegungen;

	public ZugErfasstEvent(Spieler spieler, Karte karte, Karte konkreteKarte,
	        Bewegung bewegung) {
		List<Bewegung> bewegungen = new Vector<Bewegung>();
		bewegungen.add(bewegung);
		initialisiere(spieler, karte, konkreteKarte, bewegungen);
	}

	public ZugErfasstEvent(Spieler spieler, Karte karte, Karte konkreteKarte,
	        List<Bewegung> bewegung) {
		initialisiere(spieler, karte, konkreteKarte, bewegung);
	}

	private void initialisiere(Spieler spieler, Karte karte,
	                           Karte konkreteKarte, List<Bewegung> bewegung) {
		this.spieler = spieler;
		this.karte = karte;
		this.konkreteKarte = konkreteKarte;
		this.bewegungen = bewegung;
	}

	public Karte getKarte() {
		return karte;
	}

	public Karte getKonkreteKarte() {
		return konkreteKarte;
	}

	public ZugEingabe toZugEingabe() {
		ZugEingabe ze = new ZugEingabe(spieler.getSpieler(), karte.getKarte(),
		                               bewegungen);
		if (konkreteKarte != null) {
			ze.setKonkreteKarte(konkreteKarte.getKarte());
		}
		return ze;
	}
}
