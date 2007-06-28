package pd.brett;

import pd.spieler.Spieler;

/**
 * Feld, das zu einem Spieler gehört, wie zum Beispiel das {@link BankFeld}.
 */
public abstract class SpielerFeld extends Feld {
	private Spieler spieler;

	/**
	 * @param nummer Feldnummer, siehe {@link Feld#Feld(int)}
	 * @param spieler Spieler, zu dem das Feld gehört
	 */
	public SpielerFeld(int nummer, Spieler spieler) {
		super(nummer);
		this.spieler = spieler;
    }

	/**
	 * Prüft, ob das Feld dem Spieler gehört.
	 *
	 * @param spieler
	 * @return true, wenn das Feld dem Spieler gehört
	 */
	public boolean istVon(Spieler spieler) {
		return spieler == this.spieler;
	}

	/**
	 * @return Spieler, dem das Feld gehört
	 */
	public Spieler getSpieler() {
		return spieler;
	}
}
