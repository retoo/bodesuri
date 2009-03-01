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


package ch.bodesuri.applikation.server.pd;

import java.util.List;
import java.util.Vector;

import ch.bodesuri.applikation.nachrichten.KartenTausch;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.regelsystem.ZugEingabe;


/**
 * Entspricht der Teilnahme eines Spielers an einer gewissen Runde im Spiel.
 */
public class RundenTeilnahme {
	private Vector<ZugEingabe> zuegeDieseRunde;
	private Karte karteVomPartner;
	private Spieler spieler;
	private List<Karte> karten;
	private boolean hatGetauscht;

	/**
	 * Erstellt eine neue Rundenteilnahme
	 *
	 * @param spieler Teilnehmender Spieler
	 * @param karten Die Karten des Spielers
	 */
	public RundenTeilnahme(Spieler spieler, List<Karte> karten) {
		this.spieler = spieler;
		this.karten = karten;
		this.hatGetauscht = false;
		this.zuegeDieseRunde = new Vector<ZugEingabe>();
	}

	/**
	 * Schliesst den Tausch der Karte entgültig ab indem die erhaltene Karte der
	 * Kartenliste hinzugefügt wird und dies dem Spieler mitgeteilt wird.
	 */
	public void tauschAbschluss() {
		karten.add(karteVomPartner);

		/* dem Clienten mitteilen */
		spieler.sende(new KartenTausch(karteVomPartner));
	}

	/**
	 * Erfasst einen neuen Zug. Die im Zug verwendete Karte wird dem Spieler
	 * weg genommen.
	 *
	 * @param zug
	 */
	public void neuerZug(ZugEingabe zug) {
		boolean res = nimmWeg(zug.getKarte());

		/* Cheat Schutz */
		if (res == false) {
			throw new RuntimeException("Spieler " + spieler + " spielte den Zug " + zug + " mit einer Karte die er nicht hat!");
		}

		zuegeDieseRunde.add(zug);
	}


	/**
	 * @return true falls der Spieler keine Karten mehr hat
	 */
	public boolean hatKeineKartenMehr() {
		return karten.isEmpty();
	}

	public boolean hatGetauscht() {
		return hatGetauscht;
	}

	public void setHatGetauscht() {
		hatGetauscht = true;
	}

	/**
	 * Nimmt die übergebene Karte dem Spieler weg.
	 *
	 * @param karte die zu entfernende Karte
	 * @return true falls die Karte vorhanden war
	 */
	public boolean nimmWeg(Karte karte) {
		return karten.remove(karte);
	}

	/**
	 * @return eine Liste mit allen aktuellen Karten dieses Spielers in dieser
	 *         Runde
	 */
	public List<Karte> getKarten() {
		return karten;
	}

	/**
	 * @return den Spieler
	 */
	public Spieler getSpieler() {
		return spieler;
	}


	public void setKarteVomPartner(Karte karteVomPartner) {
		this.karteVomPartner = karteVomPartner;
	}
}
