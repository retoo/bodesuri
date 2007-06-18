package applikation.events;

import dienste.eventqueue.Event;

public class ChatEingabeEvent extends Event {
	public final String text;

	public ChatEingabeEvent(String text) {
	    this.text = text;
    }
}
