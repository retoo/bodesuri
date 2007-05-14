package pd.zugsystem;

import pd.brett.Feld;

public class Aktion {
	private Feld start;
	private Feld ziel;
	
	public Aktion(Feld start, Feld ziel) {
		this.start = start;
		this.ziel  = ziel;
	}
	
	public void ausfuehren() {
		if (ziel.istBesetzt()) {
			// TODO: In Zuegen auch Aktion fuer Heimschicken generieren.
			// throw new RuntimeException("Ziel von Aktion sollte leer sein.");
		}
		start.versetzeFigurAuf(ziel);
	}
}
