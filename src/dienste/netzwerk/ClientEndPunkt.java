package dienste.netzwerk;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import dienste.serialisierung.SerialisierungsKontext;

/**
 * Erstellt eine TCP Verbindung zu einem Server.
 */
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
		serialisierungsKontext = sk;
		socket = new Socket(hostname, port);

		startVerhandlung(briefkasten);
	}
}
