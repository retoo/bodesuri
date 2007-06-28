package applikation.client.pd;

/**
 * Spezieller Typ einer Applikations-Karte. Beinhaltet selbst noch eine Karte,
 * für die der Joker steht.
 */
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
