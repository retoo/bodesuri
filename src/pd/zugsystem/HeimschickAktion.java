package pd.zugsystem;

import pd.brett.Feld;
import pd.spieler.Spieler;

public class HeimschickAktion extends Aktion {
	public HeimschickAktion(Feld start) {
	    super(start, null);
    }
	
	public void ausfuehren() {
		Spieler spieler = start.getFigur().getSpieler();
		ziel = spieler.getSpiel().getBrett().getFreiesLagerFeldVon(spieler);
		start.versetzeFigurAuf(ziel);
		ziel.setGeschuetzt(true);
	}
}
