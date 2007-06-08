package dienste.netzwerk;

import dienste.eventqueue.Event;

/**
 * Event der ausgelöst wird wenn eine Netzwerknachricht eintrifft.
 */
public class NetzwerkEvent extends Event {
	final public Brief brief;
	
	/**
	 * Ein Netzwerkevent ist eingetroffen
	 * @param brief Enthält die eingetroffene Nachricht. 
	 */
	public NetzwerkEvent(Brief brief) {
		this.brief = brief;
	}
}
