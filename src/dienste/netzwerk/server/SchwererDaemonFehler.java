package dienste.netzwerk.server;

import dienste.eventqueue.Event;

/**
 * Event der einen schweren Daemonfehler meldet.
 */
public class SchwererDaemonFehler extends Event {
	/**
	 * Aufgetauchte Exception
	 */
	public final Exception exception;


	public SchwererDaemonFehler(Exception e) {
		this.exception = e;
	}
}
