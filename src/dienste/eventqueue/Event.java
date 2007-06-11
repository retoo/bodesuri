package dienste.eventqueue;

/**
 * Basis Klasse für alle Events
 */
public abstract class Event {
	/* wird von Abgeleiteten Klassen verfeinert */

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getClass().getSimpleName().toString();
	}
}
