package pd.serialisierung;

import pd.Spiel;
import pd.SpielThreads;
import dienste.serialisierung.CodierbaresObjekt;
import dienste.serialisierung.Codierer;
import dienste.serialisierung.CodiertesObjekt;

public class BodesuriCodierbaresObjekt extends CodierbaresObjekt {
	public BodesuriCodierbaresObjekt(String code) {
		super(code);
	}

	protected Codierer getCodierer() {
		Spiel spiel = SpielThreads.getSpiel(Thread.currentThread());
		if (spiel == null) {
			throw new RuntimeException("Dem Thread ist kein Spiel zugewiesen.");
		} else {
			return spiel.getCodierer();
		}
	}

	protected CodiertesObjekt getCodiertesObjekt(String code) {
		return new BodesuriCodiertesObjekt(code);
	}

}
