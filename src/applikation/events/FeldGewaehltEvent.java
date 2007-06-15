package applikation.events;

import applikation.client.pd.Feld;
import dienste.eventqueue.Event;

public class FeldGewaehltEvent extends Event {
	public final Feld feld;
	
	public FeldGewaehltEvent(Feld feld) {
		this.feld = feld;
	}
}
