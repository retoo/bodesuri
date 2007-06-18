package applikation.client.events;

import applikation.client.pd.Feld;
import dienste.eventqueue.Event;

public class HoverEndeEvent extends Event {
	public final Feld feld;

	public HoverEndeEvent(Feld feld) {
	    this.feld = feld;
    }

	public boolean istLeise() {
		return true;
	}
}
