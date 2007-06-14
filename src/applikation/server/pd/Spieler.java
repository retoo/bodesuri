package applikation.server.pd;

import applikation.geteiltes.SpielInfo;
import applikation.geteiltes.SpielerInfo;
import applikation.nachrichten.BeitrittsInformation;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;

/**
 * Repräsentiert den Spieler auf dem Server.
 */
public class Spieler {
	/**
	 * Name des Spielers
	 */
	public Spieler partner;
	private EndPunktInterface endpunkt;
	public pd.spieler.Spieler spieler;

	/**
	 * Erstellt einen neune Spieler
	 *
	 * @param absender
	 *            Endpunkt des Spielers
	 * @param spieler
	 *            Spieler-Objekt aus der PD
	 */
	public Spieler(EndPunktInterface absender, pd.spieler.Spieler spieler) {
		this.endpunkt = absender;
		this.spieler = spieler;

	}

	public void sendeBeitrittsBestaetigung(SpielInfo spielInfo) {
		sende(new BeitrittsInformation(spielInfo));
	}




	/*public void sendeTauschKarte() {
		sende(tausch);
		tausch = null;
	}*/

	public void sende(Nachricht nachricht) {
		endpunkt.sende(nachricht);
	}



	public String toString() {
		return spieler.getName() + " (" + endpunkt + ")";
	}

	public EndPunktInterface getEndPunkt() {
		return endpunkt;
	}

	public SpielerInfo getSpielerInfo() {
		return new SpielerInfo(spieler.getName());
	}
}
