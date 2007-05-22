package applikation.client.events;

import dienste.netzwerk.Brief;
import dienste.statemachine.Event;

public class NetzwerkEvent extends Event {
	final public Brief brief;
	
	public NetzwerkEvent(Brief brief) {
		this.brief = brief;
	}
}
