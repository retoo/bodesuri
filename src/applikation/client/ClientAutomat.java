package applikation.client;

import applikation.client.controller.Controller;
import applikation.client.konfiguration.Konfiguration;
import applikation.client.pd.Spiel;
import applikation.client.zustaende.AmZug;
import applikation.client.zustaende.ClientZustand;
import applikation.client.zustaende.KarteTauschenAuswaehlen;
import applikation.client.zustaende.KarteTauschenBekommen;
import applikation.client.zustaende.Lobby;
import applikation.client.zustaende.NichtAmZug;
import applikation.client.zustaende.ProgrammStart;
import applikation.client.zustaende.SchwererFehler;
import applikation.client.zustaende.SpielEnde;
import applikation.client.zustaende.SpielStart;
import applikation.client.zustaende.StarteRunde;
import applikation.client.zustaende.VerbindungErfassen;
import dienste.automat.Automat;
import dienste.automat.EventQuelleAdapter;
import dienste.eventqueue.EventQueue;

/**
 * Der Automat für den Client.
 */
public class ClientAutomat extends Automat {
	private Controller controller;
	private Spiel spiel;

	/**
	 * Alle für den Automat benötigten Zustände erstellen & registrieren.
	 *
	 * @param controller
	 * @param queue EventQueue
	 * @param konfig Basiskonfiguration
	 */
	public ClientAutomat(Controller controller, EventQueue queue, Konfiguration konfig) {
		this.controller = controller;
		this.spiel = new Spiel(konfig);

		EventQueue eventQueue = queue;
		if (eventQueue == null) {
			eventQueue = new EventQueue();
		}

		spiel.queue = eventQueue;

		registriere(new SchwererFehler());
		registriere(new ProgrammStart());
		registriere(new VerbindungErfassen());
		registriere(new Lobby(controller));
		registriere(new SpielStart());
		registriere(new StarteRunde());
		registriere(new KarteTauschenAuswaehlen());
		registriere(new KarteTauschenBekommen());
		registriere(new NichtAmZug());
		registriere(new AmZug());
		registriere(new SpielEnde());

		setStart(ProgrammStart.class);

		setEventQuelle(new EventQuelleAdapter(eventQueue));
	}

	private void registriere(ClientZustand zustand) {
		zustand.setController(controller);
		zustand.setSpiel(spiel);

		super.registriere(zustand);
	}

	public void run() {
		super.run();
	}


	public String toString() {
		return "Client-Automat";
	}
}
