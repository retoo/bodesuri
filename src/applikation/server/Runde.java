package applikation.server;

import java.util.List;
import java.util.Vector;

public class Runde {
	public Vector<Spieler> spielers;
	public final int nummer;

	public Runde(int nummer, List<Spieler> spielers) {
		this.nummer = nummer;
		this.spielers = new Vector<Spieler>();
		this.spielers.addAll(spielers);
		for (Spieler s : spielers) {
			s.hatGetauscht = false;
		}
	}

	public void entferneSpieler(Spieler aktuellerSpieler) {
	    this.spielers.remove(aktuellerSpieler);

    }

	public boolean isFertigGetauscht() {
		/* Pruefen ob irgend ein Spieler noch nicht getauscht hat */
		for (Spieler s : spielers) {
			if (!s.hatGetauscht) {
				return false;
			}
		}

		return true;
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
