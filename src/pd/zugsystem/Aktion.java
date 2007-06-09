package pd.zugsystem;

import pd.brett.BankFeld;
import pd.brett.Feld;
import pd.brett.HimmelFeld;
import pd.brett.LagerFeld;

/**
 * Aktion, die eine Figur von einem Start- auf ein Zielfeld setzt.
 */
public class Aktion {
	protected Feld start;
	protected Feld ziel;
	
	public Aktion(Feld start, Feld ziel) {
		this.start = start;
		this.ziel  = ziel;
	}
	
	/**
	 * Führt Aktion aus, die Figur wird vom Start- aufs Zielfeld versetzt.
	 */
	public void ausfuehren() {
		if (ziel.istBesetzt()) {
			throw new RuntimeException("Ziel von Aktion sollte leer sein.");
		}
		start.versetzeFigurAuf(ziel);
		start.setGeschuetzt(false);
		if (start instanceof LagerFeld && ziel instanceof BankFeld) {
			ziel.setGeschuetzt(true);
		} else if (ziel instanceof HimmelFeld || ziel instanceof LagerFeld) {
			ziel.setGeschuetzt(true);
		}
	}
}
