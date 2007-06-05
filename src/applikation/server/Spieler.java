package applikation.server;

import applikation.nachrichten.BeitrittsBestaetigung;
import applikation.nachrichten.KartenTausch;
import applikation.nachrichten.ZugAufforderung;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;

/**
 * Repräsentiert den Spieler auf dem Server.
 */
public class Spieler {
	/**
	 * Name des Spielers
	 */
	public String name;
	public Spieler partner;
	private EndPunkt endpunkt;
	public boolean hatGetauscht;

	/**
	 * Erstellt einen neune Spieler
	 *
	 * @param endpunkt
	 *            Endpunkt des Spielers
	 * @param name
	 *            Name des Spielers
	 */
	public Spieler(EndPunkt endpunkt, String name) {
		this.name = name;
		this.endpunkt = endpunkt;
		this.hatGetauscht = false;
	}

	public void sendeBeitrittsBestaetigung(Spielerschaft spielers) {
		sende(new BeitrittsBestaetigung(spielers.getStringArray()));
	}

	public void sendeZugAuffoderung() {
		sende(new ZugAufforderung());
	}


	public void sendeKartenTausch(KartenTausch tausch) {
	    sende(tausch);
    }

	public void sende(Nachricht nachricht) {
		endpunkt.sende(nachricht);
	}

	public String toString() {
		return name + " (" + endpunkt + ")";
	}

	public EndPunkt getEndPunkt() {
		return endpunkt;
	}
}
