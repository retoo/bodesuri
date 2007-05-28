package pd.serialisierung;

import pd.Spiel;
import dienste.serialisierung.CodierbaresObjekt;
import dienste.serialisierung.Codierer;
import dienste.serialisierung.CodiertesObjekt;

public class BodesuriCodierbaresObjekt extends CodierbaresObjekt {
	private static final long serialVersionUID = 1L;

	public BodesuriCodierbaresObjekt(String code) {
	    super(code);
    }

	protected Codierer getCodierer() {
		return Spiel.aktuelles.getCodierer();
	}

	protected CodiertesObjekt getCodiertesObjekt(String code) {
		return new BodesuriCodiertesObjekt(code);
	}

}
