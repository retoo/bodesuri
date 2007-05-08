package dienste.netzwerk;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import dienste.netzwerk.nachrichten.NeueVerbindung;


public class Daemon implements Runnable {	
	private Briefkasten briefkasten;

	private ServerSocket serverSock;

	public Daemon(int port, Briefkasten briefkasten) throws IOException {
		serverSock = new ServerSocket(port);
		this.briefkasten = briefkasten;
	}

	public void run() {
		try {
			while (true) {
				Socket clientSocket = serverSock.accept();
				EndPunkt client = new EndPunkt(clientSocket, briefkasten);
				briefkasten.einwerfen(new Brief(client, new NeueVerbindung(client)));
			}
		} catch (IOException e) {
			System.out.println("IOException im Daemon");
			e.printStackTrace();
			System.exit(99);
		}

	}

}
