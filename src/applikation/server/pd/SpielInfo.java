package applikation.server.pd;

import java.io.Serializable;
import java.util.Vector;

public class SpielInfo implements Serializable {
	public final Vector<SpielerInfo> spielers;

	public SpielInfo(Vector<SpielerInfo> spielers) {
		this.spielers = spielers;
	}
}
