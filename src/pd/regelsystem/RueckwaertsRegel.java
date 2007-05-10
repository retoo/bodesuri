package pd.regelsystem;

import pd.brett.Feld;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;

public class RueckwaertsRegel extends Regel {
	private int schritte;
	
	public RueckwaertsRegel(int schritte) {
		this.schritte = schritte;
	}

	public Regel validiere(Zug zug) {
		Bewegung bewegung = zug.getBewegung();
		Feld start = bewegung.getStart();
		
		if (!start.istBesetztVon(zug.getSpieler())) {
			return null;
		}
		
		if (start.getWegRueckwaerts(bewegung.getZiel()).size()-1 != schritte) {
			return null;
		}
		
		return this;
	}

	public void ausfuehren(Zug zug) {
		Feld start = zug.getBewegung().getStart();
		Feld ziel  = zug.getBewegung().getZiel();
		start.versetzeFigurAuf(ziel);
	}
}
