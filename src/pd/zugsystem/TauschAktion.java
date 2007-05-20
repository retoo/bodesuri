package pd.zugsystem;

import pd.brett.Feld;
import pd.spieler.Figur;

public class TauschAktion extends Aktion {
	public TauschAktion(Feld start, Feld ziel) {
	    super(start, ziel);
    }
	
	public void ausfuehren() {
		Figur tmp = ziel.getFigur();
		start.versetzeFigurAuf(ziel);
		start.setFigur(tmp);
	}
}
