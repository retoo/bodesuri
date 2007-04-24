package PD.regelsystem;

import PD.zugsystem.Bewegung;
import PD.zugsystem.Feld;
import PD.zugsystem.Zug;

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
