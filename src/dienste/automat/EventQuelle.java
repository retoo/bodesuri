package dienste.automat;

/**
 * Beschreibt eine Quelle für Events für den Zustandsautomaten. Die zu verwendende Eventqueue muss 
 * mittels folgendem Interface ansprechbar sein.
 */
public interface EventQuelle {
	/**
	 * Liefert den nächsten anstehenden {@link Event}.
	 * 
	 * @return der nächste zu verarbeitende Event
	 */
	Event getEvent();
}
