package dienste.netzwerk.server;

import java.io.IOException;

import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.BriefkastenAdapter;
import dienste.netzwerk.Daemon;
import dienste.statemachine.EventQueue;

public class Server {
	private static final int PORT = 7788;
	protected static final int MAXSPIELER = 1;
	protected BriefKastenInterface serverBriefkasten;
	protected EventQueue queue;

	public Server() throws IOException {
		this.queue = new EventQueue();
		serverBriefkasten = new BriefkastenAdapter(queue);

		Daemon daemon = new Daemon(PORT, serverBriefkasten);

		Thread t = new Thread(daemon);
		t.start();
	}
}
