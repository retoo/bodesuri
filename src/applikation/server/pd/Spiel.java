package applikation.server.pd;

import java.util.IdentityHashMap;
import java.util.Vector;

import pd.SpielThreads;
import pd.karten.KartenGeber;
import applikation.geteiltes.SpielInfo;
import applikation.geteiltes.SpielerInfo;
import applikation.nachrichten.ChatNachricht;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungWegException;
import dienste.netzwerk.server.Server;
import dienste.serialisierung.SerialisierungsKontext;

/**
 * Repräsentiert eine Gruppe von Spieler die in einem Spiel auf dem Server
 * mitspielen.
 */
public class Spiel implements SerialisierungsKontext {
	private IdentityHashMap<EndPunktInterface, Spieler> endpunktZuSpieler;
	private Vector<Spieler> spielers = new Vector<Spieler>();
	private Vector<Partnerschaft> partnerschaften;
	private int anzahlSpieler;
	private pd.Spiel spiel;

	public Server server;
	public EventQueue queue;
	public Runde runde;

	public void registriere(Thread thread) {
		SpielThreads.registriere(thread, this.spiel);
	}

	/**
	 * Erstellt eine neue Spielerschaft.
	 *
	 * @param anzSpieler
	 *            Anzahl der Speielr
	 */
	public Spiel(int anzSpieler) {
		this.spiel = new pd.Spiel();
		this.anzahlSpieler = anzSpieler;
		this.spielers = new Vector<Spieler>();
		this.partnerschaften = new Vector<Partnerschaft>();
		this.endpunktZuSpieler = new IdentityHashMap<EndPunktInterface, Spieler>();
	}

	public void partnerschaftenBilden() {
		if (anzahlSpieler != spielers.size())
			throw new RuntimeException("partnerschaftBilden zu früh aufgerufen");

		if (anzahlSpieler == 1) {
			/* Entwickler Betrieb */
			Spieler einzelgaenger = spielers.get(0);

			einzelgaenger.partner = einzelgaenger;
			partnerschaften.add( new Partnerschaft(einzelgaenger, einzelgaenger) );

		} else if (anzahlSpieler == 2) {
			spielers.get(0).partner = spielers.get(1);
			spielers.get(1).partner = spielers.get(0);

			partnerschaften.add( new Partnerschaft(spielers.get(0), spielers.get(1)) );
		} else if (anzahlSpieler == 4) {
			/* Normaler Betrieb */
			spielers.get(0).partner = spielers.get(2);
			spielers.get(2).partner = spielers.get(0);
			partnerschaften.add( new Partnerschaft(spielers.get(0), spielers.get(2)) );

			spielers.get(1).partner = spielers.get(3);
			spielers.get(3).partner = spielers.get(1);
			partnerschaften.add( new Partnerschaft(spielers.get(1), spielers.get(3)) );
		} else {
			throw new RuntimeException(
			                           "Hups, so viele sieler sind nicht unterstüzt");
		}
	}

	/**
	 * Sendet die übergebene Nachricht an alle Spieler
	 *
	 * @param nachricht
	 *            zu versendende Nachricht
	 */
	public void broadcast(Nachricht nachricht) {
		for (Spieler spieler : spielers) {
			try {
				spieler.sende(nachricht);
			} catch (VerbindungWegException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Sendet den übergebenen String als Chatnachricht an alle Spieler
	 *
	 * @param msg
	 *            zu sendende Textmitteilung
	 */
	public void broadcast(String msg) {
		broadcast(new ChatNachricht("Server", msg));
	}

	/**
	 * Anzahl der Spieler
	 *
	 * @return Anzahl der Spieler
	 */
	public int getAnzahlSpieler() {
		return spielers.size();
	}

	/**
	 * Anzahl der offenen Spielposten
	 *
	 * @return Anzahl der noch offenen Spielslots
	 */
	public int getAnzahlOffen() {
		return anzahlSpieler - getAnzahlSpieler();
	}

	/**
	 * Prüft ob Spiel komplett ist
	 *
	 * @return true falls Spiel komplett
	 */
	public boolean isKomplett() {
		return getAnzahlSpieler() == anzahlSpieler;
	}

	public Runde starteRunde() {
		if (runde == null) {
			runde = new Runde(0, spielers, spiel.getKartenGeber());
		} else {
			runde = new Runde(runde.nummer + 1, spielers, spiel.getKartenGeber());
		}

		return runde;
	}

	public void sicherStellenIstAktuellerSpieler(EndPunktInterface endpunkt) {
		if (runde.getAktuellerSpieler() != getSpieler(endpunkt)) {

			broadcast("HAH.. huere michi, de " + endpunkt
			          + " wott voll bschisse");
			new RuntimeException("beschiss von " + endpunkt + " an "
			                     + runde.getAktuellerSpieler());
		}
	}

	public Spieler getSpieler(EndPunktInterface endpunkt) {
		Spieler spieler = endpunktZuSpieler.get(endpunkt);

		if (spieler == null)
			throw new RuntimeException(
			                           "Unbekannter Spieler, kann Spieler nicht anhand des Endpunktes "
			                                   + endpunkt + " auflösen");

		return spieler;
	}

	public SpielInfo getSpielInfo() {
		Vector<SpielerInfo> spielers = new Vector<SpielerInfo>();

		for (Spieler spieler : this.spielers) {
			SpielerInfo si = spieler.getSpielerInfo();
			spielers.add(si);
		}

		return new SpielInfo(spielers);
	}


	/*
	 * Façade für pd.Spiel
	 */


	/**
	 * Erstellt einen neuen Spieler und registriert diesen beim Spiel
	 *
	 * @param spielerName Name des Spielers
	 * @param endpunkt Endpunkt des Spielers
	 * @return Spieler Objekt
	 */
	public Spieler fuegeHinzu(String spielerName, EndPunktInterface endpunkt) {
		/* PD Spieler erstellen */
		pd.spieler.Spieler pdSpieler = spiel.fuegeHinzu(spielerName);

		/* Mini PD Spieler erstellen */
		Spieler spieler = new Spieler(endpunkt, pdSpieler);


		endpunktZuSpieler.put(spieler.getEndPunkt(), spieler);

		spielers.add(spieler);

		return spieler;
	}

	/**
	 * Liefert den Kartengeber des Spiels. Siehe {@link pd.Spiel#getKartenGeber()}
	 * @return Den {@link KartenGeber}
	 */
	public KartenGeber getKartenGeber() {
	    return spiel.getKartenGeber();
    }

	public boolean istFertig() {
		for (Partnerschaft p : partnerschaften) {
			if (p.istFertig()) {
				return true;
			}
		}

	    return false;
    }
}