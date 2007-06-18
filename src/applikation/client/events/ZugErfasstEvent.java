package applikation.client.events;

import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import applikation.client.pd.Karte;
import applikation.client.pd.Spieler;
import dienste.eventqueue.Event;

public class ZugErfasstEvent extends Event {
	Spieler spieler;
	Karte karte;
	Bewegung bewegung;

	public ZugErfasstEvent(Spieler spieler, Karte karte, Bewegung bewegung) {
		this.spieler = spieler;
		this.karte = karte;
		this.bewegung = bewegung;
	}

	public Karte getKarte() {
		return karte;
	}

	public Bewegung getBewegung() {
    	return bewegung;
    }

	public Spieler getSpieler() {
    	return spieler;
    }
	
	public ZugEingabe toZugEingabe() {
		return new ZugEingabe(spieler.getSpieler(), karte.getKarte(), bewegung);
	}
}
