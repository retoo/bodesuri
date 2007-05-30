package applikation.server;

import java.util.Vector;

public class Runde {
	public Vector<Spieler> spielers;

	public Runde() {
		spielers = new Vector<Spieler>();
	}

	public void entferneSpieler(Spieler aktuellerSpieler) {
	    this.spielers.remove(aktuellerSpieler);

    }

	public boolean isFertig() {
		return spielers.isEmpty();
	}
}
