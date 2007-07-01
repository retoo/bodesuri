package pd.regelsystem;

import pd.karten.Karte;
import pd.regelsystem.verstoesse.RegelVerstoss;
import pd.regelsystem.verstoesse.Verstoesse;
import pd.spiel.brett.Feld;
import pd.spiel.spieler.Figur;
import pd.spiel.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Weg;
import pd.zugsystem.ZugEingabe;
import pd.zugsystem.ZugEingabeAbnehmer;

/**
 * Gleich wie {@link VorwaertsRegel}, aber Zugrichtung ist rückwärts.
 */
public class RueckwaertsRegel extends VorwaertsRegel {
	public RueckwaertsRegel(int schritte) {
		super(schritte);
		setBeschreibung(schritte + " rückwärts");
	}

	public boolean arbeitetMitWeg() {
		return true;
	}

	protected void pruefeBewegung(Bewegung bewegung, Spieler spieler) throws RegelVerstoss {
		Feld start = bewegung.start;
		Feld ziel  = bewegung.ziel;

		if (start.istLager() && ziel.istLager()) {
			throw new Verstoesse.ImLagerFahren();
		} else if (start.istLager()) {
			throw new Verstoesse.FalschStarten();
		} else if (ziel.istLager()) {
			throw new Verstoesse.InsLagerFahren();
		} else if (start.istHimmel()) {
				throw new Verstoesse.ImHimmelNurVorwaertsFahren();
		} else if (ziel.istHimmel()) {
			throw new Verstoesse.RueckwaertsInHimmelFahren();
		}
	}

	protected void pruefeWegRichtung(Weg weg) throws RegelVerstoss {
		if (!weg.istRueckwaerts()) {
			throw new Verstoesse.NurRueckwaertsFahren();
		}
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                                  ZugEingabeAbnehmer abnehmer) {
		for (Figur figur : spieler.getZiehbareFiguren()) {
			Feld start = figur.getFeld();

			if (start.istLager() || start.istHimmel()) {
				continue;
			}

			Feld ziel = getZiel(start, schritte);
			ZugEingabe ze = getMoeglicheZugEingabe(spieler, karte, start, ziel);
			if (ze != null) {
				boolean abbrechen = abnehmer.nehmeEntgegen(ze);
				if (abbrechen) {
					return;
				}
			}
		}
	}

	private Feld getZiel(Feld start, int schritte) {
		Feld feld = start;
		for (int i = 0; i < schritte; ++i) {
			feld = feld.getVorheriges();
		}
		return feld;
	}
}
