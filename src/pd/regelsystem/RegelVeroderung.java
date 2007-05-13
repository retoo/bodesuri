package pd.regelsystem;

import java.util.Vector;

import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class RegelVeroderung extends Regel {
	
	Vector<Regel> regeln = new Vector<Regel>();
	
	public void fuegeHinzu(Regel regel) {
		regeln.add(regel);
	}

	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		RegelVerstoss regelVerstoss = null;
		for (Regel regel : regeln) {
			try {
				Zug resultat = regel.validiere(zugEingabe);
				return resultat;
			} catch (RegelVerstoss rv) {
				regelVerstoss = rv;
			}
		}
		if (regelVerstoss == null) {
			throw new RuntimeException("Sollte nicht passieren.");
		}
		throw regelVerstoss;
	}
}
