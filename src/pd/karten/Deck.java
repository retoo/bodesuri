package pd.karten;

import java.util.List;
import java.util.Vector;

public class Deck {
	public static List<Karte> erstelleKarten() {
		List<Karte> karten = new Vector<Karte>();

		for (KartenFarbe farbe : KartenFarbe.values()) {
			karten.add(new Zwei(farbe));
			karten.add(new Drei(farbe));
			karten.add(new Vier(farbe));
			karten.add(new Fuenf(farbe));
			karten.add(new Sechs(farbe));
			karten.add(new Sieben(farbe));
			karten.add(new Acht(farbe));
			karten.add(new Neun(farbe));
			karten.add(new Zehn(farbe));
			karten.add(new Bube(farbe));
			karten.add(new Dame(farbe));
			karten.add(new Koenig(farbe));
			karten.add(new Ass(farbe));
		}
		karten.add(new Joker(KartenFarbe.Herz));
		karten.add(new Joker(KartenFarbe.Karo));
		karten.add(new Joker(KartenFarbe.Kreuz));

		return karten;
	}
}
