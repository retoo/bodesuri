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


package pd.spiel.brett;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import pd.spiel.spieler.Figur;
import pd.spiel.spieler.Spieler;

/**
 * Spielbrett, das es pro Spiel gibt und alle Felder beinhaltet.
 *
 * Die Felder bilden eine verkettete Liste.
 *
 * @see Feld
 */
public class Brett {
	/** Die Anzahl der Felder, die sich auf dem Brett befinden */
	public static final int ANZAHL_FELDER = 96;

	private Map<Spieler, BankFeld> bankFelder;
	private Map<Spieler, Vector<LagerFeld>> lagerFelder;
	private Map<Spieler, Vector<HimmelFeld>> himmelFelder;
	private Vector<Feld> alleFelder;

	private int feldNummer;

	/**
	 * Erstellt ein Brett.
	 *
	 * @param spieler Spieler, die auf dem Brett spielen
	 */
	public Brett(List<Spieler> spieler) {
		erstelleFelder(spieler);
	}

	/*
	 * Feldnummerierung ist folgendermassen:
	 * - Bankfeld 0
	 * - Lagerfelder von 1-4
	 * - Himmelfelder von 5-8
	 * - Normale Felder von 9-23
	 * Also 24 Felder pro "Ecke" von Spieler.
	 */
	private void erstelleFelder(List<Spieler> spieler) {
		alleFelder   = new Vector<Feld>();
		bankFelder   = new HashMap<Spieler, BankFeld>();
		lagerFelder  = new HashMap<Spieler, Vector<LagerFeld>>();
		himmelFelder = new HashMap<Spieler, Vector<HimmelFeld>>();

		feldNummer = 0;
		Vector<Feld> felderInRing = new Vector<Feld>();

		for (Spieler sp : spieler) {
			BankFeld bf = new BankFeld(feldNummer++, sp);
			bankFelder.put(sp, bf);
			alleFelder.add(bf);

			erstelleLager(sp, bf);
			erstelleHimmel(sp, bf);

			felderInRing.add(bf);
			for (int i = 0; i < 15; ++i) {
				NormalesFeld nf = new NormalesFeld(feldNummer++);
				felderInRing.add(nf);
				alleFelder.add(nf);
			}
		}

		verkette(felderInRing, true);
	}

	/* TODO: Robin: bf wird nie verwendet (-reto) */
	private void erstelleLager(Spieler sp, BankFeld bf) {
	    Vector<LagerFeld> lager = new Vector<LagerFeld>();
	    for (int i = 0; i < 4; ++i) {
	    	LagerFeld lf = new LagerFeld(feldNummer++, sp);
	    	Figur figur = sp.getFiguren().get(i);
	    	lf.setFigur(figur);
	    	figur.versetzeAuf(lf);
	    	lf.setGeschuetzt(true);
	    	lager.add(lf);
	    }
	    lagerFelder.put(sp, lager);
	    alleFelder.addAll(lager);
    }

	private void erstelleHimmel(Spieler sp, BankFeld bf) {
		Vector<HimmelFeld> himmel = new Vector<HimmelFeld>();
		for (int i = 0; i < 4; ++i) {
			HimmelFeld hf = new HimmelFeld(feldNummer++, sp);
			himmel.add(hf);
		}
		bf.setHimmel(himmel.get(0));
		verkette(himmel, false);
		himmelFelder.put(sp, himmel);
		alleFelder.addAll(himmel);
	}

	/*
	 * Verkette die Felder, also setze Nächstes und Vorheriges. Wenn ringsum
	 * gesetzt ist, wird auch das Letzte mit dem Ersten verkettet.
	 */
	private void verkette(List<? extends Feld> felder, boolean ringsum) {
		int anzahl = ringsum ? felder.size() : felder.size() - 1;
		for (int i = 0; i < anzahl; ++i) {
			int i2 = (i + 1) % felder.size();
			Feld f1 = felder.get(i);
			Feld f2 = felder.get(i2);
			f1.setNaechstes(f2);
			f2.setVorheriges(f1);
		}
	}

	/**
	 * Bankfeld eines Spielers herausfinden.
	 *
	 * @param spieler Spieler
	 * @return Bankfeld des Spielers
	 */
	public BankFeld getBankFeldVon(Spieler spieler) {
		return bankFelder.get(spieler);
	}

	/**
	 * Lagerfelder eines Spielers herausfinden.
	 *
	 * @param spieler Spieler
	 * @return Lagerfelder des Spielers
	 */
	public List<LagerFeld> getLagerFelderVon(Spieler spieler) {
	    return lagerFelder.get(spieler);
    }

	/**
	 * Himmelfelder eines Spielers herausfinden.
	 *
	 * @param spieler Spieler
	 * @return Himmelfelder des Spielers
	 */
	public List<HimmelFeld> getHimmelFelderVon(Spieler spieler) {
		return himmelFelder.get(spieler);
	}

	/**
	 * Erstes freies Lagerfeld eines Spielers herausfinden.
	 *
	 * @param spieler Spieler
	 * @return Erstes freies Lagerfeld
	 */
	public LagerFeld getFreiesLagerFeldVon(Spieler spieler) {
		for (LagerFeld feld : lagerFelder.get(spieler)) {
			if (!feld.istBesetzt()) {
				return feld;
			}
		}
		throw new RuntimeException("getFreiesLagerFeldVon wurde aufgerufen, " +
				"aber es sind keine freien Lagerfelder mehr vorhanden.");
	}

	/**
	 * Gibt alle Felder, die auf dem Brett sind, zurück.
	 *
	 * @return Eine Liste aller Felder
	 */
	public List<Feld> getAlleFelder() {
		return alleFelder;
	}
}
