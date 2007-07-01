package pd.serialisierung;

import dienste.serialisierung.CodierbaresObjekt;
import dienste.serialisierung.Codierer;
import dienste.serialisierung.CodiertesObjekt;

public class BodesuriCodierbaresObjekt extends CodierbaresObjekt {
	public BodesuriCodierbaresObjekt(String code) {
		super(code);
	}

	protected Codierer getCodierer() {
		Codierer codierer = CodiererThreads.getCodierer(Thread.currentThread());
		if (codierer == null) {
			throw new RuntimeException("Dem Thread ist kein Codierer zugewiesen.");
		} else {
			return codierer;
		}
	}

	protected CodiertesObjekt getCodiertesObjekt(String code) {
		return new BodesuriCodiertesObjekt(code);
	}

}
