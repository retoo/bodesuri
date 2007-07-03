package dienste.automat.zustaende;

/**
 * Definiert das Interface eines passiven Zustandes. Ein Passiverzustand verarbeitet
 * nur eine festgelegt Aktion und kann auf keine Events reagieren.
 */
public interface PassiverZustand extends ZustandsInterface {
	/**
	 * Aktion des passiven Zustandes
	 *
	 * @return nächster Zustand
	 */
	Class<? extends Zustand> handle();
}
