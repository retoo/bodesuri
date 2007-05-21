package spielplatz.zustandssynchronisation;

import dienste.netzwerk.Brief;

public class BodesuriEvent extends Event {
	public final Brief brief;
	
	public BodesuriEvent(Brief brief) {
		this.brief = brief;
	}
}