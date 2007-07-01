package applikation.server.pd;

import java.util.List;
import java.util.Vector;

import pd.karten.Karte;
import pd.regelsystem.ZugEingabe;
import applikation.nachrichten.KartenTausch;

public class RundenTeilnahme {
	private Vector<ZugEingabe> zuegeDieseRunde;
	private Karte karteVomPartner;
	private Spieler spieler;
	private List<Karte> karten;
	private boolean hatGetauscht;

	public RundenTeilnahme(Spieler spieler, List<Karte> karten) {
		this.spieler = spieler;
		this.karten = karten;
		this.hatGetauscht = false;
		this.zuegeDieseRunde = new Vector<ZugEingabe>();
	}

	public void tauscheKarte() {
		karten.add(karteVomPartner);

		/* dem Clienten mitteilen */
		spieler.sende(new KartenTausch(karteVomPartner));
	}

	public void neuerZug(ZugEingabe zug) {
		boolean res = nimmWeg(zug.getKarte());

		if (res == false) {
			throw new RuntimeException("Spieler " + spieler + " spielte den Zug " + zug + " mit einer Karte die er nicht hat!");
		}

		zuegeDieseRunde.add(zug);
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public boolean hatKeineKartenMehr() {
		return karten.isEmpty();
	}

	public boolean hatGetauscht() {
		return hatGetauscht;
	}

	public void setHatGetauscht() {
		hatGetauscht = true;
	}

	public boolean nimmWeg(Karte karte) {
		return karten.remove(karte);
	}

	public List<Karte> getKarten() {
		return karten;
	}

	public void setKarteVomPartner(Karte karteVomPartner) {
		this.karteVomPartner = karteVomPartner;
	}
}
