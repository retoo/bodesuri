package pd.karten;

public class Joker extends Karte {
	public Joker(KartenFarbe farbe) {
		super("Joker", farbe);
	}

	public String toString() {
		return "Joker";
	}

	public String getRegelBeschreibung() {
		return "Als beliebige Karte einsetzbar";
	}
}
