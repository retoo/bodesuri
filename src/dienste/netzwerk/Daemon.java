package dienste.netzwerk;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import dienste.automat.EventQueue;


public class Daemon implements Runnable {
	private BriefKastenInterface briefkasten;
	private ServerSocket serverSock;
	private EventQueue queue;

	public Daemon(int port, BriefKastenInterface briefkasten, EventQueue queue)
	        throws IOException {
		this.serverSock = new ServerSocket(port);
		this.briefkasten = briefkasten;
		this.queue = queue;
	}

	public void run() {
		try {
			while (true) {
				Socket clientSocket = serverSock.accept();
				EndPunkt client = new EndPunkt(clientSocket, briefkasten);

				queue.enqueue(new NeueVerbindung(client));
			}
		} catch (IOException e) {
			System.out.println("IOException im Daemon");
			e.printStackTrace();
			System.exit(99);
		}
	}
}
