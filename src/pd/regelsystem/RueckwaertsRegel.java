package pd.regelsystem;

import java.util.List;
import java.util.Vector;

import pd.brett.Feld;
import pd.zugsystem.Bewegung;

public class RueckwaertsRegel extends VorwaertsRegel {
	public RueckwaertsRegel(int schritte) {
		super(schritte);
	}
	
	protected List<Feld> getWeg(Bewegung bewegung) {
		Vector<Feld> weg = new Vector<Feld>();
		Feld feld = bewegung.getStart();
		while (feld != bewegung.getZiel()) {
			weg.add(feld);
			feld = feld.getVorheriges();
		}
		weg.add(feld);
		return weg;
	}
}
