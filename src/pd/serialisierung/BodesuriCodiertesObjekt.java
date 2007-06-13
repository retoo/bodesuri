package pd.serialisierung;

import pd.Spiel;
import pd.SpielThreads;
import dienste.serialisierung.CodiererInterface;
import dienste.serialisierung.CodiertesObjekt;

public class BodesuriCodiertesObjekt extends CodiertesObjekt {
	public BodesuriCodiertesObjekt(String code) {
		super(code);
	}

	protected CodiererInterface getCodierer() {
		Spiel spiel = SpielThreads.getSpiel(Thread.currentThread());
		if (spiel == null) {
			throw new RuntimeException("Dem Thread ist kein Spiel zugewiesen.");
		} else {
			return spiel.getCodierer();
		}
	}
}
