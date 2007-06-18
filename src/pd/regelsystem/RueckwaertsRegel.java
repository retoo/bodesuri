package pd.regelsystem;

import pd.brett.Feld;
import pd.spieler.Figur;
import pd.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Weg;

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
	
	protected void pruefeWeg(Weg weg) throws RegelVerstoss {
		if (!weg.istRueckwaerts()) {
			throw new RegelVerstoss("Es muss rückwärts gefahren werden.");
		}

		pruefeWegLaenge(weg);
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
	
	private Feld getZiel(Feld start, int schritte) {
		Feld feld = start;
		for (int i = 0; i < schritte; ++i) {
			feld = feld.getVorheriges();
		}
		return feld;
	}
}
