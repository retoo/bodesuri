package pd.regelsystem;

import java.util.Vector;

import pd.zugsystem.ZugEingabe;

public class RegelVeroderung extends Regel {
	
	Vector<Regel> regeln = new Vector<Regel>();
	
	public void fuegeHinzu(Regel regel) {
		regeln.add(regel);
	}

	public Regel validiere(ZugEingabe zugEingabe) {
		for (Regel regel : regeln) {
			Regel resultat = regel.validiere(zugEingabe);
			if (resultat != null) {
				return resultat;
			}
		}
		return null;
	}

	/* TODO: Zweites Interface Ausfuehrer oder so machen. */
	/* deprecated keyword hinzugefügt (-rschuett) */
	@Deprecated
	public void ausfuehren(ZugEingabe zug) {
		throw new Error("Sollte gar nie aufgerufen werden.");
	}
}
