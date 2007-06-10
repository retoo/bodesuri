package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.brett.Feld;
import pd.brett.HimmelFeld;
import pd.brett.LagerFeld;
import pd.zugsystem.Bewegung;

/**
 * Gleich wie {@link VorwaertsRegel}, aber Zugrichtung ist rückwärts.
 */
public class RueckwaertsRegel extends VorwaertsRegel {
	public RueckwaertsRegel(int schritte) {
		super(schritte);
		setBeschreibung(schritte + " rückwärts");
	}
	
	protected List<Feld> getWeg(Bewegung bewegung) throws RegelVerstoss {
		Vector<Feld> weg = new Vector<Feld>();
		
		Feld start = bewegung.start;
		Feld ziel  = bewegung.ziel;
		
		if (start instanceof LagerFeld && ziel instanceof LagerFeld) {
			throw new RegelVerstoss("Im Lager kann nicht gefahren werden.");
		} else if (start instanceof LagerFeld) {
			throw new RegelVerstoss("Es kann nicht rückwärts aus dem Lager " +
			                        "gefahren werden.");
		} else if (ziel instanceof LagerFeld) {
			throw new RegelVerstoss("Es gibt nur eine Art, ins Lager " +
			                        "zurückzukehren...");
		} else if (start instanceof HimmelFeld) {
				throw new RegelVerstoss("Im Himmel kann nicht mehr rückwärts " +
				                        "gefahren werden.");
		} else if (ziel instanceof HimmelFeld) {
			throw new RegelVerstoss("Es kann nicht rückwärts in den Himmel " +
			                        "gefahren werden.");
		}
		
		Feld feld = start;
		while (feld != ziel) {
			weg.add(feld);
			feld = feld.getVorheriges();
		}
		weg.add(feld);
		return weg;
	}
}
