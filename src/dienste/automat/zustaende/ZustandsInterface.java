package dienste.automat.zustaende;

/**
 * Definiert das Interface eines Zustandes. Alle Zustände können, falls gewünscht, einen
 * onEntry() bzw. onExit() hook implementieren.
 */
public interface ZustandsInterface {
	/**
	 * onEntry wird bei eintritt in einen neuen Zustand aufgerufen.
	 */
	public void onEntry();

	/**
	 * onExit wird bei austritt aus einem Zustand aufgerufen
	 */
	public void onExit();
}
