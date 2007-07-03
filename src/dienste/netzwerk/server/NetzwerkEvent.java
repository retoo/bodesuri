package dienste.netzwerk.server;

import dienste.eventqueue.Event;
import dienste.netzwerk.Brief;

/**
 * Event der ausgelöst wird wenn eine Netzwerknachricht eintrifft.
 */
public class NetzwerkEvent extends Event {
	/**
	 * Im Event enthaltene Brief
	 */
	public final Brief brief;

	/**
	 * Ein Netzwerkevent ist eingetroffen
	 * @param brief Enthält die eingetroffene Nachricht.
	 */
	public NetzwerkEvent(Brief brief) {
		this.brief = brief;
	}

	/* (non-Javadoc)
	 * @see dienste.eventqueue.Event#toString()
	 */
	public String toString() {
		return super.toString() + " " + brief.nachricht;
	}
}
