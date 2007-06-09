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
		
		if (start instanceof HimmelFeld || start instanceof LagerFeld) {
			throw new RegelVerstoss("Ungültiges Startfeld.");
		} else if (ziel instanceof HimmelFeld || ziel instanceof LagerFeld) {
			throw new RegelVerstoss("Ungültiges Zielfeld.");
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
