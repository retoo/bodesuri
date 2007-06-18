package applikation.client.events;

import applikation.client.pd.Karte;
import dienste.eventqueue.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer eine Karte ausgewählt hat.
 */
public class KarteGewaehltEvent extends Event {
	/**
	 * Die ausgewählte Karte
	 */
	public final Karte karte;

	/**
	 * Der Benutzer hat eine Karte ausgewählt.
	 * @param gewaehlteKarte Die ausgewählte Karte
	 */
	public KarteGewaehltEvent(Karte gewaehlteKarte) {
		this.karte = gewaehlteKarte;
	}

}
