package pd.regelsystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import pd.karten.Karte;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.regelsystem.verstoesse.Verstoesse;
import pd.regelsystem.verstoesse.WegLaengeVerstoss;
import pd.spiel.brett.BankFeld;
import pd.spiel.brett.Feld;
import pd.spiel.spieler.Figur;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.HeimschickAktion;
import pd.zugsystem.Weg;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;
import pd.zugsystem.ZugEingabeAbnehmer;

/**
 * Regel der Karte Sieben, bei der der Zug auf mehrere Figuren aufgeteilt werden
 * kann.
 */
public class SiebnerRegel extends VorwaertsRegel {
	public SiebnerRegel() {
		super(7);  /* Für kannZiehen gebraucht. */
		setBeschreibung("7 vorwärts auf mehrere Figuren aufteilbar, Überholen schickt heim");
	}

	public boolean arbeitetMitWeg() {
		return true;
	}

	@SuppressWarnings("null")
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() <= 0) {
			throw new Verstoesse.AnzahlBewegungen();
		}

		Spieler spieler = zugEingabe.getBetroffenerSpieler();

		int wegLaenge = 0;
		HashMap<Feld, Figur> figuren = new HashMap<Feld, Figur>();
		Set<Feld> geschuetzt = new HashSet<Feld>();
		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			pruefeBewegung(bewegung, spieler);
			Weg weg = bewegung.getWeg();
			if (weg == null) {
				throw new Verstoesse.SoNichtFahren();
			}
			pruefeWegRichtung(weg);
			wegLaenge += weg.size() - 1;
			for (Feld feld : weg) {
				figuren.put(feld, feld.getFigur());
				if (feld.istGeschuetzt()) {
					geschuetzt.add(feld);
				}
			}
		}

		if (wegLaenge != 7) {
			throw new WegLaengeVerstoss(7, wegLaenge);
		}

		Zug zug = new Zug();

		for (Bewegung bewegung : zugEingabe.getBewegungen()) {
			for (Feld feld : bewegung.getWeg()) {
				Figur figur = figuren.get(feld);
				boolean hatFigur = (figur != null);

				if (feld == bewegung.start) {
					if (!hatFigur) {
						throw new Verstoesse.MitFigurFahren();
					} else if (!figur.istVon(spieler)) {
						throw new Verstoesse.MitEigenerFigurFahren();
					}
					continue;
				}

				if (geschuetzt.contains(feld) ||
				    (hatFigur && feld.istHimmel())) {
					throw new Verstoesse.AufOderUeberGeschuetzteFahren();
				}

				if (hatFigur) {
					zug.fuegeHinzu(new HeimschickAktion(feld));
					figuren.put(feld, null);
				}
			}

			if (bewegung.start == bewegung.ziel) {
				continue;
			}

			Figur figur = figuren.get(bewegung.start);
			figuren.put(bewegung.start, null);
			figuren.put(bewegung.ziel, figur);
			geschuetzt.remove(bewegung.start);

			zug.fuegeHinzu(new Aktion(bewegung.start, bewegung.ziel));
		}

		return zug;
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                                  ZugEingabeAbnehmer abnehmer) {
		Map<Figur, Feld> positionen = new IdentityHashMap<Figur, Feld>();
		for (Figur figur : spieler.getZiehbareFiguren()) {
			positionen.put(figur, figur.getFeld());
		}
		List<Figur> reihenfolge = new Vector<Figur>();
		liefereZugEingaben(spieler, karte, abnehmer, positionen, reihenfolge, 7);
	}

	private boolean liefereZugEingaben(Spieler spieler, Karte karte,
	                                   ZugEingabeAbnehmer abnehmer,
	                                   Map<Figur, Feld> positionen,
	                                   List<Figur> reihenfolge, int schritte) {
		if (schritte == 0) {
			ZugEingabe ze = getMoeglicheZugEinabe(spieler, karte,
			                                      positionen, reihenfolge);
			if (ze == null) {
				return false;
			}
			boolean abbrechen = abnehmer.nehmeEntgegen(ze);
			return abbrechen;
		}

		for (Figur figur : spieler.getZiehbareFiguren()) {
			Feld feld = positionen.get(figur);
			
			if (feld.istLager()) {
				continue;
			}

			Feld feldNeu;
			List<Feld> kandidaten = new Vector<Feld>();
			if (feld.istBank() && ((BankFeld) feld).istVon(figur.getSpieler())) {
				feldNeu = ((BankFeld) feld).getHimmel();
				kandidaten.add(feldNeu);
			}
			feldNeu = feld.getNaechstes();
			if (feldNeu != null) {
				kandidaten.add(feldNeu);
			}

			boolean figurInReihenfolge = reihenfolge.contains(figur);
			if (!figurInReihenfolge) {
				reihenfolge.add(figur);
			}
			for (Feld kandidat : kandidaten) {
				positionen.put(figur, kandidat);

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

		/* Noch nicht abbrechen */
		return false;
	}

	private ZugEingabe getMoeglicheZugEinabe(Spieler spieler, Karte karte,
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
