package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.brett.Feld;
import pd.zugsystem.Bewegung;

/**
 * Gleich wie {@link VorwaertsRegel}, aber Zugrichtung ist rückwärts.
 */
public class RueckwaertsRegel extends VorwaertsRegel {
	public RueckwaertsRegel(int schritte) {
		super(schritte);
	}
	
	protected List<Feld> getWeg(Bewegung bewegung) {
		Vector<Feld> weg = new Vector<Feld>();
		Feld feld = bewegung.start;
		while (feld != bewegung.ziel) {
			weg.add(feld);
			feld = feld.getVorheriges();
		}
		weg.add(feld);
		return weg;
	}
}
