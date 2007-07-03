package applikation.server.pd;

import java.util.List;
import java.util.Vector;

import pd.regelsystem.ZugEingabe;
import pd.regelsystem.karten.Karte;
import applikation.nachrichten.KartenTausch;

/**
 * Entspricht der Teilnahme eines Spielers an einer gewissen Runde im Spiel.
 */
public class RundenTeilnahme {
	private Vector<ZugEingabe> zuegeDieseRunde;
	private Karte karteVomPartner;
	private Spieler spieler;
	private List<Karte> karten;
	private boolean hatGetauscht;

	/**
	 * Erstellt eine neue Rundenteilnahme
	 *
	 * @param spieler Teilnehmender Spieler
	 * @param karten Die Karten des Spielers
	 */
	public RundenTeilnahme(Spieler spieler, List<Karte> karten) {
		this.spieler = spieler;
		this.karten = karten;
		this.hatGetauscht = false;
		this.zuegeDieseRunde = new Vector<ZugEingabe>();
	}

	/**
	 * Schliesst den Tausch der Karte entgültig ab indem die bekommene Karte
	 * der Kartenliste hinzugefügt wird und dem Spieler mitegeteilt wird.
	 */
	public void tauschAbschluss() {
		karten.add(karteVomPartner);

		/* dem Clienten mitteilen */
		spieler.sende(new KartenTausch(karteVomPartner));
	}

	/**
	 * Erfasst einen neuen Zug. Die im Zug verwendete Karte wird dem Spieler
	 * weg genommen.
	 *
	 * @param zug
	 */
	public void neuerZug(ZugEingabe zug) {
		boolean res = nimmWeg(zug.getKarte());

		/* Cheat Schutz */
		if (res == false) {
			throw new RuntimeException("Spieler " + spieler + " spielte den Zug " + zug + " mit einer Karte die er nicht hat!");
		}

		zuegeDieseRunde.add(zug);
	}


	/**
	 * @return true falls der Spieler keine Karten mehr ha
	 */
	public boolean hatKeineKartenMehr() {
		return karten.isEmpty();
	}

	public boolean hatGetauscht() {
		return hatGetauscht;
	}

	public void setHatGetauscht() {
		hatGetauscht = true;
	}

	/**
	 * Nimmt die übergebene Karte dem Spieler weg.
	 *
	 * @param karte die zu entfernende Karte
	 * @return true falls die Karte vorhanden war
	 */
	public boolean nimmWeg(Karte karte) {
		return karten.remove(karte);
	}

	/**
	 * @return eine Liste mit allen aktuellen Karten dieses Spielers in dieser
	 *         Runde
	 */
	public List<Karte> getKarten() {
		return karten;
	}

	/**
	 * @return den Spieler
	 */
	public Spieler getSpieler() {
		return spieler;
	}


	public void setKarteVomPartner(Karte karteVomPartner) {
		this.karteVomPartner = karteVomPartner;
	}
}
