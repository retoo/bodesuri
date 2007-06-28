package applikation.nachrichten;

import java.io.Serializable;
import java.util.Vector;

/**
 * Ein Vektor von {@link SpielerInfo}s.
 */
public class SpielInfo implements Serializable {
	public final Vector<SpielerInfo> spielers;

	public SpielInfo(Vector<SpielerInfo> spielers) {
		this.spielers = spielers;
	}
}
