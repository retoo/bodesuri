package applikation.server.pd;

import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.KartenTausch;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;

/**
 * Repräsentiert den Spieler auf dem Server.
 */
public class Spieler {
	/**
	 * Name des Spielers
	 */
	public Spieler partner;
	private EndPunkt endpunkt;
	public boolean hatGetauscht;
	private KartenTausch tausch;
	public pd.spieler.Spieler spieler;

	/**
	 * Erstellt einen neune Spieler
	 *
	 * @param endpunkt
	 *            Endpunkt des Spielers
	 * @param spieler
	 *            Spieler-Objekt aus der PD
	 */
	public Spieler(EndPunkt endpunkt, pd.spieler.Spieler spieler) {
		this.endpunkt = endpunkt;
		this.spieler = spieler;
		this.hatGetauscht = false;
	}

	public void sendeBeitrittsBestaetigung(SpielInfo spielInfo) {
		sende(new BeitrittsBestaetigung(spielInfo));
	}

	/**
	 * Zu tauschende Kart
	 *
	 * @param tausch
	 *            Tausch-Nachricht
	 */
	public void getauschteKarte(KartenTausch tausch) {
		this.tausch = tausch;
	}

	public void sendeTauschKarte() {
		sende(tausch);
		tausch = null;
	}

	public void sende(Nachricht nachricht) {
		endpunkt.sende(nachricht);
	}

	public String toString() {
		return spieler.getName() + " (" + endpunkt + ")";
	}

	public EndPunkt getEndPunkt() {
		return endpunkt;
	}

	public SpielerInfo getSpielerInfo() {
		return new SpielerInfo(spieler.getName());
    }
}
