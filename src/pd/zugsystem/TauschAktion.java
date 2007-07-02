package pd.zugsystem;

import pd.spiel.brett.Feld;
import pd.spiel.spieler.Figur;

/**
 * Aktion, die zwei Figuren tauscht.
 */
public class TauschAktion extends Aktion {
	public TauschAktion(Feld start, Feld ziel) {
	    super(start, ziel);
    }

	public void ausfuehren() {
		if (start.istGeschuetzt() || ziel.istGeschuetzt()) {
			throw new RuntimeException("Es dürfen keine geschützten Figuren getauscht werden.");
		}

		Figur tmp = ziel.getFigur();
		start.versetzeFigurAuf(ziel);
		start.setFigur(tmp);
	}
}
