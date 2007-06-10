package pd.brett;

import pd.spieler.Spieler;

/**
 * Himmelfeld; da wo ein Spieler am Schluss seine Figuren hinbringen sollte.
 *
 * Das erste Himmelfeld kann über {@link BankFeld#getHimmel()} erreicht werden.
 */
public class HimmelFeld extends SpielerFeld {
	/**
	 * @see SpielerFeld#SpielerFeld(int, Spieler)
	 */
	public HimmelFeld(int nummer, Spieler spieler) {
		super(nummer, spieler);
	}
	
	public boolean istHimmel() {
		return true;
	}
}
