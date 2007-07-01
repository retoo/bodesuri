package pd.regelsystem.karten;

import pd.regelsystem.Regel;
import pd.serialisierung.BodesuriCodierbaresObjekt;

/**
 * Karte, mit welcher im Spiel gespielt wird.
 *
 * Eine Karte hat einen Namen, eine {@link KartenFarbe} und eine {@link Regel}.
 */
public abstract class Karte extends BodesuriCodierbaresObjekt {
	private KartenFarbe farbe;
	private Regel regel;
	private String name;

	/**
	 * Erstellt eine Karte.
	 *
	 * @param name Name der Karte, wie z. B. "Ass"
	 * @param farbe Farbe der Karte, wie z. B. KartenFarbe.Herz
	 */
	public Karte(String name, KartenFarbe farbe) {
		super("Karte " + farbe + " " + name);
		this.farbe = farbe;
		this.name = name;
	}

	public String toString() {
		return getKartenFarbe() + " " + getName();
	}

	public String getName() {
		return name;
	}

	public KartenFarbe getKartenFarbe() {
		return farbe;
	}

	public String getRegelBeschreibung() {
		return getRegel().getBeschreibung();
	}

	public Regel getRegel() {
		return regel;
	}

	protected void setRegel(Regel regel) {
		this.regel = regel;
	}
}
