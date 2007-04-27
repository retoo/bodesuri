package pd.regelsystem;

import java.util.Vector;

import pd.zugsystem.Zug;

public class RegelVeroderung extends Regel {
	
	Vector<Regel> regeln = new Vector<Regel>();
	
	public void fuegeRegelHinzu(Regel regel) {
		regeln.add(regel);
	}

	public Regel validiere(Zug zug) {
		for (Regel regel : regeln) {
			Regel resultat = regel.validiere(zug);
			if (resultat != null) {
				return resultat;
			}
		}
		return null;
	}

	/* TODO: Zweites Interface Ausfuehrer oder so machen. */
	public void ausfuehren(Zug zug) {
		throw new Error("Sollte gar nie aufgerufen werden.");
	}
}
