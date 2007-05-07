/**
 * @(#) Ass.java
 */

package pd.deck;

import pd.regelsystem.RegelVeroderung;
import pd.regelsystem.VorwaertsRegel;

public class Ass extends Karte{
	private static final long serialVersionUID = 1L;

	public Ass(KartenFarbe farbe) {
		super(farbe);
		RegelVeroderung regelVeroderung = new RegelVeroderung();
		regelVeroderung.fuegeHinzu(new VorwaertsRegel(1));
		regelVeroderung.fuegeHinzu(new VorwaertsRegel(11));
		/* TODO: ein Stein vom Startraum auf die Startposition */
		setRegel(regelVeroderung);
		/* TODO: ein weiteres Attribut hinzufügen oder setWert(string)*/
		setWert(1);
	}
}
