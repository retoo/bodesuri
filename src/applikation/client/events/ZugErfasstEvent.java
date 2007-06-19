package applikation.client.events;

import java.util.List;
import java.util.Vector;

import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import applikation.client.pd.Karte;
import applikation.client.pd.Spieler;
import dienste.eventqueue.Event;

public class ZugErfasstEvent extends Event {
	Spieler spieler;
	Karte karte;
	List<Bewegung> bewegungen;

	public ZugErfasstEvent(Spieler spieler, Karte karte, Bewegung bewegung) {
		this.spieler = spieler;
		this.karte = karte;
		this.bewegungen = new Vector<Bewegung>();
		this.bewegungen.add(bewegung);
	}
	
	public ZugErfasstEvent(Spieler spieler, Karte karte, List<Bewegung> bewegung) {
		this.spieler = spieler;
		this.karte = karte;
		this.bewegungen = bewegung;
	}

	public Karte getKarte() {
		return karte;
	}

	public ZugEingabe toZugEingabe() {
		return new ZugEingabe(spieler.getSpieler(), karte.getKarte(), bewegungen);
	}
}
