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
		return getClass().getSimpleName();
	}

	/**
	 * Subklassen können diese Methode überschreiben und false zurückliefern
	 * wenn die betroffenen Evens nicht mittels Debug-Meldungen gemeldet werden sollen.
	 *
	 * @return liefert true zurück wenn es sich bei diesem Event um einen
	 *         'leisen' Event handeln soll.
	 */
	public boolean istLeise() {
		return false;
	}
}
