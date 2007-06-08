package applikation.server;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Vector;

import applikation.nachrichten.ChatNachricht;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungWegException;

/**
 * Repräsentiert eine Gruppe von Spieler die in einem Spiel auf dem Server
 * mitspielen.
 */
public class Spielerschaft implements Iterable<Spieler> {
	/**
	 * Maximale Anzahl Spieler
	 *
	 * TODO: muss das wirklich so ne Konstante sein.
	 */
	public static final int MAXSPIELER = 1;

	private Vector<Spieler> spielers = new Vector<Spieler>();
	private int aktuellerSpieler;
	private IdentityHashMap<EndPunkt, Spieler> endpunktZuSpieler;

	public Runde runde;

	private int anzahlSpieler;


	/**
	 * Erstellt eine neue Spielerschaft.
	 */
	public Spielerschaft() {
		this.anzahlSpieler = MAXSPIELER;
		this.spielers = new Vector<Spieler>();
		this.endpunktZuSpieler = new IdentityHashMap<EndPunkt, Spieler>();

		aktuellerSpieler = 0;
	}


	public void partnerschaftenBilden() {
		if (anzahlSpieler != spielers.size())
			throw new RuntimeException("partnerschaftBilden zu früh aufgerufen");

		if (anzahlSpieler == 1) {
			/* Entwickler Betrieb */
			Spieler einzelgaenger = spielers.get(0);

			einzelgaenger.partner = einzelgaenger;
		} else if (anzahlSpieler == 4) {
			/* Normaler Betrieb */
			spielers.get(0).partner = spielers.get(2);
			spielers.get(2).partner = spielers.get(0);

			spielers.get(1).partner = spielers.get(3);
			spielers.get(1).partner = spielers.get(1);
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

	/**
	 * Generiert einen Array der die Namen aller Spieler beinhaltet
	 *
	 * @return Array der die Namen aller Spieler beinhaltet
	 */
	public String[] getStringArray() {
		String[] spielers_str = new String[spielers.size()];

		for (int i = 0; i < spielers.size(); i++)
			spielers_str[i] = spielers.get(i).name;

		return spielers_str;
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
	 * Markiert den nächsten Spieler als 'aktuellerSpieler'. Methode darf erst
	 * aufgerufen ewrden nachdem mindestens ein Spieler hinzugefügt wurde.
	 */
	public void rotiereSpieler() {
		int anzahlSpieler = spielers.size();

		if (anzahlSpieler > 0)
			aktuellerSpieler = (aktuellerSpieler + 1) % anzahlSpieler;
		else
			throw new RuntimeException(
			                           "Kann nicht rotieren, es gibt ja noch gar keine Spieler");
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

	/**
	 * Liefert aktuellen Spieler
	 *
	 * @return der zurzeit spielende Spieler
	 */
	public Spieler getAktuellerSpieler() {
		return spielers.get(aktuellerSpieler);
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
		if (getAktuellerSpieler() != getSpieler(endpunkt)) {

			broadcast("HAH.. huere michi, de " + endpunkt
			          + " wott voll bschisse");
			new RuntimeException("beschiss von " + endpunkt + " an "
			                     + getAktuellerSpieler());
		}
	}


	public Spieler getSpieler(EndPunkt endpunkt) {
		Spieler spieler = endpunktZuSpieler.get(endpunkt);

		if (spieler == null)
			throw new RuntimeException("Unbekannter Spieler, kann Spieler nicht anhand des Endpunktes " + endpunkt + " auflösen");

		return spieler;
	}
}
