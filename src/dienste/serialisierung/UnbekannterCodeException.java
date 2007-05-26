package dienste.serialisierung;

import java.io.ObjectStreamException;

/**
 * Exception, die vom {@link Codierer} geworfen wird, wenn einem empfangenen
 * Code kein Objekt zugeordnet ist.
 */
public class UnbekannterCodeException extends ObjectStreamException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Erstellt eine UnbekannterCodeException.
	 * 
	 * @param code Code, der unbekannt ist
	 */
	public UnbekannterCodeException(String code) {
		super("'" + code + "' unbekannt");
	}
}
