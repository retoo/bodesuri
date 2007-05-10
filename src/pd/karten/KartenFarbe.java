package pd.karten;

import java.util.Vector;

public abstract class KartenFarbe {	
	private Vector<Karte> karten = new Vector<Karte>();
	
	public KartenFarbe() { }
	
	public Vector<Karte> createKarten() {
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
		return karten;
	}
}
