package pd.zugsystem;

import pd.spielerverwaltung.Spieler;

public abstract class SpielerFeld extends Feld {
	private Spieler spieler;

	public SpielerFeld(Spieler spieler) {
		this.spieler = spieler;
    }

	public Spieler getSpieler() {
		return spieler;
	}
}
