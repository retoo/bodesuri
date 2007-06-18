package spielplatz;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import pd.karten.Karte;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import ui.GUIController;
import applikation.client.controller.Controller;
import applikation.client.events.VerbindeEvent;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Karten;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import dienste.eventqueue.Event;
import dienste.eventqueue.EventQueue;

public class SurriController extends Controller implements Observer {
	private String nick;
	private Spiel spiel;
	private GUIController guicontroller;

	public SurriController(EventQueue queue, String nick) {
		this.guicontroller = new GUIController(queue);
		this.eventQueue = queue;
		this.nick = nick;
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		throw new RuntimeException(fehlermeldung);
	}

	public void zeigeLobby(List<Spieler> spieler) {
		guicontroller.zeigeLobby(spieler);
	}

	public void zeigeSpiel(Spiel spiel) {
		guicontroller.zeigeSpiel(spiel);
		this.spiel = spiel;
		spiel.addObserver(this);
	}

	public void zeigeVerbinden(String spielerName) {
		guicontroller.zeigeVerbinden(spielerName);
		Event e = new VerbindeEvent("localhost", 7788, nick);

		eventQueue.enqueue(e);
	}

	public void karteTauschenAuswaehlen() {
		Karten karten = spiel.spielerIch.getKarten();

		int el = (int) Math.floor(Math.random() * karten.size());

		this.karteAuswaehlen(karten.get(el));
	}

	public void zugAufforderung() {
		System.out.println("Berechne möglichkeiten");

		List<ZugEingabe> moeglich = spiel.spielerIch.getMoeglicheZuege();

		IdentityHashMap<Karte, applikation.client.pd.Karte> karten = new IdentityHashMap<Karte, applikation.client.pd.Karte>();

		for (applikation.client.pd.Karte k : spiel.spielerIch.getKarten()) {
			karten.put(k.getKarte(), k);

		}

		if (moeglich.isEmpty()) {
			System.out.println("nix möglich");
			this.aufgeben();
		} else {
			System.out.println("möglich sind: " + moeglich.size());

			int rand = (int) Math.floor(Math.random() * moeglich.size());
			ZugEingabe ze = moeglich.get(rand);
			Bewegung bewegung = ze.getBewegung();
			applikation.client.pd.Karte karte = karten.get(ze.getKarte());

			ZugErfasstEvent zee = new ZugErfasstEvent(spiel.spielerIch, karte, bewegung);
			eventQueue.enqueue(zee);
		}

	}


	public void update(Observable o, Object arg) {

    }


}
