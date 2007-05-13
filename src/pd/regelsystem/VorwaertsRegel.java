package pd.regelsystem;

import pd.brett.Feld;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class VorwaertsRegel extends Regel {
	private int schritte;
	
	public VorwaertsRegel(int schritte) {
		this.schritte = schritte;
	}

	public Zug validiere(ZugEingabe zugEingabe) {
		Bewegung bewegung = zugEingabe.getBewegung();
		Feld start = bewegung.getStart();
		Feld ziel  = bewegung.getZiel();
		
		/* TODO: Ist das bei allen Regeln so? -> Auslagern */
		if (!start.istBesetztVon(zugEingabe.getSpieler())) {
			return null;
		}
		
		if (start.getWeg(ziel).size()-1 != schritte) {
			return null;
		}
		
		Zug zug = new Zug();
		zug.fuegeHinzu(new Aktion(start, ziel));
		
		/* TODO: Aktion für Figur auf Ziel heimschicken. */
		
		return zug;
	}
}
