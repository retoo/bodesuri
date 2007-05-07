package dienste.serialisierung;

import java.io.ObjectStreamException;

public class UnbekannterCodeException extends ObjectStreamException {
	private static final long serialVersionUID = 1L;
	
	public UnbekannterCodeException(String fehler) {
		super(fehler);
	}
}
