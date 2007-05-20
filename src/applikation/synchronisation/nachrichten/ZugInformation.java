package applikation.synchronisation.nachrichten;

import pd.zugsystem.ZugEingabe;
import dienste.netzwerk.Nachricht;

public class ZugInformation extends Nachricht {
    private static final long serialVersionUID = 1L;
	
    final public ZugEingabe zug;
	
	public ZugInformation(ZugEingabe zug) {
		this.zug = zug;
	}
}
