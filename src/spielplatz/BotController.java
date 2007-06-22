package spielplatz;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Vector;

import pd.karten.Karte;
import pd.zugsystem.Bewegung;
import pd.zugsystem.ZugEingabe;
import ui.GUIController;
import applikation.client.controller.Controller;
import applikation.client.events.VerbindeEvent;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Chat;
import applikation.client.pd.Karten;
import applikation.client.pd.Spiel;
import applikation.client.pd.Spieler;
import applikation.geteiltes.SpielerInfo;
import dienste.eventqueue.Event;
import dienste.eventqueue.EventQueue;

public class BotController extends Controller {
	private Spiel spiel;
	private GUIController guicontroller;
	private String nickname;
	private String hostname;
	private int port;
	private boolean gui;

	public BotController(EventQueue queue, String nickname, String hostname, int port, Bot bot, boolean gui) {
		if (gui) {
			this.guicontroller = new GUIController(queue);
		}

		this.gui = gui;
		this.eventQueue = queue;
		this.nickname = nickname;
		this.hostname = hostname;
		this.port = port;
	}

	public void zeigeFehlermeldung(String fehlermeldung) {
		throw new RuntimeException(fehlermeldung);
	}

	public void zeigeLobby(List<Spieler> spieler, Chat chat) {
		if (gui)
			guicontroller.zeigeLobby(spieler, chat);
	}

	public void zeigeSpiel(Spiel spiel) {
		if (gui)
			guicontroller.zeigeSpiel(spiel);
		this.spiel = spiel;
	}

	public void zeigeVerbinden(String spielerName) {
		if (gui)
			guicontroller.zeigeVerbinden(spielerName);
		Event e = new VerbindeEvent(this.hostname, this.port, this.nickname);

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

    public void zeigeSpielerInLobby(Vector<SpielerInfo> spielers) {
    }
}
