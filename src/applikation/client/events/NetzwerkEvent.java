package applikation.client.events;

import dienste.automat.Event;
import dienste.netzwerk.Brief;

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
