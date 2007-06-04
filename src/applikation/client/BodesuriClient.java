package applikation.client;

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
import dienste.automat.EventQueue;
import dienste.netzwerk.EndPunkt;

/**
 * Version des Automaten für den Client
 */
public class BodesuriClient extends Automat {
	public EventQueue queue;
	public EndPunkt endpunkt;
	private Controller controller;

	/**
	 * Im Konstruktor werden alle benötigten Zustände erstellt & registriert.
	 * 
	 * @param queue
	 * @param controller
	 */
	public BodesuriClient(EventQueue queue, Controller controller) {
		registriere(new SchwererFehler());
		registriere(new ProgrammStart(controller));
		registriere(new VerbindungErfassen(controller));
		registriere(new VerbindungSteht());
		registriere(new LobbyStart(controller));
		registriere(new Lobby(controller));
		registriere(new SpielStart(controller));
		registriere(new AmZug(controller));
		registriere(new NichtAmZug());
		registriere(new StarteRunde());
		registriere(new KarteTauschenAuswaehlen());
		registriere(new KarteTauschenBekommen());

		setStart(ProgrammStart.class);

		this.queue = queue;
		this.controller = controller;

		setEventQuelle(queue);
	}

	/* kommt mal in den controller oder geh ganz anders */
	public void meldeFehler(String fehlermeldung) {
	    controller.zeigeFehlermeldung(fehlermeldung);
    }
}
