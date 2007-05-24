package applikation.client;

import pd.karten.Karte;
import pd.spieler.Spieler;
import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.verbinden.VerbindenView;
import applikation.client.zustaende.AmZug;
import applikation.client.zustaende.KarteWaehlen;
import applikation.client.zustaende.Lobby;
import applikation.client.zustaende.LobbyStart;
import applikation.client.zustaende.NichtAmZug;
import applikation.client.zustaende.ProgrammStart;
import applikation.client.zustaende.SchwererFehler;
import applikation.client.zustaende.SpielStart;
import applikation.client.zustaende.VerbindungErfassen;
import applikation.client.zustaende.VerbindungSteht;
import applikation.client.zustaende.Ziehen;
import applikation.zugentgegennahme.ZugEntgegennahme;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.netzwerk.EndPunkt;

/**
 * Version des Automaten für den Client
 */
public class BodesuriClient extends Automat {
	public EventQueue queue;
	public EndPunkt endpunkt;

	public VerbindenView verbindenView;
	public LobbyView lobbyView;
	public BodesuriView spielView;

	public pd.Spiel spiel;
	public String spielerName;
	public Spieler spielerIch;

	// TODO sollten eigentlich nicht hier sein.
	// Sind zu fest vom Status abhängig. Müssen aber momentan so sein, da es
	// nicht möglich ist Daten von Event zu Event zu übergeben.
	public Karte karte;
	public ZugEntgegennahme zugentgegennahme;

	/**
	 * Im Konstruktor werden alle benötigten Zustände erstellt & registriert.
	 * 
	 * @param queue
	 *            Die Warteschlange für die Events
	 */
	public BodesuriClient(EventQueue queue) {
		registriere(new SchwererFehler());
		registriere(new ProgrammStart());
		registriere(new VerbindungErfassen());
		registriere(new VerbindungSteht());
		registriere(new LobbyStart());
		registriere(new Lobby());
		registriere(new SpielStart());
		registriere(new AmZug());
		registriere(new NichtAmZug());
		registriere(new KarteWaehlen());
		registriere(new Ziehen());

		setStart(ProgrammStart.class);

		setEventQuelle(queue);

		this.queue = queue;
	}
}
