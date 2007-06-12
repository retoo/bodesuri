package dienste.netzwerk;

import java.io.IOException;

public interface EndPunktInterface {

	/**
	 * Sende die angegebene Nachricht an den Host auf der anderen Seite des
	 * Kommunikationkanals.
	 *
	 * @param nachricht
	 *            zu übertragende Nachricht
	 */
	public abstract void sende(Nachricht nachricht);

	/**
	 * Beendet die Verbindung
	 *
	 * @throws IOException
	 */
	public abstract void ausschalten() throws IOException;

}
