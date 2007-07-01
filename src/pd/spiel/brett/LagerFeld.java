package pd.spiel.brett;

import pd.spiel.spieler.Spieler;

/**
 * Lagerfeld, auf dem die Figur des Spieler am Anfang steht.
 *
 * Das nächste Feld ist das {@link BankFeld}.
 */
public class LagerFeld extends SpielerFeld {
	/**
	 * @see SpielerFeld#SpielerFeld(int, Spieler)
	 */
	public LagerFeld(int nummer, Spieler spieler) {
		super(nummer, spieler);
	}

	public boolean istLager() {
		return true;
	}
}
