package spielplatz.zustandssynchronisation.events;

import dienste.netzwerk.Brief;

public class NetzwerkEvent extends Event {
	final public Brief brief;
	
	public NetzwerkEvent(Brief brief) {
		this.brief = brief;
	}
}
