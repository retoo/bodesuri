package pd.serialisierung;

import pd.Spiel;
import dienste.serialisierung.Codierer;
import dienste.serialisierung.CodiertesObjekt;

public class BodesuriCodiertesObjekt extends CodiertesObjekt {
	private static final long serialVersionUID = 1L;

	public BodesuriCodiertesObjekt(String code) {
	    super(code);
    }

	protected Codierer getCodierer() {
		return Spiel.aktuelles.getCodierer();
	}
}
