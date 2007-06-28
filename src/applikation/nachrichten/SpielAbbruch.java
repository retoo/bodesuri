package applikation.nachrichten;

import dienste.netzwerk.Nachricht;

public class SpielAbbruch extends Nachricht {
	public final String nachricht;

	public SpielAbbruch(String nachricht) {
		this.nachricht = nachricht;
	}
}
