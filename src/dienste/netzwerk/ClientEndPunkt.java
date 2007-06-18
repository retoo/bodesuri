package dienste.netzwerk;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import dienste.serialisierung.SerialisierungsKontext;

public class ClientEndPunkt extends EndPunkt{
	/**
	 * Startet die Kommunikation mit dem übergebenen System (Hostname & Port).
	 * Dieser Konstrutkur wird vom Client verwendet.
	 *
	 *
	 * @param hostname
	 *            Hostname des zu verbindenen Systems
	 * @param port
	 *            Port des zu verbinden Systems
	 * @param briefkasten Briefkasten in welchem die Nachrichten abgelegt werden können
	 * @param sk Serialisierungskontext
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public ClientEndPunkt(String hostname, int port, BriefKastenInterface briefkasten,
	                SerialisierungsKontext sk)
	        throws UnknownHostException, IOException {
		System.out.println("Verbinde zu " + hostname + ":" + port);

		serialisierungsKontext = sk;
		socket = new Socket(hostname, port);

		startVerhandlung(briefkasten);

		System.out.println("Verbindungsaufbau erfolgreich");
	}
}
