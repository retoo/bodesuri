package pd.zugsystem;

import pd.spiel.brett.Feld;
import pd.spiel.spieler.Spieler;

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
