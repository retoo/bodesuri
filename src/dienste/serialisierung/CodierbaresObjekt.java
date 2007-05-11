package dienste.serialisierung;

import java.io.ObjectStreamException;
import java.io.Serializable;

import pd.Spiel;

abstract public class CodierbaresObjekt implements Serializable {
	private String code;
	
	public CodierbaresObjekt(String code) {
		Spiel.aktuelles.getCodierer().speichere(code, this);
		this.code = code;
	}
	
	protected Object writeReplace() throws ObjectStreamException {
		return new CodiertesObjekt(code);
	}
}
