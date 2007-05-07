package dienste.netzwerk;

import dienste.netzwerk.nachrichtentypen.Nachricht;

public class Brief {
	public EndPunkt absender;
	public Nachricht nachricht;

	public Brief(EndPunkt absender, Nachricht nachricht) {
		this.absender = absender;
		this.nachricht = nachricht;
	}
	

	public String toString() {
		return "(" + absender + ": " + nachricht + ")";
	}
}
