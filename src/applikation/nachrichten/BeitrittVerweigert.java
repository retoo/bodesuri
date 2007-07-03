package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

/**
 * Nachricht die dem Clienten meldet, dass der Server den Beitritten verweigert hat.
 */
public class BeitrittVerweigert extends Nachricht {
	public final String meldung;

	public BeitrittVerweigert(String meldung) {
		this.meldung = meldung;
	}
}
