package pd.karten;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Deck {
	private static Map<KartenFarbe, List<Karte>> kartenFuerFarbe = 
		new HashMap<KartenFarbe, List<Karte>>();

	public static List<Karte> erstelleKarten() {
		List<Karte> karten = new Vector<Karte>();

		for (KartenFarbe farbe : KartenFarbe.values()) {
			kartenFuerFarbe.put(farbe, erstelleKartenFuerFarbe(farbe));
			karten.addAll(kartenFuerFarbe.get(farbe));
		}
		karten.add(new Joker(KartenFarbe.Herz));
		karten.add(new Joker(KartenFarbe.Karo));
		karten.add(new Joker(KartenFarbe.Kreuz));

		return karten;
	}

	public static List<Karte> getKartenFuerFarbe(KartenFarbe farbe) {
		return kartenFuerFarbe.get(farbe);
	}

	private static List<Karte> erstelleKartenFuerFarbe(KartenFarbe farbe) {
		List<Karte> karten = new Vector<Karte>();
		karten.add(new Ass(farbe));
		karten.add(new Koenig(farbe));
		karten.add(new Dame(farbe));
		karten.add(new Bube(farbe));
		karten.add(new Zehn(farbe));
		karten.add(new Neun(farbe));
		karten.add(new Acht(farbe));
		karten.add(new Sieben(farbe));
		karten.add(new Sechs(farbe));
		karten.add(new Fuenf(farbe));
		karten.add(new Vier(farbe));
		karten.add(new Drei(farbe));
		karten.add(new Zwei(farbe));
		return karten;
	}
}
