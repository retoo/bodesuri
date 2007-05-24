package applikation.client.events;

import pd.karten.Karte;
import dienste.automat.Event;

public class KarteGewaehltEvent extends Event {
	public final Karte karte;
	
	public KarteGewaehltEvent (Karte karte) {
		this.karte = karte;
	}

}
