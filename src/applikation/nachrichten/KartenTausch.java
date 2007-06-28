package applikation.nachrichten;

import pd.karten.Karte;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit der Client dem Server und der Server dem Client die getauschte
 * Karte übermittelt.
 */
public class KartenTausch extends Nachricht {
	public final Karte karte;

	public KartenTausch(Karte karte) {
		this.karte = karte;
	}
}
