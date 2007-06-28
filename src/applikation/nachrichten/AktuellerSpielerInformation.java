package applikation.nachrichten;

import pd.spieler.Spieler;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Server dem Client mitteilt wer am Zug ist.
 */
public class AktuellerSpielerInformation extends Nachricht {
	public final Spieler spieler;

	public AktuellerSpielerInformation(Spieler spieler) {
		this.spieler = spieler;
	}
}
