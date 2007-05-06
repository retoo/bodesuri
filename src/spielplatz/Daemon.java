package spielplatz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import spielplatz.hilfsklassen.Brief;
import spielplatz.hilfsklassen.NeueVerbindung;

public class Daemon implements Runnable {
	private static final int PORT = 3334;
	
	private Briefkasten briefkasten;

	private ServerSocket serverSock;

	public Daemon(Briefkasten briefkasten) throws IOException {
		serverSock = new ServerSocket(PORT);
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
