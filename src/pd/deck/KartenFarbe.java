package pd.deck;

import java.util.Vector;

public abstract class KartenFarbe {	
	private Vector<Karte> karten = new Vector<Karte>();
	
	public KartenFarbe() {
		karten.add(new Zwei(this));
		karten.add(new Drei(this));
		karten.add(new Vier(this));
		karten.add(new Fuenf(this));
		karten.add(new Sechs(this));
		karten.add(new Sieben(this));
		karten.add(new Acht(this));
		karten.add(new Neun(this));
		karten.add(new Zehn(this));
		karten.add(new Bube(this));
		karten.add(new Dame(this));
		karten.add(new Koenig(this));
		karten.add(new Ass(this));
		karten.add(new Joker(this));
	}
	
	public Vector<Karte> getKarten() {
		return karten;
	}
}
