package applikation.client.events;

import applikation.client.pd.Feld;
import dienste.eventqueue.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer mit der Maus aus einem Feld
 * hovert.
 */
public class HoverEndeEvent extends Event {
	public final Feld feld;

	public HoverEndeEvent(Feld feld) {
		this.feld = feld;
	}

	public boolean istLeise() {
		return true;
	}
}
