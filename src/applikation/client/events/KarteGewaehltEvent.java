package applikation.client.events;

import pd.karten.Karte;
import dienste.automat.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer eine Karte ausgewählt hat.
 */
public class KarteGewaehltEvent extends Event {
	public final Karte karte;

	/**
	 * Der Benutzer hat eine Karte ausgewählt.
	 * @param karte Die ausgewählte Karte
	 */
	public KarteGewaehltEvent(Karte karte) {
		this.karte = karte;
	}

}
