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


package ch.bodesuri.pd.regelsystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;
import ch.bodesuri.pd.regelsystem.verstoesse.Verstoesse;
import ch.bodesuri.pd.regelsystem.verstoesse.WegLaengeVerstoss;
import ch.bodesuri.pd.spiel.brett.BankFeld;
import ch.bodesuri.pd.spiel.brett.Feld;
import ch.bodesuri.pd.spiel.spieler.Figur;
import ch.bodesuri.pd.spiel.spieler.Spieler;
import ch.bodesuri.pd.zugsystem.Aktion;
import ch.bodesuri.pd.zugsystem.Bewegung;
import ch.bodesuri.pd.zugsystem.HeimschickAktion;
import ch.bodesuri.pd.zugsystem.Weg;
import ch.bodesuri.pd.zugsystem.Zug;


/**
 * Regel der Karte Sieben, bei der der Zug auf mehrere Figuren aufgeteilt werden
 * kann.
 */
public class SiebnerRegel extends VorwaertsRegel {
	public SiebnerRegel() {
		super(0);  /* Wird hier nicht gebraucht. */
		setBeschreibung("7 vorwärts auf mehrere Figuren aufteilbar, Überholen schickt heim");
	}

	public boolean arbeitetMitWeg() {
		return true;
	}

	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() <= 0) {
			throw new Verstoesse.AnzahlBewegungen();
		}

		Spieler spieler = zugEingabe.getBetroffenerSpieler();

		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			pruefeBewegung(bewegung, spieler);
		}

		List<Weg> wege = getWege(zugEingabe.getBewegungen());
		pruefeWege(wege);

		Map<Feld, Figur> figuren = getFeldFigurenMap(wege);
		Set<Feld> geschuetzt = getFeldGeschuetztSet(wege);

		Zug zug = new Zug();

		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			Feld start = bewegung.start;
			Feld ziel  = bewegung.ziel;

			for (Feld feld : bewegung.getWeg()) {
				Figur figur = figuren.get(feld);

				if (feld == start) {
					pruefeFahrenMit(figur, spieler);
					continue;
				}

				if (geschuetzt.contains(feld)) {
					throw new Verstoesse.AufOderUeberGeschuetzteFahren();
				}

				if (figur != null) {
					zug.fuegeHinzu(new HeimschickAktion(feld));
					figuren.put(feld, null);
				}
			}

			/* Leere Bewegungen ignorieren. */
			if (start == ziel) {
				continue;
			}

			/* Figuren nachführen. */
			Figur figur = figuren.get(start);
			figuren.put(start, null);
			figuren.put(ziel, figur);

			/* Geschützte Felder nachführen. */
			geschuetzt.remove(start);
			if (ziel.istHimmel()) {
				geschuetzt.add(ziel);
			}

			zug.fuegeHinzu(new Aktion(start, ziel));
		}

		return zug;
	}

	private List<Weg> getWege(List<Bewegung> bewegungen) {
		List<Weg> wege = new LinkedList<Weg>();
		for (Bewegung bewegung : bewegungen) {
			wege.add(bewegung.getWeg());
		}
		return wege;
	}

	private void pruefeWege(List<Weg> wege) throws RegelVerstoss {
		int laenge = 0;

		for (Weg weg : wege) {
			if (weg == null) {
				throw new Verstoesse.SoNichtFahren();
			}
			pruefeWegRichtung(weg);
			laenge += weg.getWegLaenge();
		}

		if (laenge != 7) {
			throw new WegLaengeVerstoss(7, laenge);
		}
	}

	private Map<Feld, Figur> getFeldFigurenMap(List<Weg> wege) {
		Map<Feld, Figur> figuren = new HashMap<Feld, Figur>();
		for (Weg weg : wege) {
			for (Feld feld : weg) {
				figuren.put(feld, feld.getFigur());
			}
		}
		return figuren;
	}

	private Set<Feld> getFeldGeschuetztSet(List<Weg> wege) {
		Set<Feld> geschuetzt = new HashSet<Feld>();
		for (Weg weg : wege) {
			for (Feld feld : weg) {
				if (feld.istGeschuetzt()) {
					geschuetzt.add(feld);
				}
			}
		}
		return geschuetzt;
	}


	/* Einstiegsmethode */
	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                                  ZugEingabeAbnehmer abnehmer) {
		Map<Figur, Feld> positionen = new IdentityHashMap<Figur, Feld>();
		for (Figur figur : spieler.getZiehbareFiguren()) {
			positionen.put(figur, figur.getFeld());
		}
		List<Figur> reihenfolge = new Vector<Figur>();

		/* Rekursive Methode starten */
		liefereZugEingaben(spieler, karte, abnehmer, positionen, reihenfolge, 7);
	}

	/* Rekursiv aufgerufene Methode */
	private boolean liefereZugEingaben(Spieler spieler, Karte karte,
	                                   ZugEingabeAbnehmer abnehmer,
	                                   Map<Figur, Feld> positionen,
	                                   List<Figur> reihenfolge, int schritte) {
		if (schritte == 0) {
			ZugEingabe ze = getMoeglicheZugEingabe(spieler, karte,
			                                       positionen, reihenfolge);
			if (ze == null) {
				/* Noch nicht abbrechen. */
				return false;
			}
			boolean abbrechen = abnehmer.nehmeEntgegen(ze);
			return abbrechen;
		}

		for (Figur figur : spieler.getZiehbareFiguren()) {
			Feld feld = positionen.get(figur);

			/* Vom Lager aus kann man nicht fahren. */
			if (feld.istLager()) {
				continue;
			}

			List<Feld> kandidaten = new Vector<Feld>();

			/* In Himmel möglich? */
			if (feld.istBank() && ((BankFeld) feld).istVon(figur.getSpieler())) {
				Feld himmel = ((BankFeld) feld).getHimmel();
				kandidaten.add(himmel);
			}

			/* Nächstes Feld möglich? */
			if (feld.getNaechstes() != null) {
				kandidaten.add(feld.getNaechstes());
			}

			boolean figurInReihenfolge = reihenfolge.contains(figur);
			if (!figurInReihenfolge) {
				reihenfolge.add(figur);
			}
			for (Feld kandidat : kandidaten) {
				positionen.put(figur, kandidat);

				/* Nächsten Schritt versuchen. */
				boolean abbrechen;
				abbrechen = liefereZugEingaben(spieler, karte, abnehmer,
				                               positionen, reihenfolge,
				                               schritte - 1);
				if (abbrechen) {
					return abbrechen;
				}

				positionen.put(figur, feld);
			}
			if (!figurInReihenfolge) {
				reihenfolge.remove(figur);
			}
		}

		/* Noch nicht abbrechen. */
		return false;
	}

	private ZugEingabe getMoeglicheZugEingabe(Spieler spieler, Karte karte,
	                                          Map<Figur, Feld> positionen,
	                                          List<Figur> reihenfolge) {
		List<Bewegung> bewegungen = new Vector<Bewegung>();
		for (Figur figur : reihenfolge) {
			Feld start = figur.getFeld();
			Feld ziel  = positionen.get(figur);
			if (start != ziel) {
				bewegungen.add(new Bewegung(start, ziel));
			}
		}
		return getMoeglicheZugEingabe(spieler, karte, bewegungen);
	}
}
