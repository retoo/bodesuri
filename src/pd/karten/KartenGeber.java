package pd.karten;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class KartenGeber {
	/*
	 * 13 * 4 Farben + 2 Joker = 54 Karten = ein Deck
	 * 54 Karten * 2 = 108 Karten
	 */
	private static final int ANZAHL_KARTEN = 108;
	private Stack<Karte> kartenStapel = new Stack<Karte>();

	public KartenGeber() {
		mischen();
	}

	/**
	 * Initialisert Kartenstapel und Karten. Eine Karten wird über die
	 * Farbe erzeugt. Die Farbe ist nachher nur noch als Attribut in den
	 * Karten vorhanden. Das Deck wird gemischelt.
	 */
	private void mischen() {
		for (int i=0; i < 2; ++i) {
			kartenStapel.addAll(new Deck(i).getKarten());
		}
		
		// Mischen
		for (int i = 0; i < ANZAHL_KARTEN; ++i) {
			int a = (int)(Math.random() * ANZAHL_KARTEN);
			int b = (int)(Math.random() * ANZAHL_KARTEN);
			wechseln(a, b);
		}
	}
	
	/**
	 * Wechselt zwei Karten innerhalb des Decks, sofern die Indizes innerhalb des
	 * gültigen Bereichs liegen.
	 * 
	 * @param von
	 * @param nach
	 */
	private void wechseln(int von, int nach) {
		if (von  < 0 || von  >= ANZAHL_KARTEN ||
		    nach < 0 || nach >= ANZAHL_KARTEN)
			return;
		
		Karte tmp = kartenStapel.get(von);
		kartenStapel.setElementAt(kartenStapel.get(nach), von);
		kartenStapel.setElementAt(tmp, nach);
	}

	public Karte getKarte() {
		Karte obersteKarte;
		try {
			obersteKarte = kartenStapel.pop();
		} catch (EmptyStackException ex) {
			mischen();
			obersteKarte = getKarte();		// rekursiver Aufruf
		}
		return obersteKarte;
	}

	public List<Karte> getKarten(int anzahl) {
	    Vector<Karte> karten = new Vector<Karte>();
	    
	    for (int i = 0; i < anzahl; i++) {
	    	karten.add(getKarte());
	    }
	    
	    return karten;
    }
}
