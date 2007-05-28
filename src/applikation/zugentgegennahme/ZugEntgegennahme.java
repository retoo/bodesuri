/**
 * @(#) ZugEntgegennahme.java
 */

package applikation.zugentgegennahme;

import pd.brett.Feld;
import pd.zugsystem.Bewegung;
import applikation.events.BewegungEingegebenEvent;
import dienste.automat.EventQueue;

// TODO: Sollte dies nun nicht eher BewegungsEntgegennahme heissen?
/**
 * Nimmt die vom Spieler gemachten Züge (Klicks auf Felder) entgegen und sendet
 * die kompletten Züge an den Automaten zur Validierung.
 */
public class ZugEntgegennahme {
	private ZugStatus zugStatus;
	private Feld start;
	private Feld ziel;

	private EventQueue queue;

	/**
	 * Eine neue Zugentgegennahme ermöglichen.
	 * 
	 * @param queue
	 *            Queue an die die erfassten Züge gesendet werden.
	 */
	public ZugEntgegennahme(EventQueue queue) {
		this.queue = queue;
		zugStatus = new WartenAufStarteingabe();
	}

	/**
	 * Status-Handhabung für die Zugentgegennahme.
	 */
	private abstract class ZugStatus {
		/**
		 * Handle zum Reagieren auf einen Zug.
		 * 
		 * @param feld
		 *            Das angeklickte Feld
		 */
		abstract public void handle(Feld feld);
	}

	private class WartenAufStarteingabe extends ZugStatus {
		public void handle(Feld feld) {
			start = feld;
			zugStatus = new WartenAufZieleingabe();
		}
	}

	private class WartenAufZieleingabe extends ZugStatus {
		public void handle(Feld feld) {
			ziel = feld;
			zugStatus = new ZugErfasst();
			zugBestaetigen(start, ziel);
		}
	}

	private class ZugErfasst extends ZugStatus {
		public void handle(Feld feld) {
		}
	}

	/**
	 * Setzt den neuen Status der ZugEntgegennahme und entscheidet, ob das Feld
	 * das Start- oder das Zielfeld ist.
	 * 
	 * @param feld
	 */
	public void ziehen(Feld feld) {
		zugStatus.handle(feld);
	}

	/**
	 * Nachdem ein Zug erfolgreich erfasst wurde wird hier die resultierende
	 * Bewegung erstellt.
	 * 
	 * @param start
	 * @param ziel
	 */
	private void zugBestaetigen(Feld start, Feld ziel) {
		Bewegung bewegung = new Bewegung(start, ziel);
		queue.enqueue(new BewegungEingegebenEvent(bewegung));
	}
}
