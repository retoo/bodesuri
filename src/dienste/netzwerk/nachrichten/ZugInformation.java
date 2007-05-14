package dienste.netzwerk.nachrichten;

import pd.zugsystem.ZugEingabe;


public class ZugInformation extends Nachricht {
    private static final long serialVersionUID = 1L;
	public ZugEingabe zug;
	
	public ZugInformation(ZugEingabe zug) {
		this.zug = zug;
	}
}
