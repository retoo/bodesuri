package applikation.server.pd;

import java.util.List;
import java.util.Vector;

public class Runde {
	public Vector<Spieler> spielers;
	public final int nummer;
	private int aktuellerSpieler;

	public Runde(int nummer, List<Spieler> spielers) {
		this.nummer = nummer;
		this.spielers = new Vector<Spieler>(spielers);

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

	/**
	 * Markiert den nächsten Spieler als 'aktuellerSpieler'. Methode darf erst
	 * aufgerufen ewrden nachdem mindestens ein Spieler hinzugefügt wurde.
	 */
	public void rotiereSpieler() {
		int anzahlSpieler = spielers.size();

		if (anzahlSpieler > 0)
			aktuellerSpieler = (aktuellerSpieler + 1) % anzahlSpieler;
		else
			throw new RuntimeException("Kann nicht rotieren, es gibt ja noch gar keine Spieler");
	}

	/**
	 * Liefert aktuellen Spieler
	 *
	 * @return der zurzeit spielende Spieler
	 */
	public Spieler getAktuellerSpieler() {
		return spielers.get(aktuellerSpieler);
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
