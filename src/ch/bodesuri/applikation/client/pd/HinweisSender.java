package ch.bodesuri.applikation.client.pd;

import java.util.Observable;

import ch.bodesuri.pd.spiel.spieler.SpielerFarbe;


/**
 * Sendet Hinweise über den Status des Spiel an den Benutzer (z.B. wer am Zug
 * ist, dass man eine Karte wählen soll...)
 */
public class HinweisSender extends Observable {
	/**
	 * Einen neuen Hinweis versenden.
	 * 
	 * @param hinweis
	 *            Der zu versendende Hinweis
	 * @param ersetzen
	 *            Ob der Hinweis durch den nächsten Hinweis überschrieben werden
	 *            soll.
	 * @param spielerFarbe 
	 */
	public void neuerHinweis(String hinweis, boolean ersetzen, SpielerFarbe spielerFarbe) {
		setChanged();
		notifyObservers(new Hinweis(hinweis, ersetzen, spielerFarbe));
	}

	/**
	 * Ein einzelner Hinweis wie er ans GUI versandt wird.
	 */
	public class Hinweis {
		/** Der Hinweistext **/
		public final String hinweis;
		/** Ob der Hinweis durch den nächsten Hinweis überschrieben werden soll. **/
		public final boolean ersetzen;
		/** Die Farbe des Spielers **/
		public final SpielerFarbe spielerFarbe;

		public Hinweis(String hinweis, boolean ersetzen, SpielerFarbe spielerFarbe) {
			this.hinweis = hinweis;
			this.ersetzen = ersetzen;
			this.spielerFarbe = spielerFarbe;
		}

		@Override
		public String toString() {
			return hinweis;
		}
	}
}
