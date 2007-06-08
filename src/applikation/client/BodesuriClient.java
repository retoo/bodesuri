package applikation.client;

import applikation.client.controller.Controller;
import applikation.client.zugautomat.ZugAutomat;
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
import dienste.netzwerk.EndPunkt;

/**
 * Version des Automaten für den Client
 */
public class BodesuriClient extends Automat {
	public EventQueue queue;
	public EndPunkt endpunkt;

	public ZugAutomat zugAutomat;

	/**
	 * Im Konstruktor werden alle benötigten Zustände erstellt & registriert.
	 *
	 * @param controller
	 */
	public BodesuriClient(Controller controller) {
		registriere(new SchwererFehler());
		registriere(new ProgrammStart(controller));
		registriere(new VerbindungErfassen(controller));
		registriere(new VerbindungSteht());
		registriere(new LobbyStart(controller));
		registriere(new Lobby(controller));
		registriere(new SpielStart(controller));
		registriere(new AmZug());
		registriere(new NichtAmZug(controller));
		registriere(new StarteRunde());
		registriere(new KarteTauschenAuswaehlen(controller));
		registriere(new KarteTauschenBekommen());

		setStart(ProgrammStart.class);

		this.queue = new EventQueue();
		controller.setEventQueue(queue);
		setEventQuelle(new EventQuelleAdapter(queue));
	}

	public void run() {
		super.run();
	}


	public String toString() {
		return "Client-Automat";
	}
}
