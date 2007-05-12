package pd.regelsystem;

import pd.brett.Feld;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;

public class RueckwaertsRegel extends Regel {
	private int schritte;
	
	public RueckwaertsRegel(int schritte) {
		this.schritte = schritte;
	}

	public Regel validiere(ZugEingabe zugEingabe) {
		Bewegung bewegung = zugEingabe.getBewegung();
		Feld start = bewegung.getStart();
		
		if (!start.istBesetztVon(zugEingabe.getSpieler())) {
			return null;
		}
		
		if (start.getWegRueckwaerts(bewegung.getZiel()).size()-1 != schritte) {
			return null;
		}
		
		return this;
	}

	public void ausfuehren(ZugEingabe zugEingabe) {
		Feld start = zugEingabe.getBewegung().getStart();
		Feld ziel  = zugEingabe.getBewegung().getZiel();
		start.versetzeFigurAuf(ziel);
	}
}
