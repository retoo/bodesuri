package pd.karten;

import java.util.List;
import java.util.Vector;

public class Deck {
	private List<Karte> karten;
	
	/* TODO: Statische Methode machen, wenn Deck sonst nicht gebraucht. */
	public Deck(int nummer) {
		karten = new Vector<Karte>();
		for (KartenFarbe farbe : KartenFarbe.values()) {
			karten.add(new Zwei(farbe, nummer));
			karten.add(new Drei(farbe, nummer));
			karten.add(new Vier(farbe, nummer));
			karten.add(new Fuenf(farbe, nummer));
			karten.add(new Sechs(farbe, nummer));
			karten.add(new Sieben(farbe, nummer));
			karten.add(new Acht(farbe, nummer));
			karten.add(new Neun(farbe, nummer));
			karten.add(new Zehn(farbe, nummer));
			karten.add(new Bube(farbe, nummer));
			karten.add(new Dame(farbe, nummer));
			karten.add(new Koenig(farbe, nummer));
			karten.add(new Ass(farbe, nummer));
		}
		karten.add(new Joker(KartenFarbe.HERZ, nummer));
		karten.add(new Joker(KartenFarbe.KARO, nummer));
		karten.add(new Joker(KartenFarbe.KREUZ, nummer));
	}
	
	public List<Karte> getKarten() {
		return karten;
	}
}
