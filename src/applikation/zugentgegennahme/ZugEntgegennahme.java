/**
 * @(#) ZugEntgegennahme.java
 */

package applikation.zugentgegennahme;

import pd.brett.Feld;
import pd.spieler.Spieler;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class ZugEntgegennahme {
	private static ZugStatus zugStatus = new WartenAufStarteingabe();
	
	/**
	 * Status-Handhabung für die Zugentgegennahme. Alle Innerklassen und Methoden sind static,
	 * könnten aber relativ einfach als Instanzmethoden-/variabeln refaktorisiert werden. Dann
	 * müsste zB. das BrettView eine Instanz von ZugEntgegennahme verwalten, welche dann weiter
	 * an die Teile weitergegeben wird, welche die ZugEntgegennahme dann verwenden.
	 * 
	 * @author HobusP
	 */
	static private abstract class ZugStatus {
		private Feld start;
		private Feld ziel;
		
		public void abbrechen() {
			start = null;
			ziel = null;
		}
		
		public void handle(Feld feld) { 
			zugStatus = new WartenAufZieleingabe();
		}
	}
	static private class WartenAufStarteingabe extends ZugStatus {
		public void handle(Feld feld) {
			super.start = feld;
		}
	}
	static private class WartenAufZieleingabe extends ZugStatus {
		public void handle(Feld feld) {
			super.ziel = feld;
			zugBestaetigen(super.start, super.ziel);
			zugStatus = new WartenAufStarteingabe();
		}
	}
	
	/**
	 * Setzt den neuen Status der ZugEntgegennahme und entscheidet, ob das Feld
	 * das Start- oder das Zielfeld ist.
	 * @param feld
	 */
	public static void ziehen(Feld feld) {
		zugStatus.handle(feld);
	}
	
	/**
	 * Nachdem ein Zug erfolgreich erfasst wurde wird hier die resultierende Bewegung erstellt,
	 * eine ZugEingabe angelegt der validierte Zug zurückgegeben.
	 * @param start
	 * @param ziel
	 * @return
	 */
	private static Zug zugBestaetigen(Feld start, Feld ziel) {
		Bewegung bewegung = new Bewegung(start, ziel);
		ZugEingabe ze = new ZugEingabe(new Spieler(0, null), null, bewegung);	// FIXME: woher Spieler nehmen?
		//return regel.validiere(ze);
		return null;
	}
}
