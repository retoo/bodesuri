package pd.zugsystem;

import java.io.Serializable;

import pd.brett.Feld;

/**
 * Hilfsklasse für eine Bewegung, die ein Start und ein Ziel hat.
 */
public class Bewegung implements Serializable {
	/** Start der Bewegung. */
	public final Feld start;
	/** Ziel der Bewegung. */
	public final Feld ziel;

	public Bewegung(Feld start, Feld ziel) {
		this.start = start;
		this.ziel = ziel;
	}

	public String toString() {
		return "von " + start + " nach " + ziel;
	}
}
