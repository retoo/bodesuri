package pd.regelsystem;

import pd.zugsystem.Bewegung;
import pd.zugsystem.Feld;
import pd.zugsystem.Zug;

public class VorwaertsRegel extends Regel {
	private int schritte;
	
	public VorwaertsRegel(int schritte) {
		this.schritte = schritte;
	}

	public boolean validiere(Zug zug) {
		Bewegung bewegung = zug.getBewegung();
		Feld start = bewegung.getStart();
		
		/* TODO: Ist das bei allen Regeln so? -> Auslagern */
		if (start.getFigur().getSpieler() != zug.getSpieler()) {
			return false;
		}
		
		if (start.getWeg(bewegung.getZiel()).size() != schritte) {
			return false;
		}
		
		return true;
	}

}
