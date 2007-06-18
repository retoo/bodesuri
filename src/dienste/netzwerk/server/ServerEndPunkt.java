package dienste.netzwerk.server;

import java.io.IOException;
import java.net.Socket;

import dienste.netzwerk.BriefKastenInterface;
import dienste.netzwerk.EndPunkt;
import dienste.serialisierung.SerialisierungsKontext;

public class ServerEndPunkt extends EndPunkt {

	/**
	 * Startet die Kommunkation mit dem übergebenen Socket. Dieser Konstrutkur
	 * wird vom Server verwendet.
	 *
	 * @param socket
	 *            zu verwendendender Socket
	 * @param briefkasten
	 *            Briefkasten in welchem die Nachrichten abgelegt werden können
	 * @param sk Serialisierungskontext
	 * @throws IOException
	 */
	public ServerEndPunkt(Socket socket, BriefKastenInterface briefkasten,
	                SerialisierungsKontext sk)
	        throws IOException {
		this.socket = socket;
		this.serialisierungsKontext = sk;

		startVerhandlung(briefkasten);
	}
}
