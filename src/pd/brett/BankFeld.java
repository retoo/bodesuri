package pd.brett;

import pd.spieler.Spieler;

/**
 * Bankfeld, das erste Feld, auf das eine Figur kommt, wenn sie das Lager
 * verlässt.
 */
public class BankFeld extends SpielerFeld {
	private static final long serialVersionUID = 1L;
	
	private HimmelFeld himmelFeld;

	/**
	 * @see SpielerFeld#SpielerFeld(int, Spieler)
	 */
	public BankFeld(int nummer, Spieler spieler) {
		super(nummer, spieler);
	}
	
	/**
	 * Gibt das erste Feld des Himmels zurück.
	 * 
	 * @return Erstes Himmelfeld, das nach Bankfeld kommt
	 */
	public HimmelFeld getHimmel() {
		return himmelFeld;
	}
	
	/**
	 * Setzt das erste Himmelfeld.
	 * 
	 * @param himmelFeld Erstes Himmelfeld
	 */
	public void setHimmel(HimmelFeld himmelFeld) {
		this.himmelFeld = himmelFeld;
	}
}
