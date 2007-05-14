package pd.karten;

import java.util.List;
import java.util.Vector;

public class Deck {
	public static List<Karte> erstelleKarten(int deck) {
		List<Karte> karten = new Vector<Karte>();
		
		for (KartenFarbe farbe : KartenFarbe.values()) {
			karten.add(new Zwei(farbe, deck));
			karten.add(new Drei(farbe, deck));
			karten.add(new Vier(farbe, deck));
			karten.add(new Fuenf(farbe, deck));
			karten.add(new Sechs(farbe, deck));
			//karten.add(new Sieben(farbe, deck));
			karten.add(new Acht(farbe, deck));
			karten.add(new Neun(farbe, deck));
			karten.add(new Zehn(farbe, deck));
			/*karten.add(new Bube(farbe, deck));
			karten.add(new Dame(farbe, deck));
			karten.add(new Koenig(farbe, deck));
			karten.add(new Ass(farbe, deck));*/
		}
		/*karten.add(new Joker(KartenFarbe.Herz, deck));
		karten.add(new Joker(KartenFarbe.Karo, deck));
		karten.add(new Joker(KartenFarbe.Kreuz, deck));*/
		
		return karten;
	}
}
