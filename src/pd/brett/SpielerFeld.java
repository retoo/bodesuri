package pd.brett;

import pd.spieler.Spieler;

/**
 * Feld, das zu einem Spieler gehört, wie zum Beispiel das {@link BankFeld}.
 */
public abstract class SpielerFeld extends Feld {
	private Spieler spieler;

	/**
	 * @param nummer Feldnummer, siehe {@link Feld#Feld}
	 * @param spieler Spieler, zu dem das Feld gehört
	 */
	public SpielerFeld(int nummer, Spieler spieler) {
		super(nummer);
		this.spieler = spieler;
    }

	public Spieler getSpieler() {
		return spieler;
	}
}
