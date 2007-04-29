package spielplatz;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import spielplatz.hilfsklassen.Brief;
import spielplatz.hilfsklassen.NeueVerbindung;

public class Akzeptierer implements Runnable {
	private ServerSocket socket;
	private Briefkasten briefkasten;

	public Akzeptierer(ServerSocket socket, Briefkasten briefkasten) {
		this.socket = socket;
		this.briefkasten = briefkasten;
	}

	public void run() {
		try {
			while (true) {
				Socket clientSocket = socket.accept();
				EndPunkt client = new EndPunkt(clientSocket, briefkasten);
				briefkasten.einwerfen(new Brief(client, new NeueVerbindung(client)));
			}
		} catch (IOException e) {
			// TODO Sinnvolles Handling bei Fehlern
			e.printStackTrace();
		}

	}

}
