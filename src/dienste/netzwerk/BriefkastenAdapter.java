package dienste.netzwerk;

import spielplatz.zustandssynchronisation.EventQueue;
import spielplatz.zustandssynchronisation.events.NetzwerkEvent;

public class BriefkastenAdapter implements BriefKastenInterface {
	private EventQueue queue;

	public BriefkastenAdapter(EventQueue queue) {
	    this.queue = queue;
    }

	public void einwerfen(Brief brief) {
		NetzwerkEvent event = new NetzwerkEvent(brief);
		queue.enqueue(event);
	}
}
