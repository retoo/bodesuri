package dienste.netzwerk;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;


public class Empfaenger implements Runnable {
	private BriefKastenInterface briefkasten;
	private ObjectInputStream inputStream;
	private EndPunktInterface endpunkt;

	protected Empfaenger(EndPunktInterface endpunkt, Socket socket,
	        BriefKastenInterface briefkasten) throws IOException {
		inputStream = new ObjectInputStream(socket.getInputStream());
		this.endpunkt = endpunkt;
		this.briefkasten = briefkasten;

		if (endpunkt == null) {
			throw new RuntimeException("Remove me later, FIXME");
		}
	}

	public void run() {
		try {

			while (true) {
				Object obj = inputStream.readObject();

				if (obj == null)
					throw new RuntimeException("FIXME FIXME");

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
