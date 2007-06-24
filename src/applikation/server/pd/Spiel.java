package applikation.server.pd;

import java.util.IdentityHashMap;
import java.util.Vector;

import pd.SpielThreads;
import pd.karten.KartenGeber;
import pd.spieler.Partnerschaft;
import applikation.geteiltes.SpielInfo;
import applikation.geteiltes.SpielerInfo;
import applikation.nachrichten.ChatNachricht;
import dienste.eventqueue.EventQueue;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;
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

	/**
	 * Bildet die Partnerschaften, die im Spiel benötigt werden. Die zuordnung der Spieler zu
	 * den Partnerschaften erfolgt nach einem vorgegebenen Schema. Das Bilden der Partnerschaften
	 * funktioniert für 1, 2 oder 4 Spieler.
	 */
	public void partnerschaftenBilden() {
		if (anzahlSpieler != spielers.size())
			throw new RuntimeException("partnerschaftBilden zu früh aufgerufen");

		if (anzahlSpieler == 1) {
			/* Entwickler Betrieb */
			Spieler einzelgaenger = spielers.get(0);

			einzelgaenger.setPartner(einzelgaenger);
			partnerschaften.add( new Partnerschaft(einzelgaenger.spieler, einzelgaenger.spieler) );
		} else if (anzahlSpieler == 2) {
			spielers.get(0).setPartner(spielers.get(1));
			spielers.get(1).setPartner(spielers.get(0));

			partnerschaften.add( new Partnerschaft(spielers.get(0).spieler, spielers.get(1).spieler) );
		} else if (anzahlSpieler == 4) {
			/* Normaler Betrieb */
			spielers.get(0).setPartner(spielers.get(2));
			spielers.get(2).setPartner(spielers.get(0));
			partnerschaften.add( new Partnerschaft(spielers.get(0).spieler, spielers.get(2).spieler) );

			spielers.get(1).setPartner(spielers.get(3));
			spielers.get(3).setPartner(spielers.get(1));
			partnerschaften.add( new Partnerschaft(spielers.get(1).spieler, spielers.get(3).spieler) );
		} else {
			throw new RuntimeException("Hups, so viele sieler sind nicht unterstüzt");
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
			spieler.sende(nachricht);
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
	 * @return Anzahl der noch offenen Spielslots
	 */
	public int getAnzahlOffen() {
		return anzahlSpieler - getAnzahlSpieler();
	}

	/**
	 * @return true falls Spiel komplett
	 */
	public boolean isKomplett() {
		return getAnzahlSpieler() == anzahlSpieler;
	}

	/**
	 * @return Neu zu spielende Runde
	 */
	public Runde starteRunde() {
		if (runde == null) {
			runde = new Runde(0, spielers, spiel.getKartenGeber());
		} else {
			runde = new Runde(runde.nummer + 1, spielers, spiel.getKartenGeber());
		}

		return runde;
	}

	/**
	 * Anti-Cheat Funktion die sicherstellt, dass der Endpunkt auch immer der
	 * aktuelle Spieler der Runde ist.
	 *
	 * @param endpunkt
	 */
	public void sicherStellenIstAktuellerSpieler(EndPunktInterface endpunkt) {
		if (runde.getAktuellerSpieler() != getSpieler(endpunkt)) {

			broadcast("HAH.. huere michi, de " + endpunkt
			          + " wott voll bschisse");
			throw new RuntimeException("Beschiss von " + endpunkt + " an "
			                     + runde.getAktuellerSpieler());
		}
	}

	public Spieler getSpieler(EndPunktInterface endpunkt) {
		Spieler spieler = endpunktZuSpieler.get(endpunkt);

		if (spieler == null)
			throw new RuntimeException("Unbekannter Spieler, kann Spieler nicht anhand des Endpunktes "
			                                   + endpunkt + " auflösen");

		return spieler;
	}

	public Vector<Partnerschaft> getPartnerschaften() {
		return partnerschaften;
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
	 * @param absender Zu entfernenden Spieler
	 */
	public void entferne(EndPunktInterface absender) {
		Spieler s = getSpieler(absender);

		if (s == null)
			throw new RuntimeException("Unbekannter absender " + absender);

		spielers.remove(s);
    }

	/**
	 * Liefert den Kartengeber des Spiels. Siehe {@link pd.Spiel#getKartenGeber()}
	 * @return Den {@link KartenGeber}
	 */
	public KartenGeber getKartenGeber() {
	    return spiel.getKartenGeber();
    }

	/**
	 * Zeigt an, ob das Spiel fertig ist oder nicht. Dies wird anhand der Partnerschaften
	 * ermittelt (sobald eine Partnerschaft fertig ist, ist auch das Spiel fertig).
	 *
	 * @return Ob Spiel fertig ist oder nicht
	 */
	public boolean istFertig() {
		return getGewinner() != null;
    }

	/**
	 * Gibt die siegreiche Partnerschaft zurück.
	 *
	 * @return Siegreiche Partnerschaft. Falls noch nicht bekannt wird null zurückgegeben.
	 */
	public Partnerschaft getGewinner() {
		for (Partnerschaft p : partnerschaften) {
			if (p.istFertig()) {
				return p;
			}
		}

	    return null;
    }
}