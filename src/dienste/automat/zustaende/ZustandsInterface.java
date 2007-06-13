package dienste.automat.zustaende;

public interface ZustandsInterface {
	/**
	 * onEntry wird augefrufen bevor die Events verarbeitet werden.
	 */
	public void onEntry();

	/**
	 * onExit wird nach der Verarbeitung der Events aufgerufen.
	 */
	public void onExit();
}
