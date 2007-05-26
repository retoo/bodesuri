package pd.zugsystem;

import pd.brett.Feld;
import pd.spieler.Figur;

/**
 * Aktion, die zwei Figuren tauscht.
 */
public class TauschAktion extends Aktion {
	public TauschAktion(Feld start, Feld ziel) {
	    super(start, ziel);
    }
	
	/**
	 * Führt Aktion aus, die Figur auf dem Start- kommt auf das Zielfeld und
	 * umgekehrt.
	 */
	public void ausfuehren() {
		Figur tmp = ziel.getFigur();
		start.versetzeFigurAuf(ziel);
		start.setFigur(tmp);
	}
}
