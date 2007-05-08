package dienste.netzwerk.nachrichten;

import pd.zugsystem.Zug;


public class ZugInformation extends Nachricht {
    private static final long serialVersionUID = 1L;
	public Zug zug;
	
	public ZugInformation(Zug zug) {
		this.zug = zug;
	}
}
