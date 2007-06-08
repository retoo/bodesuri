package dienste.netzwerk;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import dienste.eventqueue.EventQueue;



/**
 * Netzwerkdämon der neue Netzwerk-Verbindungen akzeptiert und diese den
 * dazugehörigen Server meldet.
 * 
 */
public class Daemon implements Runnable {
	private BriefKastenInterface briefkasten;
	private ServerSocket serverSock;
	private EventQueue queue;

	/**
	 * Erstellt einen neuen Dämon.
	 * 
	 * @param port TCP-Port auf welchem gelauscht werden soll
	 * @param briefkasten Briefkasten in welchen Nachrichten der Verbindungen abgelegt werden soll
	 * @param queue Queue über welche die neue Verbindung angekündet wird 
	 * @throws IOException Bei Netzwerkproblemen
	 */
	public Daemon(int port, BriefKastenInterface briefkasten, EventQueue queue)
	        throws IOException {
		this.serverSock = new ServerSocket(port);
		this.briefkasten = briefkasten;
		this.queue = queue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			while (true) {
				Socket clientSocket = serverSock.accept();
				EndPunkt client = new EndPunkt(clientSocket, briefkasten);

				queue.enqueue(new NeueVerbindung(client));
			}
		} catch (IOException e) {
			/* FIXME: nicer */
			System.out.println("IOException im Daemon");
			e.printStackTrace();
			System.exit(99);
		}
	}
}
