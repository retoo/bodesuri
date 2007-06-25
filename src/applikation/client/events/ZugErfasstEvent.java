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
	Karte konkreteKarte;
	List<Bewegung> bewegungen;

	public ZugErfasstEvent(Spieler spieler, Karte karte, Karte konkreteKarte, Bewegung bewegung) {
		List<Bewegung> bewegungen = new Vector<Bewegung>();
		bewegungen.add(bewegung);
		initialisiere(spieler, karte, konkreteKarte, bewegungen);
	}
	
	public ZugErfasstEvent(Spieler spieler, Karte karte, Karte konkreteKarte, List<Bewegung> bewegung) {
		initialisiere(spieler, karte, konkreteKarte, bewegung);
	}
	
	private void initialisiere(Spieler spieler, Karte karte, Karte konkreteKarte, List<Bewegung> bewegung) {
		this.spieler = spieler;
		this.karte = karte;
		this.konkreteKarte = konkreteKarte; 
		this.bewegungen = bewegung;
	}

	public Karte getKarte() {
		return karte;
	}
	
	public Karte getKonkreteKarte() {
		return konkreteKarte;
	}

	public ZugEingabe toZugEingabe() {
		ZugEingabe ze = new ZugEingabe(spieler.getSpieler(), karte.getKarte(), bewegungen);
		if (konkreteKarte != null) {
			ze.setKonkreteKarte(konkreteKarte.getKarte());
		}
		return ze;
	}
}
