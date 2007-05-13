package pd.regelsystem;

import java.util.Vector;

import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class RegelVeroderung extends Regel {
	
	Vector<Regel> regeln = new Vector<Regel>();
	
	public void fuegeHinzu(Regel regel) {
		regeln.add(regel);
	}

	public Zug validiere(ZugEingabe zugEingabe) {
		for (Regel regel : regeln) {
			Zug resultat = regel.validiere(zugEingabe);
			if (resultat != null) {
				return resultat;
			}
		}
		return null;
	}
}
