package dienste.netzwerk.server;

import java.io.IOException;

import dienste.netzwerk.Briefkasten;
import dienste.netzwerk.Daemon;

public class Server {
	
	
	private static final int PORT = 7788;
	protected static final int MAXSPIELER = 1;
	protected Briefkasten serverBriefkasten;

	public Server() throws IOException {
		serverBriefkasten = new Briefkasten();

		Daemon daemon = new Daemon(PORT, serverBriefkasten);

		Thread t = new Thread(daemon);
		t.start();
	}
}
