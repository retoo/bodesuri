package pd.karten;

import pd.regelsystem.VorwaertsRegel;

public class Koenig extends Karte{
	private static final long serialVersionUID = 1L;

	public Koenig(KartenFarbe farbe, int deck){
		super("Koenig", farbe, deck);
		/* TODO: ein Stein vom Startraum auf die Startposition */
		setRegel(new VorwaertsRegel(13));
	}
}
