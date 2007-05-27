package dienste.automat.zustaende;

/**
 * Passiver Zustand welcher direkt abgearbeitet wird ohne zuerst auf Events zu
 * warten. Die handle() Methode ist tortzdem verantwortlich den nächsten zu
 * Zustand zu bestimmen.
 * 
 */
public abstract class PassiverZustand extends Zustand {
	/**
	 * Ist für das Verarbeiten eines passiven Zustandes verwantwortlich und
	 * teilt dem Zustandsautomaten mit welcher nächster Zustand ausgeführt
	 * werden soll.
	 * 
	 * @return der nächste auszuführende Zustand
	 */
	public abstract Zustand handle();
}
