package pd.regelsystem;

import pd.brett.Feld;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;

public class VorwaertsRegel extends Regel {
	private int schritte;
	
	public VorwaertsRegel(int schritte) {
		this.schritte = schritte;
	}

	public Regel validiere(ZugEingabe zugEingabe) {
		Bewegung bewegung = zugEingabe.getBewegung();
		Feld start = bewegung.getStart();
		
		/* TODO: Ist das bei allen Regeln so? -> Auslagern */
		if (!start.istBesetztVon(zugEingabe.getSpieler())) {
			return null;
		}
		
		if (start.getWeg(bewegung.getZiel()).size()-1 != schritte) {
			return null;
		}
		
		return this;
	}

	/* TODO: Andere Figur auf Ziel heim schicken */
	public void ausfuehren(ZugEingabe zugEingabe) {
		Feld start = zugEingabe.getBewegung().getStart();
		Feld ziel  = zugEingabe.getBewegung().getZiel();
		start.versetzeFigurAuf(ziel);
	}

}
