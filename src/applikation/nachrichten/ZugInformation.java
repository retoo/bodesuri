package applikation.nachrichten;

import pd.regelsystem.ZugEingabe;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht mit welcher der Client dem Server und der Server dem Client gemachte Züge
 * mitteilt.
 */
public class ZugInformation extends Nachricht {
	public final ZugEingabe zug;

	public ZugInformation(ZugEingabe zug) {
		this.zug = zug;
	}
}
