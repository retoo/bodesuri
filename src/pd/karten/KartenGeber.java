package pd.karten;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

public class KartenGeber {
	/*
	 * 13 * 4 Farben + 3 Joker = 55 Karten = ein Deck
	 * 55 Karten * 2 = 110 Karten
	 */
	private Stack<Karte> kartenStapel = new Stack<Karte>();
	private List<Karte> deck;

	public KartenGeber() {
		deck = Deck.erstelleKarten();
		mischen();
	}

	/**
	 * Initialisert Kartenstapel und Karten. Eine Karten wird über die
	 * Farbe erzeugt. Die Farbe ist nachher nur noch als Attribut in den
	 * Karten vorhanden. Das Deck wird gemischelt.
	 */
	private void mischen() {
		for (int i = 0; i < 2; ++i) {
			kartenStapel.addAll(deck);
		}
		
		int anzahl = kartenStapel.size();
		for (int i = 0; i < anzahl - 1; ++i) {
			int k = i + (int)(Math.random() * (anzahl - i));
			wechseln(i, k);
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
		Karte tmp = kartenStapel.get(von);
		kartenStapel.setElementAt(kartenStapel.get(nach), von);
		kartenStapel.setElementAt(tmp, nach);
	}

	public Karte getKarte() {
		Karte obersteKarte;
		if ( kartenStapel.size() == 0 ) {
			mischen();
		}
		obersteKarte = kartenStapel.pop();
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
