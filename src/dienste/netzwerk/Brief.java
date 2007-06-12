package dienste.netzwerk;

/**
 * Repräsentiert eine Netzwek-Nachricht.
 */
public class Brief {
	/**
	 * Absender der Nachricht
	 */
	public EndPunktInterface absender;
	/**
	 * Im Brief enhaltende Nachricht
	 */
	public Nachricht nachricht;

	/**
	 * Erstellt einen neuen Brief.sollte nur intern verwendet.
	 *
	 * @param absender Absender
	 * @param nachricht Nachricht
	 */
	protected Brief(EndPunktInterface absender, Nachricht nachricht) {
		this.absender = absender;
		this.nachricht = nachricht;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + absender + ": " + nachricht + ")";
	}
}
