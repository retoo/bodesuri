package pd.zugsystem;

import pd.spiel.brett.Feld;
import pd.spiel.spieler.Spieler;

/**
 * Aktion, die eine Figur ins Lager heimschickt.
 */
public class HeimschickAktion extends Aktion {
	public HeimschickAktion(Feld start) {
	    super(start, null);
    }

	public void ausfuehren() {
		if (start.istGeschuetzt()) {
			throw new RuntimeException("Es darf keine geschützte Figur heimgeschickt werden.");
		}

		Spieler spieler = start.getFigur().getSpieler();
		ziel = spieler.getSpiel().getBrett().getFreiesLagerFeldVon(spieler);
		start.versetzeFigurAuf(ziel);
		ziel.setGeschuetzt(true);
	}
}
