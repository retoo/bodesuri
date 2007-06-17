package applikation.nachrichten;

import pd.spieler.Spieler;
import dienste.netzwerk.Nachricht;

public class AktuellerSpielerInformation extends Nachricht {
	public final Spieler spieler;

	public AktuellerSpielerInformation(Spieler spieler) {
		this.spieler = spieler;
	}
}
