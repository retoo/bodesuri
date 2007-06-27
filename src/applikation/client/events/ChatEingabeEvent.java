package applikation.client.events;

import dienste.eventqueue.Event;

/**
 * Event der ausgelöst wird wenn der Benutzer einen Text im Chat eingegeben hat.
 */
public class ChatEingabeEvent extends Event {
	public final String text;

	public ChatEingabeEvent(String text) {
		this.text = text;
	}
}
