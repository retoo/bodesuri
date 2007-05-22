package dienste.netzwerk;

import applikation.client.events.NetzwerkEvent;
import dienste.statemachine.EventQueue;

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
