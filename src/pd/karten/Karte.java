package pd.karten;

import pd.regelsystem.Regel;
import pd.serialisierung.BodesuriCodierbaresObjekt;

public abstract class Karte extends BodesuriCodierbaresObjekt {
	private KartenFarbe farbe;
	private Regel regel;
	private String name;

	public Karte(String name, KartenFarbe farbe, int deck) {
		super("Karte " + farbe + " " + name + " " + deck);
		this.farbe = farbe;
		this.name = name;
	}

	public String toString() {
		return getKartenFarbe() + " " + getName();
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

	public String getName() {
		return name;
	}
	
	public String getRegelBeschreibung() {
		return getRegel().getBeschreibung();
	}
}
