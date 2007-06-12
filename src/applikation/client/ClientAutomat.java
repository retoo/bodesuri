package applikation.client;

import applikation.client.controller.Controller;
import applikation.client.zustaende.ClientZustand;
import applikation.client.zustaende.AmZug;
import applikation.client.zustaende.KarteTauschenAuswaehlen;
import applikation.client.zustaende.KarteTauschenBekommen;
import applikation.client.zustaende.Lobby;
import applikation.client.zustaende.LobbyStart;
import applikation.client.zustaende.NichtAmZug;
import applikation.client.zustaende.ProgrammStart;
import applikation.client.zustaende.SchwererFehler;
import applikation.client.zustaende.SpielStart;
import applikation.client.zustaende.StarteRunde;
import applikation.client.zustaende.VerbindungErfassen;
import applikation.client.zustaende.VerbindungSteht;
import dienste.automat.Automat;
import dienste.automat.EventQuelleAdapter;
import dienste.eventqueue.EventQueue;

/**
 * Version des Automaten für den Client
 */
public class ClientAutomat extends Automat {
	private Controller controller;
	private SpielDaten spielDaten;

	/**
	 * Im Konstruktor werden alle benötigten Zustände erstellt & registriert.
	 *
	 * @param controller
	 * @param spielerName Standard-Wert für den Spielernamen
	 */
	public ClientAutomat(Controller controller, String spielerName) {
		this.controller = controller;
		this.spielDaten = new SpielDaten();
		// TODO: Sollte das Spiel nicht primär in den SpielDaten gespeichert
		// sein, und diese werden dem Controller übergeben? (Im Server gibt es
		// auch spielDaten.spiel.) --Robin
		this.spielDaten.spiel = spielDaten.spiel;

		EventQueue queue = new EventQueue();
		controller.setEventQueue(queue);
		spielDaten.queue = queue;
		spielDaten.spielerName = spielerName;

		registriere(new SchwererFehler());
		registriere(new ProgrammStart());
		registriere(new VerbindungErfassen());
		registriere(new VerbindungSteht());
		registriere(new LobbyStart());
		registriere(new Lobby(controller));
		registriere(new SpielStart());
		registriere(new AmZug());
		registriere(new NichtAmZug());
		registriere(new StarteRunde());
		registriere(new KarteTauschenAuswaehlen());
		registriere(new KarteTauschenBekommen());

		setStart(ProgrammStart.class);

		setEventQuelle(new EventQuelleAdapter(queue));
	}

	public void registriere(ClientZustand zustand) {
		zustand.setController(controller);
		zustand.setSpielDaten(spielDaten);

		super.registriere(zustand);
	}

	public void run() {
		super.run();
	}


	public String toString() {
		return "Client-Automat";
	}
}
