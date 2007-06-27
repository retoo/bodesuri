package applikation.client.events;

import applikation.client.pd.Feld;
import dienste.eventqueue.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer ein Feld auswählt.
 */
public class FeldGewaehltEvent extends Event {
	public final Feld feld;

	public FeldGewaehltEvent(Feld feld) {
		this.feld = feld;
	}
}
