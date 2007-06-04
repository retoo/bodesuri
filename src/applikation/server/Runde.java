package applikation.server;

import java.util.Vector;

public class Runde {
	public Vector<Spieler> spielers;
	public final int nummer;

	public Runde(int nummer) {
		this.nummer = nummer;
		spielers = new Vector<Spieler>();
	}

	public void entferneSpieler(Spieler aktuellerSpieler) {
	    this.spielers.remove(aktuellerSpieler);

    }
	
	/*
	 * Anzahl Karten mit aufsteigender Rundennummer: 6, 5, 4, 3, 2, 6, 5, ...
	 */
	public int getAnzahlKartenProSpieler() {
		return 6 - (nummer % 5);
	}

	public boolean isFertig() {
		return spielers.isEmpty();
	}
}
