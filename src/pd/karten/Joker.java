package pd.karten;

public class Joker extends Karte {
	public Joker(KartenFarbe farbe, int deck) {
		super("Joker", farbe, deck);
	}
	
	public String getRegelBeschreibung() {
		return "Als beliebige Karte einsetzbar";
	}
}
