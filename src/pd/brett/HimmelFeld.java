package pd.brett;

import pd.spieler.Spieler;

/**
 * Himmelfeld; da wo ein Spieler am Schluss seine Figuren hinbringen sollte.
 * 
 * Das erste Himmelfeld kann über {@link BankFeld#getHimmel()} erreicht werden.
 */
public class HimmelFeld extends SpielerFeld {
	private static final long serialVersionUID = 1L;

	/**
	 * @see SpielerFeld#SpielerFeld(int, Spieler)
	 */
	public HimmelFeld(int nummer, Spieler spieler) {
		super(nummer, spieler);
	}
}
