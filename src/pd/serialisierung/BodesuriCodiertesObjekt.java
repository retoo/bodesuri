package pd.serialisierung;

import dienste.serialisierung.Codierer;
import dienste.serialisierung.CodiertesObjekt;

/**
 * CodiertesObjekt für Bodesuri, welches den Codierer von
 * {@link CodiererThreads} holt.
 */
public class BodesuriCodiertesObjekt extends CodiertesObjekt {
	public BodesuriCodiertesObjekt(String code) {
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
}
