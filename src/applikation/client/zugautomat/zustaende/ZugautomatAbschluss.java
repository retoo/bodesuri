package applikation.client.zugautomat.zustaende;

import applikation.client.pd.SteuerungsZustand;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Räumt den Zugautomaten auf. Alle Bewegungen werden zurückgesetzt, die
 * Kartenauswahl deaktiviert, die SteuerungsButtons ausgeblendet und in
 * {@link ZugautomatEndZustand} übergegangen.
 */
public class ZugautomatAbschluss extends ClientZugZustand implements
        PassiverZustand {

	public Class<? extends Zustand> handle() {
		bewegungenZuruecksetzen();
		spielDaten.spiel.spielerIch.getKarten().setAktiv(false);
		spielDaten.spiel.setSteuerungsZustand(SteuerungsZustand.NICHTS);

		return ZugautomatEndZustand.class;
	}
}
