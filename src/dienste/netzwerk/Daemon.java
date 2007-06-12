package dienste.netzwerk;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import dienste.eventqueue.EventQueue;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * Netzwerkdämon der neue Netzwerk-Verbindungen akzeptiert und diese den
 * dazugehörigen Server meldet.
 *
 */
public class Daemon implements Runnable {
	private BriefKastenInterface briefkasten;
	private ServerSocket serverSock;
	private EventQueue queue;
	private SerialisierungsKontext serialisierungsKontext;

	/**
	 * Erstellt einen neuen Dämon.
	 *
	 * @param port
	 *            TCP-Port auf welchem gelauscht werden soll
	 * @param briefkasten
	 *            Briefkasten in welchen Nachrichten der Verbindungen abgelegt
	 *            werden soll
	 * @param queue
	 *            Queue über welche die neue Verbindung angekündet wird
	 * @param sk Serialisierungskontext
	 * @throws IOException
	 *             Bei Netzwerkproblemen
	 */
	public Daemon(int port, BriefKastenInterface briefkasten, EventQueue queue,
	              SerialisierungsKontext sk)
	        throws IOException {
		this.serverSock = new ServerSocket(port);
		this.briefkasten = briefkasten;
		this.queue = queue;
		this.serialisierungsKontext = sk;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			while (true) {
				Socket clientSocket = serverSock.accept();
				EndPunktInterface client = new EndPunkt(clientSocket, briefkasten,
				                               serialisierungsKontext);

				queue.enqueue(new NeueVerbindung(client));
			}
		} catch (Exception e) {
			try {
				/* Fehler dem HauptThread melden und abbrechen */
				queue.enqueue(new SchwererDaemonFehler(e));
			} catch (Exception e_nested) {
				/* sogar das Fehler-Melden geht nicht mehr */
				System.out.println("Doppel-Fehler");
				e.printStackTrace();
				e_nested.printStackTrace();
				System.exit(99);
			}
		}
	}
}
