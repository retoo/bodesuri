package ui;

import applikation.events.VerbindeEvent;
import dienste.eventqueue.EventQueue;

public class Steuerung {
	private EventQueue eventQueue;

	public Steuerung(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

	/**
	 * Dem Automaten auftragen eine Verbindung zum Server aufzubauen.
	 *
	 * @param host
	 *            Hostname des Servers
	 * @param port_raw
	 *            Port des Servers
	 * @param spieler
	 *            Name des Spielers
	 */
	public void verbinde(String host, int port_raw, String spieler) {
		VerbindeEvent ve = new VerbindeEvent(host, port_raw, spieler);
		eventQueue.enqueue(ve);
	}
}
