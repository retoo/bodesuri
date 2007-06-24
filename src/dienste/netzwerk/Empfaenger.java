package dienste.netzwerk;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import dienste.serialisierung.SerialisierungsKontext;


public class Empfaenger implements Runnable {
	private BriefKastenInterface briefkasten;
	private ObjectInputStream inputStream;
	private EndPunktInterface endpunkt;
	private SerialisierungsKontext serialisierungsKontext;

	protected Empfaenger(EndPunktInterface endpunkt, Socket socket,
	        BriefKastenInterface briefkasten, SerialisierungsKontext serialisierungsKontext) throws IOException {
		inputStream = new ObjectInputStream(socket.getInputStream());
		this.endpunkt = endpunkt;
		this.briefkasten = briefkasten;
		this.serialisierungsKontext = serialisierungsKontext;
	}

	public void run() {
		try {
			serialisierungsKontext.registriere(Thread.currentThread());

			while (true) {
				Object obj = inputStream.readObject();

				Nachricht nachricht = (Nachricht) obj;
				Brief brief = new Brief(endpunkt, nachricht);

				briefkasten.einwerfen(brief);
			}
		} catch (EOFException eof) {
			Brief brief = new Brief(endpunkt, new VerbindungGeschlossen());
			briefkasten.einwerfen(brief);
			return;
		} catch (IOException e) {
			Brief brief = new Brief(endpunkt, new VerbindungGeschlossen());
			briefkasten.einwerfen(brief);
			return;
		} catch (ClassNotFoundException e) {
			/*
			 * der Client kannte die Klasse nicht die übertragen wurde, fehler
			 * schwerler
			 */
			System.out
			          .println("ClassNotFoundException im Empfänger (Endpunkt: "
			                   + endpunkt + ")");
			e.printStackTrace();
			System.exit(99);
		}
	}
}
