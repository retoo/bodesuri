package applikation.nachrichten;

import pd.zugsystem.ZugEingabe;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht die die Informationen zu einem Zug beinhaltet
 */
public class ZugInformation extends Nachricht {
	/**
	 * Gefahrener Zug
	 */
	public final ZugEingabe zug;

	/**
	 * @param zug
	 */
	public ZugInformation(ZugEingabe zug) {
		this.zug = zug;
	}
}
