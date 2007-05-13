package pd.regelsystem;

import pd.brett.Feld;
import pd.zugsystem.Aktion;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class RueckwaertsRegel extends Regel {
	private int schritte;
	
	public RueckwaertsRegel(int schritte) {
		this.schritte = schritte;
	}

	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		Bewegung bewegung = zugEingabe.getBewegung();
		Feld start = bewegung.getStart();
		Feld ziel  = bewegung.getZiel();
		
		if (!start.istBesetztVon(zugEingabe.getSpieler())) {
			throw new RegelVerstoss("Zug muss mit Figur begonnen werden.");
		}
		
		if (start.getWegRueckwaerts(ziel).size()-1 != schritte) {
			throw new RegelVerstoss("Zug muss über " + schritte +
			                        " Felder gehen.");
		}
		
		Zug zug = new Zug();
		zug.fuegeHinzu(new Aktion(start, ziel));
		
		/* TODO: Aktion für Figur auf Ziel heimschicken. */
		
		return zug;
	}
}
