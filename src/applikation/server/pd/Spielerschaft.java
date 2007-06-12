package applikation.server.pd;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Vector;

import pd.spieler.SpielerFarbe;

import applikation.nachrichten.ChatNachricht;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungWegException;

/**
 * Repräsentiert eine Gruppe von Spieler die in einem Spiel auf dem Server
 * mitspielen.
 */
public class Spielerschaft implements Iterable<Spieler> {
	Vector<Spieler> spielers = new Vector<Spieler>();
	private IdentityHashMap<EndPunkt, Spieler> endpunktZuSpieler;

	public Runde runde;

	private int anzahlSpieler;

	/**
	 * Erstellt eine neue Spielerschaft.
	 * @param anzSpieler Anzahl der Speielr
	 */
	public Spielerschaft(int anzSpieler) {
		this.anzahlSpieler = anzSpieler;
		this.spielers = new Vector<Spieler>();
		this.endpunktZuSpieler = new IdentityHashMap<EndPunkt, Spieler>();
	}

	public void partnerschaftenBilden() {
		if (anzahlSpieler != spielers.size())
			throw new RuntimeException("partnerschaftBilden zu früh aufgerufen");

		if (anzahlSpieler == 1) {
			/* Entwickler Betrieb */
			Spieler einzelgaenger = spielers.get(0);

			einzelgaenger.partner = einzelgaenger;
		} else if (anzahlSpieler == 2) {
			spielers.get(0).partner = spielers.get(1);
			spielers.get(1).partner = spielers.get(0);
		} else if (anzahlSpieler == 4) {
			/* Normaler Betrieb */
			spielers.get(0).partner = spielers.get(2);
			spielers.get(2).partner = spielers.get(0);

			spielers.get(1).partner = spielers.get(3);
			spielers.get(3).partner = spielers.get(1);
		} else {
			throw new RuntimeException("Hups, so viele sieler sind nicht unterstüzt");
		}
	}

	/**
	 * Fügt einen Spieler zur Spielerschaft hinzu
	 *
	 * @param spieler
	 *            neuer Spieler
	 */
	public void add(Spieler spieler) {
		endpunktZuSpieler.put(spieler.getEndPunkt(), spieler);
		spielers.add(spieler);
	}

	public SpielerFarbe naechsteFarbe() {

	    return null;
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
		broadcast(new ChatNachricht(msg));
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

	/**
	 * Iterator welcher ermöglicht über alle Spieler zu iterieren.
	 *
	 * @see java.lang.Iterable#iterator()
	 * @return spielers-iterator
	 */
	public Iterator<Spieler> iterator() {
		return spielers.iterator();
	}


	public Runde starteRunde() {
		if (runde == null) {
			runde = new Runde(0, spielers);
		} else {
			runde = new Runde(runde.nummer + 1, spielers);
		}

		return runde;
	}

	public void sicherStellenIstAktuellerSpieler(EndPunkt endpunkt) {
		if (runde.getAktuellerSpieler() != getSpieler(endpunkt)) {

			broadcast("HAH.. huere michi, de " + endpunkt
			          + " wott voll bschisse");
			new RuntimeException("beschiss von " + endpunkt + " an "
			                     + runde.getAktuellerSpieler());
		}
	}

	public Spieler getSpieler(EndPunkt endpunkt) {
		Spieler spieler = endpunktZuSpieler.get(endpunkt);

		if (spieler == null)
			throw new RuntimeException("Unbekannter Spieler, kann Spieler nicht anhand des Endpunktes " + endpunkt + " auflösen");

		return spieler;
	}

	public SpielInfo getSpielInfo() {
		Vector<SpielerInfo> spielers = new Vector<SpielerInfo>();

		for (Spieler spieler : this) {
			SpielerInfo si = spieler.getSpielerInfo();
			spielers.add(si);
		}

	    return new SpielInfo(spielers);
    }
}
