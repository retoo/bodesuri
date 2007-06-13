package dienste.serialisierung;

import java.io.Serializable;

/**
 * Objekt, dem ein Code zugeordnet ist und codiert werden kann.
 * 
 * Das Objekt wird mithilfe des Codierers codiert, um das codierte Objekt
 * anstelle des ursprünglichen Objekts über das Netzwerk zu schicken.
 */
public abstract class CodierbaresObjekt implements Serializable {
	private String code;
	
	/**
	 * Grundkonstruktor für codierbare Objekte.
	 * 
	 * @param code Eindeutiger Code, der dem Objekt zugeordnet ist
	 */
	public CodierbaresObjekt(String code) {
		getCodierer().speichere(code, this);
		this.code = code;
	}
	
	/**
	 * Wird von ObjectOutputStream aufgerufen, um herauszufinden, was für ein
	 * Objekt anstelle dieses Objekts in den ObjectOutputStream geschrieben
	 * werden soll. Es soll ein CodiertesObjekt mit dem Code geschrieben werden.
	 */
	protected Object writeReplace() {
		return getCodiertesObjekt(code);
	}
	
	protected abstract Codierer getCodierer();
	
	protected abstract CodiertesObjekt getCodiertesObjekt(String code);
}
