package applikation.server.zustaende;

import dienste.netzwerk.Nachricht;
import pd.spieler.Spieler;

public class AufgabeInformation extends Nachricht {
	public final Spieler spieler;

	public AufgabeInformation(Spieler spieler) {
	    this.spieler = spieler;
    }
}
