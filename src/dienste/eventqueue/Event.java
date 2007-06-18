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
		/* TODO: getSimpleName returned bereits einen String (-reto ) */
		return getClass().getSimpleName().toString();
	}

	public boolean istLeise() {
		return false;
	}
}
