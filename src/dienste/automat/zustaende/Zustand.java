package dienste.automat.zustaende;

import dienste.automat.Automat;

/**
 * Zustand in einem Zustands-Automaten. Jedem Zustand gewisse Tätigkeiten beim betreten
 * bzw. verlassen zugewiesen werden.
 * 
 * Die Klasse sollte nicht direkt abgeleitet werden, stattessen sollten 
 * die Klassen {@link AktiverZustand} und {@link PassiverZustand} erweitert werden.
 */
public abstract class Zustand {
	/**
	 * Wird beim registrieren des Zustandes aufgerufen und ermöglicht 
	 * es ihm zu wissen in welchem Automaten er gebraucht wird. Es ist den abgeleiteten
	 * Klassen wie und in welcher Form sie die Referenz abspeichern wollen.
	 * 
	 * @param automat Zu dem Zustand gehörender Auotmat
	 */
	public abstract void setAutomat(Automat automat);

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
}
