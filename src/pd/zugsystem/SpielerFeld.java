package pd.zugsystem;

import pd.spielerverwaltung.Spieler;

public class SpielerFeld extends Feld {
	private Spieler spieler;
	
	public Spieler getSpieler() {
		return spieler;
	}

	public void setSpieler(Spieler spieler) {
    	this.spieler = spieler;
    }
}
