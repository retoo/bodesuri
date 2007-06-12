package dienste.automat.zustaende;

import dienste.automat.KeinUebergangException;
import dienste.eventqueue.Event;

/**
 * Zustand in einem Zustands-Automaten. Jedem Zustand gewisse Tätigkeiten beim betreten
 * bzw. verlassen zugewiesen werden.
 *
 * Die Klasse sollte nicht direkt abgeleitet werden, stattessen sollten
 * die Klassen {@link Zustand} und {@link PassiverZustand} erweitert werden.
 */
public abstract class Zustand implements ZustandsInterface {
	/**
	 * Die entry() Methode wird augefrufen bevor die Events verarbeitet werden.
	 */
	public void entry() {
	}

	/**
	 * Die exit() Methode wird nach der Verarbeitung der Events aufgerufen
	 */
	public void exit() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getClass().getSimpleName();
	}

	/**
     * Verarbeitet einen übergebenen Event und teilt dem Zustandsautomaten mit
     * welcher nächster Zustand ausgeführt werden soll. Diese Methode führt
     * hierzu je nach Event spezialisierte handler Methoden aus welche von den
     * verschiedenen Zuständen implementiert werden können
     *
     * @param event
     *            Ausführender Event
     * @return Den nächsten Zustand
     */
    public Class<? extends Zustand> handle(Event event) {
    	String msg = "Zustand " + this + " hat keinen Übergang für den Event "
    	             + event;
    	throw new UnbekannterEventException(msg);
    }

	protected Class<? extends Zustand> keinUebergang() {
    	throw new KeinUebergangException("Kein Übergang definiert in Zustand "
    	                                 + this);
    }

	protected Class<? extends Zustand> ignoriereEvent(String event) {
		if (event != null)
			System.out.println("Ignoriere Event: " + event);

		return this.getClass();
    }
}
