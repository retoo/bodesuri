package applikation.server;

import applikation.nachrichten.SpielBeitreten;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.NetzwerkEvent;

public class TestSpieler {
	public EndPunktInterface endpunkt;
	public EventQueue eventQueue;
	public String name;

	public TestSpieler(String name, EndPunktInterface client, EventQueue eventQueue) {
		this.name = name;
		this.endpunkt = client;
		this.eventQueue = eventQueue;
	}

	public void sendeBeitritt() {
		sende(new SpielBeitreten(name));
	}

	private void sende(Nachricht nachricht) {
		endpunkt.sende(nachricht);
	}

	public Nachricht getNachricht() {
		NetzwerkEvent netzwerkEvent = (NetzwerkEvent) eventQueue.dequeue();
		return netzwerkEvent.brief.nachricht;
	}


	public boolean hatNachrichten() {
		return !eventQueue.isLeer();
	}
}
