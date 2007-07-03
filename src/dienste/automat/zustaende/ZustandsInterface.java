package dienste.automat.zustaende;

/**
 * Definiert das Interface eines Zustandes. Alle Zustände können, falls gewünscht, einen
 * onEntry- bzw. onExit-hook implementieren.
 */
public interface ZustandsInterface {
	/**
	 * onEntry wird bei Eintritt in einen neuen Zustand aufgerufen.
	 */
	public void onEntry();

	/**
	 * onExit wird bei Austritt aus einem Zustand aufgerufen.
	 */
	public void onExit();
}
