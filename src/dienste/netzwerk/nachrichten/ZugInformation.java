package dienste.netzwerk.nachrichten;


public class ZugInformation extends Nachricht {
    private static final long serialVersionUID = 1L;
	public Object zug;
	
	public ZugInformation(Object zug) {
		this.zug = zug;
	}
}
