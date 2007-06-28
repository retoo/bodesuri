package applikation.nachrichten;

import java.io.Serializable;

/**
 * Ein Spieler wie er über die Leitung geht. Aus diesem SpielerInfo werden auf
 * den Clients anhand des Spielernamens die Spieler-Objekte erstellt.
 */
public class SpielerInfo implements Serializable {
	public final String spielername;

	public SpielerInfo(String spielername) {
		this.spielername = spielername;
	}
}
