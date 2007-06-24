package pd.regelsystem;

import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Figur;
import pd.spieler.Spieler;
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
			throw new RegelVerstoss("Im Lager kann nicht gefahren werden.");
		} else if (start.istLager()) {
			throw new RegelVerstoss("Es kann nicht rückwärts aus dem Lager " +
			                        "gefahren werden.");
		} else if (ziel.istLager()) {
			throw new RegelVerstoss("Es gibt nur eine Art, ins Lager " +
			                        "zurückzukehren...");
		} else if (start.istHimmel()) {
				throw new RegelVerstoss("Im Himmel kann nicht mehr rückwärts " +
				                        "gefahren werden.");
		} else if (ziel.istHimmel()) {
			throw new RegelVerstoss("Es kann nicht rückwärts in den Himmel " +
			                        "gefahren werden.");
		}
	}

	protected void pruefeWegRichtung(Weg weg) throws RegelVerstoss {
		if (!weg.istRueckwaerts()) {
			throw new RegelVerstoss("Es muss rückwärts gefahren werden.");
		}
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                                  ZugEingabeAbnehmer abnehmer) {
		for (Figur figur : spieler.getFiguren()) {
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
