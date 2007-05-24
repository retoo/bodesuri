package dienste.netzwerk.server;

import java.io.IOException;

import dienste.automat.EventQueue;
import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.BriefkastenAdapter;
import dienste.netzwerk.Daemon;

public class Server {
	private static final int PORT = 7788;
	private BriefKastenInterface serverBriefkasten;
	public EventQueue queue;

	public Server(EventQueue queue) throws IOException {
		this.queue = queue;
		serverBriefkasten = new BriefkastenAdapter(queue);

		Daemon daemon = new Daemon(PORT, serverBriefkasten, queue);

		Thread t = new Thread(daemon);
		t.start();
	}
}
