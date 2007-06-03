package applikation.nachrichten;

import pd.karten.Karte;
import dienste.netzwerk.Nachricht;

public class KartenTausch extends Nachricht {
	public final Karte karte;

	public KartenTausch(Karte karte) {
		this.karte = karte;
	}
}
