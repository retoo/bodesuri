package applikation.server;

import pd.Spiel;
import pd.SpielThreads;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.server.Server;
import dienste.serialisierung.SerialisierungsKontext;

public class SpielDaten implements SerialisierungsKontext {
	/**
	 * Server
	 */
	public Server server;
	/**
	 * EventQueue
	 */
	public EventQueue queue;
	/**
	 * Spielerschaft
	 */
	public Spielerschaft spielerschaft;

	public Spiel spiel;
	public int anzSpieler;

	public SpielDaten(int anzSpieler) {
		this.anzSpieler = anzSpieler;
	}

	public void registriere(Thread thread) {
		SpielThreads.registriere(thread, spiel);
	}
}
