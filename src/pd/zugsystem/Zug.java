package pd.zugsystem;

import java.util.Vector;

/**
 * Zug, der aus mehreren Aktionen besteht und ausgeführt werden kann.
 */
public class Zug {
	private Vector<Aktion> aktionen;

	public Zug() {
		this.aktionen = new Vector<Aktion>();
	}

	/**
	 * Füge Aktion dem Zug hinzu.
	 * 
	 * @param aktion Aktion
	 */
	public void fuegeHinzu(Aktion aktion) {
		aktionen.add(aktion);
	}

	/**
	 * Führe den Zug aus. Es werden alle gespeicherten Aktionen der Reihe nach
	 * ausgeführt.
	 */
	public void ausfuehren() {
		for (Aktion aktion : aktionen) {
			aktion.ausfuehren();
		}
	}
}
