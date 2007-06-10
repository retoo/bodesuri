package applikation.server;

import pd.Spiel;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.server.Server;

public class SpielDaten{
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
}
