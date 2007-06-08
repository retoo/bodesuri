package dienste.automat;

import dienste.eventqueue.Event;
import dienste.eventqueue.EventQueue;

public class EventQuelleAdapter implements EventQuelle {
	private EventQueue eventQueue;

	public EventQuelleAdapter(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see dienste.automat.EventQuelle#getEvent()
	 */
	public Event getEvent() {
		return eventQueue.dequeue();
	}
}
