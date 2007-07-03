package dienste.netzwerk;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import dienste.serialisierung.SerialisierungsKontext;

/**
 * Dient dem Server und dem Client zur Kommunikation mit dem jeweiligen
 * Kommunikationspartner.
 */
public abstract class EndPunkt implements EndPunktInterface {
	protected Socket socket;
	private Thread empfaengerThread;
	private ObjectOutputStream outputStream;
	protected SerialisierungsKontext serialisierungsKontext;

	protected void startVerhandlung(BriefKastenInterface briefkasten) throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());

		/*
		 * Der Empfanger der Nachrichten in einem dedizierten Thread.
		 */
		empfaengerThread = new Empfaenger(this, socket, briefkasten, serialisierungsKontext);
		empfaengerThread.start();
	}

	/* (non-Javadoc)
     * @see dienste.netzwerk.EndPunktInterface#sende(dienste.netzwerk.Nachricht)
     */
	public void sende(Nachricht nachricht) {
		try {
			outputStream.writeObject(nachricht);
		} catch (IOException e) {
			throw new VerbindungWegException(e);
		}
	}

	/* (non-Javadoc)
     * @see dienste.netzwerk.EndPunktInterface#ausschalten()
     */
	public void ausschalten() {
		try {
			socket.shutdownInput();
			socket.shutdownOutput();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		InetAddress addr = socket.getInetAddress();
		return addr.getHostAddress() + ":" + socket.getPort();
	}
}
