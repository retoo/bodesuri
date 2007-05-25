package applikation.server;

import java.util.Iterator;
import java.util.Vector;

import applikation.server.nachrichten.ChatNachricht;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungWegException;

public class Spielerschaft implements Iterable<Spieler> {
	private final int maxSpieler;
	private Vector<Spieler> spielers = new Vector<Spieler>();
	private int aktuellerSpieler;

	public Spielerschaft(int maxSpieler) {
		this.maxSpieler = maxSpieler;
		spielers = new Vector<Spieler>();
		aktuellerSpieler = 0;
	}

	/**
	 * Fügt einen Spieler zur Spielerschaft hinzu
	 * 
	 * @param spieler
	 *            neuer Spieler
	 */
	public void add(Spieler spieler) {
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
			spielers_str[i] = spielers.get(i).spielerName;

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
				spieler.endpunkt.sende(nachricht);
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
	public int getAnZahlSpieler() {
		return spielers.size();
	}

	/**
	 * Prüft ob Spiel komplett ist
	 * 
	 * @return true falls Spiel komplett
	 */
	public boolean isKomplett() {
		return getAnZahlSpieler() == maxSpieler;
	}

	/**
	 * Markiert den nächsten Spieler als 'aktuellerSpieler'. Methode darf erst aufgerufen ewrden 
	 * nachdem mindestens ein Spieler hinzugefügt wurde.
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

	/**
	 * Iterator welcher ermöglciht über alle Spieler zu iterieren.
	 * 
	 * @see java.lang.Iterable#iterator()
	 * @return spieler-iterator
	 */
	public Iterator<Spieler> iterator() {
	    return spielers.iterator();
    }
}
