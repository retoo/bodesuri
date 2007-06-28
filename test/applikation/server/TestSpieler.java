package applikation.server;

import applikation.nachrichten.SpielBeitreten;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.server.NetzwerkEvent;

/**
 * Stellt eine Hilfsklasse für den Test des Servers dar. Der TestSpieler
 * simuliert einen Spieler und dessen Verhalten.
 */
public class TestSpieler {
	public EndPunktInterface endpunkt;

	public EventQueue eventQueue;

	public String name;

	/**
	 * Erstellt einen neuen TestSpieler mit Namen, einem Endpunkt und einer
	 * EventQueue.
	 * 
	 * @param name
	 *            Name des Spielers
	 * @param client
	 *            Endpunkt für den Client
	 * @param eventQueue
	 *            EventQueue
	 */
	public TestSpieler(String name, EndPunktInterface client,
			EventQueue eventQueue) {
		this.name = name;
		this.endpunkt = client;
		this.eventQueue = eventQueue;
	}

	/**
	 * Sendet eine neue SpielBeitreten-Nachricht an den Server im Namen des
	 * Spielers.
	 */
	public void sendeBeitritt() {
		sende(new SpielBeitreten(name));
	}

	/**
	 * Sendet eine Nachricht im Namen des Spielers an den Server.
	 * 
	 * @param nachricht
	 *            Zu sendende Nachricht.
	 */
	private void sende(Nachricht nachricht) {
		endpunkt.sende(nachricht);
	}

	/**
	 * @return Empfangene Nachricht.
	 */
	public Nachricht getNachricht() {
		NetzwerkEvent netzwerkEvent = (NetzwerkEvent) eventQueue.dequeue();
		return netzwerkEvent.brief.nachricht;
	}

	/**
	 * @return Prüft, ob eine Nachricht empfangen wurde.
	 */
	public boolean hatNachrichten() {
		return !eventQueue.isLeer();
	}
}
