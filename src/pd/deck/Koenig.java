/**
 * @(#) Koenig.java
 */

package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Koenig extends Karte{
	public Koenig(KartenFarbe farbe){
		super(farbe);
		/* TODO: ein Stein vom Startraum auf die Startposition */
		setRegel(new VorwaertsRegel(13));
	}
}
