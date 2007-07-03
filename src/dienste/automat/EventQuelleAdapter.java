package dienste.automat;

import dienste.eventqueue.Event;
import dienste.eventqueue.EventQueue;

/**
 * Adapter für die EventQueue welche das {@link EventQueue} Interface implementiert.
 *
 * @see EventQueue
 */
public class EventQuelleAdapter implements EventQuelle {
	private EventQueue eventQueue;

	/**
	 * Ertellt den Adapter unter Benutzung der übergebenen Queue.
	 *
	 * @param eventQueue
	 */
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
