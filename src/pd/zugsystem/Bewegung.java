package pd.zugsystem;

import java.io.Serializable;

import pd.brett.Feld;

/**
 * Hilfsklasse für eine Bewegung, die ein Start und ein Ziel hat.
 */
public class Bewegung implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Start der Bewegung. */
	public final Feld start;
	/** Ziel der Bewegung. */
	public final Feld ziel;

	public Bewegung(Feld start, Feld ziel) {
		this.start = start;
		this.ziel  = ziel;
	}

	/* FIXME: Robin bitter revieween */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "von " + start + " nach " + ziel;
	}
}
