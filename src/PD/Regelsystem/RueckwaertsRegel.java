package PD.Regelsystem;

import PD.Zugsystem.Bewegung;
import PD.Zugsystem.Feld;
import PD.Zugsystem.Zug;

public class RueckwaertsRegel extends Regel {
	private int schritte;
	
	public RueckwaertsRegel(int schritte) {
		this.schritte = schritte;
	}

	public boolean validiere(Zug zug) {
		Bewegung bewegung = zug.getBewegung();
		Feld start = bewegung.getStart();
		
		if (start.getFigur().getSpieler() != zug.getSpieler()) {
			return false;
		}
		
		if (start.getWegRueckwaerts(bewegung.getZiel()).size() != schritte) {
			return false;
		}
		
		return true;
	}

}
