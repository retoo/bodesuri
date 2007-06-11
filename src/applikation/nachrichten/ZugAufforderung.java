package applikation.nachrichten;

import pd.spieler.Spieler;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht die einen Client auffordert einen Zug zu erfassen.
 *
 */
public class ZugAufforderung extends Nachricht {
	public final Spieler spieler;
	public ZugAufforderung(Spieler spieler) {
		this.spieler = spieler;
	}
}
