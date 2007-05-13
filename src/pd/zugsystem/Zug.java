package pd.zugsystem;

import java.util.Vector;

public class Zug {
	private Vector<Aktion> aktionen;
	
	public Zug() {
		this.aktionen = new Vector<Aktion>();
	}
	
	public void fuegeHinzu(Aktion aktion) {
		aktionen.add(aktion);
	}
	
	public void ausfuehren() {
		for (Aktion aktion : aktionen) {
			aktion.ausfuehren();
		}
	}
}
