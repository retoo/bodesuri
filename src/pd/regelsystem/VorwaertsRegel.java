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

import java.util.List;
import java.util.Vector;

import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.regelsystem.verstoesse.Verstoesse;
import pd.regelsystem.verstoesse.WegLaengeVerstoss;
import pd.spiel.brett.BankFeld;
import pd.spiel.brett.Feld;
import pd.spiel.brett.HimmelFeld;
import pd.spiel.spieler.Figur;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.HeimschickAktion;
import pd.zugsystem.Weg;
import pd.zugsystem.Zug;

/**
 * Regel für normales Vorwärtsfahren, mit Heimschicken von Figur auf Zielfeld.
 */
public class VorwaertsRegel extends Regel {
	protected int schritte;

	/**
	 * @param schritte Anzahl Schritte, die die Regel überprüfen soll
	 */
	public VorwaertsRegel(int schritte) {
		this.schritte = schritte;
		setBeschreibung(schritte + " vorwärts");
	}

	public boolean arbeitetMitWeg() {
		return true;
	}

	/**
	 * Validiere Zugeingabe. Der Zug muss mit eigener Figur ausgeführt werden
	 * und auf ein ungeschütztes Zielfeld gehen. Wenn das Zielfeld von einer
	 * Figur besetzt ist, wird diese heimgeschickt.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() != 1) {
			throw new Verstoesse.AnzahlBewegungen();
		}

		Spieler spieler = zugEingabe.getBetroffenerSpieler();
		Bewegung bewegung = zugEingabe.getBewegung();
		Feld start = bewegung.start;
		Feld ziel  = bewegung.ziel;

		pruefeFahrenMit(start.getFigur(), spieler);

		pruefeBewegung(bewegung, spieler);

		Weg weg = bewegung.getWeg();
		if (weg == null) {
			throw new Verstoesse.SoNichtFahren();
		}
		pruefeWegRichtung(weg);
		pruefeWegLaenge(weg);

		for (Feld feld : weg) {
			if (feld == start) continue;

			if (feld.istBank() && feld != ziel &&
			    ((BankFeld)feld).istBesetztVonBesitzer()) {
				throw new Verstoesse.UeberFigurAufBankFahren();
			}

			if (feld.istGeschuetzt()) {
				throw new Verstoesse.AufOderUeberGeschuetzteFahren();
			}
		}

		Zug zug = new Zug();

		if (ziel.istBesetzt()) {
			zug.fuegeHinzu(new HeimschickAktion(ziel));
		}

		zug.fuegeHinzu(new Aktion(start, ziel));

		return zug;
	}

	protected void pruefeBewegung(Bewegung bewegung, Spieler spieler) throws RegelVerstoss {
		Feld start = bewegung.start;
		Feld ziel  = bewegung.ziel;

		if (start.istHimmel() && !ziel.istHimmel()) {
			throw new Verstoesse.ImHimmelNurVorwaertsFahren();
		} else if (start.istLager() && ziel.istLager()) {
			throw new Verstoesse.ImLagerFahren();
		} else if (start.istLager()) {
			throw new Verstoesse.FalschStarten();
		} else if (ziel.istLager()) {
			throw new Verstoesse.InsLagerFahren();
		}

		if (ziel.istHimmel()) {
			HimmelFeld himmel = (HimmelFeld) ziel;
			if (!himmel.istVon(spieler)) {
				throw new Verstoesse.NurInEigenenHimmelFahren();
			}
		}

		if (start.istBank() && ziel.istHimmel() && start.istGeschuetzt()) {
			throw new Verstoesse.DirektInHimmelFahren();
		}
	}

	protected void pruefeWegRichtung(Weg weg) throws RegelVerstoss {
		if (weg.istRueckwaerts()) {
			throw new Verstoesse.NurVorwaertsFahren();
		}
	}

	protected void pruefeWegLaenge(Weg weg) throws RegelVerstoss {
		int wegLaenge = weg.size() - 1;
		if (wegLaenge != schritte) {
			throw new WegLaengeVerstoss(schritte, wegLaenge);
		}
	}

	protected void pruefeFahrenMit(Figur figur, Spieler spieler)
			throws RegelVerstoss {
		if (figur == null) {
			throw new Verstoesse.MitFigurFahren();
		}

		if (!figur.istVon(spieler)) {
			throw new Verstoesse.MitEigenerFigurFahren();
		}
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                                  ZugEingabeAbnehmer abnehmer) {
		for (Figur figur : spieler.getZiehbareFiguren()) {
			Feld start = figur.getFeld();

			Vector<Feld> ziele = new Vector<Feld>();

			ziele.add(getZiel(start, schritte, false));
			ziele.add(getZiel(start, schritte, true));

			for (Feld ziel : ziele) {
				ZugEingabe ze = getMoeglicheZugEingabe(spieler, karte,
				                                       start, ziel);
				if (ze == null) {
					continue;
				}

				boolean abbrechen = abnehmer.nehmeEntgegen(ze);
				if (abbrechen) {
					return;
				}
			}
		}
	}

	protected ZugEingabe getMoeglicheZugEingabe(Spieler spieler, Karte karte,
	                                            Feld start, Feld ziel) {
		if (start == null || ziel == null) {
			return null;
		}
		List<Bewegung> bewegungen = new Vector<Bewegung>();
		bewegungen.add(new Bewegung(start, ziel));
		return getMoeglicheZugEingabe(spieler, karte, bewegungen);
	}

	protected ZugEingabe getMoeglicheZugEingabe(Spieler spieler, Karte karte,
	                                            List<Bewegung> bewegungen) {
		try {
			ZugEingabe ze = new ZugEingabe(spieler, karte, bewegungen);
			validiere(ze);
			return ze;
		} catch (RegelVerstoss rv) {
			return null;
		}
	}

	private Feld getZiel(Feld start, int schritte, boolean mitHimmel) {
		Feld feld = start;
		for (int i = 0; i < schritte; ++i) {
			if (mitHimmel && feld.istBank()) {
				feld = ((BankFeld) feld).getHimmel();
			} else {
				feld = feld.getNaechstes();
			}
			if (feld == null) {
				return null;
			}
		}
		return feld;
	}
}
