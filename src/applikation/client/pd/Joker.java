package applikation.client.pd;

public class Joker extends Karte {
	private Karte konkreteKarte;

	public Joker(Karte karte) {
	    super(karte.getKarte());
	    konkreteKarte = karte;
    }

	public Karte getKonkreteKarte() {
    	return konkreteKarte;
    }
}
