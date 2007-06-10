package pd.zugsystem;

import pd.brett.Feld;

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
		if (start.istLager() && ziel.istBank()) {
			ziel.setGeschuetzt(true);
		} else if (ziel.istHimmel() || ziel.istLager()) {
			ziel.setGeschuetzt(true);
		}
	}
}
