package dienste.netzwerk;

import java.io.Serializable;

/**
 * Repräsentiert eine über das Netz übertragbare Nachricht.
 *
 */
public abstract class Nachricht implements Serializable {
	public String toString() {
		return this.getClass().getSimpleName();
	}
}