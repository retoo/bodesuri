package dienste.netzwerk.server;

import java.io.IOException;


import dienste.eventqueue.EventQueue;
import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.BriefkastenAdapter;
import dienste.netzwerk.Daemon;
import dienste.netzwerk.NetzwerkEvent;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * Server welcher die Kommunikation mit mehreren Clients ermöglicht. Beim Start
 * wird ein Socket an den TCP Port 7788 gebunden welcher eingehende Verbindungen
 * akzeptiert. Eingehende Nachrichten könnnen über eine zu übergebende Queue
 * empfangne werden.
 * 
 * Neue Verbindungen werden über 
 * 
 */
public class Server {
	private static final int PORT = 7788;
	private BriefKastenInterface serverBriefkasten;
	private Daemon daemon;
	
	/**
	 * Queue für eingehende Events
	 */
	public EventQueue queue;


	/**
	 * Erstellt einen neuen Server.
	 * 
	 * @param queue EventQueue in welche die eingehenden {@link NetzwerkEvent} gelegt werden
	 * @throws IOException Bei Problemen mit dem Socket wird eine IOExpceiton geworfen
	 */
	public Server(EventQueue queue, SerialisierungsKontext sk) throws IOException {
		this.queue = queue;
		serverBriefkasten = new BriefkastenAdapter(queue);

		daemon = new Daemon(PORT, serverBriefkasten, queue, sk);

		Thread t = new Thread(daemon);
		t.start();
	}
}
