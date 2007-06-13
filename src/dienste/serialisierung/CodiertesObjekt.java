package dienste.serialisierung;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Objekt, das einen Code enthält und serialisiert werden kann.
 *
 * Dieses Objekt wird serialisiert und über das Netzwerk geschickt. Am anderen
 * Ende wird das Objekt deserialisiert und der ObjectInputStream ruft
 * readResolve() auf.
 */
public abstract class CodiertesObjekt implements Serializable {
	private String code;

	/**
	 * Erstellt ein CodiertesObjekt.
	 *
	 * @param code
	 *            Code, der dem codierten Objekt zugeordnet ist
	 */
	public CodiertesObjekt(String code) {
		this.code = code;
	}

	/*
	 * Wird von ObjectInputStream aufgerufen, um herauszufinden, welches Objekt
	 * diesem codierten Objekt zugeordnet ist. Dies wird über den Codierer des
	 * aktuellen Spiels herausgefunden.
	 *
	 * Wenn der Codierer den Code nicht kennt, ihm also kein Objekt zugeordnet
	 * worden ist, wird eine UnbekannterCodeException geworfen.
	 */
	protected Object readResolve() throws ObjectStreamException {
		/* Spiel.aktuelles ist leider global. */
		Object obj = getCodierer().get(code);
		if (obj == null) {
			throw new UnbekannterCodeException(code);
		}
		return obj;
	}

	protected abstract CodiererInterface getCodierer();
}
