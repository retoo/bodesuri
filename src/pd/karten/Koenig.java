package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Koenig extends Karte{
	private static final long serialVersionUID = 1L;

	public Koenig(KartenFarbe farbe){
		super(farbe);
		/* TODO: ein Stein vom Startraum auf die Startposition */
		setRegel(new VorwaertsRegel(13));
	}
}
