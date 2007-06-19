package dienste.netzwerk;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import dienste.serialisierung.SerialisierungsKontext;

/**
 * Dient dem Server und dem Client zur Kommunikation mit dem jewiligen
 * Kommunikationspartner.
 *
 */
public abstract class EndPunkt implements EndPunktInterface {
	protected Socket socket;
	private Thread empfaengerThread;
	private ObjectOutputStream outputStream;
	private Empfaenger empfaenger;
	protected SerialisierungsKontext serialisierungsKontext;

	protected void startVerhandlung(BriefKastenInterface briefkasten) throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());

		/*
		 * Bis auf weiteres machen wir das Empfanger der Nachrichten mal in
		 * einem seperaetn Thread. Später kann man das evtl. mit Select etc.
		 * besser lösen
		 */
		empfaenger = new Empfaenger(this, socket, briefkasten);
		empfaengerThread = new Thread(empfaenger);
		serialisierungsKontext.registriere(empfaengerThread);
		empfaengerThread.start();
	}

	/* (non-Javadoc)
     * @see dienste.netzwerk.EndPunktInterface#sende(dienste.netzwerk.Nachricht)
     */
	public void sende(Nachricht nachricht) {
		try {
			outputStream.writeObject(nachricht);
		} catch (IOException e) {
			throw new RuntimeException(e);	// TODO: reto -> ist hier wirklich keine VerbindungWegException?
		}
	}

	/* (non-Javadoc)
     * @see dienste.netzwerk.EndPunktInterface#ausschalten()
     */
	public void ausschalten() throws IOException {
		socket.shutdownInput();
		socket.shutdownOutput();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		InetAddress addr = socket.getInetAddress();
		return addr.getHostAddress() + ":" + socket.getPort();
	}
}
