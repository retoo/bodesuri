package applikation.server.pd;

import applikation.nachrichten.BeitrittsInformation;
import applikation.nachrichten.SpielInfo;
import applikation.nachrichten.SpielerInfo;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;

/**
 * Repräsentiert den Spieler auf dem Server.
 */
public class Spieler {
	/**
	 * Name des Spielers
	 */
	private Spieler partner;
	private EndPunktInterface endpunkt;
	public pd.spiel.spieler.Spieler spieler;

	/**
	 * Erstellt einen neune Spieler
	 *
	 * @param absender
	 *            Endpunkt des Spielers
	 * @param spieler
	 *            Spieler-Objekt aus der PD
	 */
	public Spieler(EndPunktInterface absender, pd.spiel.spieler.Spieler spieler) {
		this.endpunkt = absender;
		this.spieler = spieler;

	}

	public void sendeBeitrittsBestaetigung(SpielInfo spielInfo) {
		sende(new BeitrittsInformation(spielInfo));
	}

	public void sende(Nachricht nachricht) {
		endpunkt.sende(nachricht);
	}


	public EndPunktInterface getEndPunkt() {
		return endpunkt;
	}

	public SpielerInfo getSpielerInfo() {
		return new SpielerInfo(spieler.getName());
	}

	public boolean istFertig() {
		return spieler.istFertig();
	}

	public String getName() {
		return spieler.getName();
	}

	public String toString() {
		return spieler.getName() + " (" + endpunkt + ")";
	}

	public Spieler getPartner() {
		return partner;
	}

	public void setPartner(Spieler partner) {
		this.partner = partner;
		if (partner != null) {
			spieler.setPartner(partner.spieler);
		}
	}
}
