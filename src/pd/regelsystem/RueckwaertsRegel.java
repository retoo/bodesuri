package pd.regelsystem;

import java.util.List;

import pd.brett.Feld;
import pd.karten.Karte;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Weg;
import pd.zugsystem.ZugEingabe;

/**
 * Gleich wie {@link VorwaertsRegel}, aber Zugrichtung ist rückwärts.
 */
public class RueckwaertsRegel extends VorwaertsRegel {
	public RueckwaertsRegel(int schritte) {
		super(schritte);
		setBeschreibung(schritte + " rückwärts");
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

	public boolean kannZiehen(Spieler spieler) {
		for (Figur figur : spieler.getFiguren()) {
			Feld start = figur.getFeld();

			if (start.istLager() || start.istHimmel()) {
				continue;
			}

			Feld ziel = getZiel(start, schritte);
			if (istZugMoeglich(spieler, start, ziel)) {
				return true;
			}
		}
		return false;
	}

	public void moeglicheZuege(Spieler spieler, Karte karte, List<ZugEingabe> moeglich) {
		for (Figur figur : spieler.getFiguren()) {
			Feld start = figur.getFeld();

			if (start.istLager() || start.istHimmel()) {
				continue;
			}

			Feld ziel = getZiel(start, schritte);
			if (istZugMoeglich(spieler, start, ziel)) {
				Bewegung bewegung = new Bewegung(start, ziel);
				moeglich.add(new ZugEingabe(spieler, karte, bewegung));
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
