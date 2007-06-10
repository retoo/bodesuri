package applikation.events;

import dienste.eventqueue.Event;
import pd.brett.Feld;

public class HoverStartEvent extends Event {
	public final Feld feld;

	public HoverStartEvent(Feld feld) {
	    this.feld = feld;
    }
}
