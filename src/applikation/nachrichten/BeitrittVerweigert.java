package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

public class BeitrittVerweigert extends Nachricht {
	public final String meldung;

	public BeitrittVerweigert(String meldung) {
		this.meldung = meldung;
	}
}
