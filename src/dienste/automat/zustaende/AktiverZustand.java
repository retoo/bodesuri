package dienste.automat.zustaende;

import dienste.automat.Event;
import dienste.automat.UnbekannterEventException;

/**
 * Aktiver Zustand. Ein aktiver Zustand ist einer der Events verarbeitet.
 * Verarbeitet der Zustandsautoamt einen aktivn Zustand muss er zuerst auf
 * eingehdne Events warten. Sobald diese vorhanden sind wird die handle(Event)
 * Methode des Zustandes aufgerufen welcher für die Abarbeitung des Events
 * zuständig ist.
 * 
 * Diese Klasse muss abgeleitet werden.
 * 
 * @see PassiverZustand
 * 
 */
public abstract class AktiverZustand extends Zustand {
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
	public Zustand handle(Event event) {
		String msg = "Zustand " + this + " hat keinen Übergang für den Event "
		             + event;
		throw new UnbekannterEventException(msg);
	}
}
