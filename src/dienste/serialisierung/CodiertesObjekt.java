package dienste.serialisierung;

import java.io.ObjectStreamException;
import java.io.Serializable;

import pd.zugsystem.Spiel;

public class CodiertesObjekt implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	public CodiertesObjekt(String code) {
		this.code = code;
	}
	
	private Object readResolve() throws ObjectStreamException {
		/* Spiel.aktuelles ist leider global. */
		Object obj = Spiel.aktuelles.getCodierer().get(code);
		if (obj == null) {
			throw new UnbekannterCodeException("'" + code + "' unbekannt");
		}
		return obj;
	}
}
