package applikation.server.nachrichten;

import pd.zugsystem.ZugEingabe;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht die die Informationen zu einem Zug beinhaltet
 */
public class ZugInformation extends Nachricht {
    private static final long serialVersionUID = 1L;
	
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
