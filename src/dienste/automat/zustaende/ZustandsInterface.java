package dienste.automat.zustaende;

public interface ZustandsInterface {
	/**
	 * Die entry() Methode wird augefrufen bevor die Events verarbeitet werden.
	 */
	public void entry();

	/**
	 * Die exit() Methode wird nach der Verarbeitung der Events aufgerufen
	 */
	public void exit();
}
