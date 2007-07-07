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


package applikation.server.pd;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import pd.karten.KartenGeber;
import pd.regelsystem.Karte;
import applikation.nachrichten.KartenTausch;

/**
 * Repräsentiert eine aktuelle Runde. Eine Runde besteht immer aus einer
 * gewissen Anzahl von {@link RundenTeilnahme}n.
 */
public class Runde {
	private HashMap<Spieler, RundenTeilnahme> teilnahmen;
	private Vector<Spieler> spielers;
	private int aktuellerSpieler;
	/**
	 * Aktuelle Rundenummer
	 */
	public final int nummer;

	/**
	 * Erstellt eine neue Runde
	 *
	 * @param nummer Aktuelle Rundenummer
	 * @param spielers Alle Spieler der zu erstellenden Runde
	 * @param kartenGeber Kartengeber
	 */
	public Runde(int nummer, List<Spieler> spielers, KartenGeber kartenGeber) {
		this.teilnahmen = new HashMap<Spieler, RundenTeilnahme>();
		this.nummer = nummer;
		this.spielers = new Vector<Spieler>(spielers);
		this.aktuellerSpieler = nummer % spielers.size();

		int anzahlKarten = getAnzahlKartenProSpieler();

		for (Spieler spieler : spielers) {
			List<Karte> karten = kartenGeber.getKarten(anzahlKarten);
			RundenTeilnahme rt = new RundenTeilnahme(spieler, karten);
			teilnahmen.put(spieler, rt);
		}
	}

	/**
	 * Entfernt den übergebenen Spieler aus der aktuellen Runde.
	 *
	 * @param spieler
	 */
	public void entferneSpieler(Spieler spieler) {
		RundenTeilnahme res_map = teilnahmen.remove(spieler);
		boolean res_vector=  spielers.remove(spieler);

		this.aktuellerSpieler--;

		if (res_map == null || !res_vector) {
			throw new RuntimeException("Konnte Spieler " + spieler + " nicht aus der aktuellen Runde entfernen");
		}
	}

	/**
	 * Prüft ob fertig getauscht wurde.
	 *
	 * @return true falls dem so ist
	 */
	public boolean isFertigGetauscht() {
		/* Pruefen ob irgend ein Spieler noch nicht getauscht hat */
		for (RundenTeilnahme teilname : teilnahmen.values()) {
			if (!teilname.hatGetauscht()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Markiert den nächsten Spieler als 'aktuellerSpieler'. Methode darf erst
	 * aufgerufen werden, nachdem mindestens ein Spieler hinzugefügt wurde.
	 */
	public void rotiereSpieler() {
		int anzahlSpieler = teilnahmen.size();

		if (anzahlSpieler > 0) {
			aktuellerSpieler = (aktuellerSpieler + 1) % anzahlSpieler;
		} else
			throw new RuntimeException("Kann nicht rotieren, es gibt ja noch gar keine Spieler");
	}

	/**
	 * Liefert aktuellen Spieler
	 *
	 * @return der zurzeit spielende Spieler
	 */
	public Spieler getAktuellerSpieler() {
		return spielers.get(aktuellerSpieler);
	}

	/**
	 * Anzahl Karten mit aufsteigender Rundennummer: 6, 5, 4, 3, 2, 6, 5, ...
	 *
	 * @return int Anzahl Karten in dieser Runde
	 */
	public int getAnzahlKartenProSpieler() {
		return 6 - (nummer % 5);
	}

	/**
	 * Prüft ob die Runde fertig ist.
	 *
	 * @return true falls die Runde fertig
	 */
	public boolean isFertig() {
		return teilnahmen.isEmpty();
	}

	/**
	 * Liefert alle Teilnahmen in dieser Runde.
	 *
	 * @return Collection mit allen Teilnahmen
	 */
	public Collection<RundenTeilnahme> getTeilnamhmen() {
		return teilnahmen.values();
	}

	/**
	 * Tauscht die übergebene Karte vom übergebenen Spieler zu seinem Partner.
	 * Die betroffenen Parteien werden noch nicht über den Tausch informiert.
	 *
	 * @param spieler Spieler welcher die Karte selektiert hat
	 * @param tausch zu tauschende Karte
	 */
	public void tausche(Spieler spieler, KartenTausch tausch) {
		RundenTeilnahme rt_spieler = teilnahmen.get(spieler);
		RundenTeilnahme rt_partner = teilnahmen.get(spieler.getPartner());

		if (rt_spieler.hatGetauscht())
			throw new RuntimeException("Spieler " + spieler
			                           + "versucht ein zweites mal zu tauschen");

		rt_spieler.nimmWeg(tausch.karte);
		rt_spieler.setHatGetauscht();

		/* Transferiere Karte zum Partner */
		rt_partner.setKarteVomPartner(tausch.karte);

	}

	/**
	 * Meldet alle getauschten Karten den jeweiligen Partnern.
	 */
	public void tauscheAbschluss() {
		for (RundenTeilnahme rt : teilnahmen.values()) {
			rt.tauschAbschluss();
		}
	}

	/**
	 * Liefert das {@link RundenTeilnahme}-Objekt für einen gewissen Spieler
	 *
	 * @param spieler Spieler der in den Teilnahmen gesucht werden soll
	 * @return RundenTeilname des übergebenen Spielers
	 */
	public RundenTeilnahme getTeilname(Spieler spieler) {
		return teilnahmen.get(spieler);
	}
}
