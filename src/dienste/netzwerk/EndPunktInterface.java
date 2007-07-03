package dienste.netzwerk;


/**
 * Inerface eines Endpunktes. Wird durch die Klasse {@link EndPunkt} implementiert.
 */
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
	 */
	public abstract void ausschalten();

}
