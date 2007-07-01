package applikation.nachrichten;

import dienste.netzwerk.Nachricht;
import pd.spiel.spieler.Spieler;

/**
 * Nachricht mit der Server dem Client mitteilt wenn jemand Aufgegeben hat.
 */
public class AufgabeInformation extends Nachricht {
	public final Spieler spieler;

	public AufgabeInformation(Spieler spieler) {
	    this.spieler = spieler;
    }
}
