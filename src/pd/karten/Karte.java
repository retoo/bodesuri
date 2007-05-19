package pd.karten;

import dienste.serialisierung.CodierbaresObjekt;
import pd.regelsystem.Regel;

public class Karte extends CodierbaresObjekt {
	private static final long serialVersionUID = 1L;
	
	private KartenFarbe farbe;
	private Regel regel;

	public Karte(String name, KartenFarbe farbe, int deck) {
		super("Karte " + farbe + " " + name + " " + deck);
		this.farbe = farbe;
	}
	
	public String toString() {
		return getKartenFarbe() + " " +
               getClass().getSimpleName();
	}

	public void setKartenFarbe(KartenFarbe kartenFarbe) {
		farbe = kartenFarbe;
	}

	public KartenFarbe getKartenFarbe() {
		return farbe;
	}

	public Regel getRegel() {
		return regel;
	}

	protected void setRegel(Regel regel) {
		this.regel = regel;
	}
}
