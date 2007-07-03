package dienste.netzwerk;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import dienste.eventqueue.EventQueue;
import dienste.serialisierung.SerialisierungsKontext;
import dienste.threads.BodesuriThread;


/**
 * Der Empfänger ist für das Empfangen von {@link Nachricht} über einen
 * {@link EndPunkt} zuständig. Die eingehenden Nachrichten werden der
 * Applikation über das {@link BriefKastenInterface} gemldet. Hinter einem
 * solchen Interface steht in den meisten Fällen eine {@link EventQueue}.
 */
public class Empfaenger extends BodesuriThread {
	private BriefKastenInterface briefkasten;
	private ObjectInputStream inputStream;
	private EndPunktInterface endpunkt;
	private SerialisierungsKontext serialisierungsKontext;

	protected Empfaenger(EndPunktInterface endpunkt, Socket socket,
	        BriefKastenInterface briefkasten, SerialisierungsKontext serialisierungsKontext) throws IOException {
		super("Empfänger " + endpunkt.toString());

		inputStream = new ObjectInputStream(socket.getInputStream());
		this.endpunkt = endpunkt;
		this.briefkasten = briefkasten;
		this.serialisierungsKontext = serialisierungsKontext;
	}

	/**
	 * Startet den Empfänger-Thread
	 */
	public void run() {
		try {
			serialisierungsKontext.registriere(Thread.currentThread());

			while (true) {
				Object obj = inputStream.readObject();

				Nachricht nachricht = (Nachricht) obj;
				Brief brief = new Brief(endpunkt, nachricht);

				briefkasten.einwerfen(brief);
			}
		} catch (IOException e) {
			Brief brief = new Brief(endpunkt, new VerbindungGeschlossen());
			briefkasten.einwerfen(brief);
			return;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
