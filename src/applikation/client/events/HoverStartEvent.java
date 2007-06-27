package applikation.client.events;

import applikation.client.pd.Feld;
import dienste.eventqueue.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer mit der Maus in ein Feld hovert.
 */
public class HoverStartEvent extends Event {
	public final Feld feld;

	public HoverStartEvent(Feld feld) {
		this.feld = feld;
	}

	public boolean istLeise() {
		return true;
	}
}
