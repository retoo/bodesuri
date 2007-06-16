package applikation.events;

import applikation.client.pd.Feld;
import dienste.eventqueue.Event;

public class HoverEndeEvent extends Event {
	public final Feld feld;

	public HoverEndeEvent(Feld feld) {
	    this.feld = feld;
    }
}
