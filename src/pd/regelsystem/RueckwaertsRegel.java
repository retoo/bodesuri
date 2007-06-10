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
		setBeschreibung(schritte + " rückwärts");
	}
	
	protected List<Feld> getWeg(Bewegung bewegung) throws RegelVerstoss {
		Vector<Feld> weg = new Vector<Feld>();
		
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
		
		Feld feld = start;
		while (feld != ziel) {
			weg.add(feld);
			feld = feld.getVorheriges();
		}
		weg.add(feld);
		return weg;
	}
}
