package pd.brett;

import pd.spielerverwaltung.Spieler;

public abstract class SpielerFeld extends Feld {
	private Spieler spieler;

	public SpielerFeld(int nummer, Spieler spieler) {
		super(nummer);
		this.spieler = spieler;
    }

	public Spieler getSpieler() {
		return spieler;
	}
}
