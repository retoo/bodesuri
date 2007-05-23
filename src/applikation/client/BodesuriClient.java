package applikation.client;

import pd.spieler.Spieler;
import ui.BodesuriView;
import ui.lobby.LobbyView;
import ui.verbinden.VerbindenView;
import applikation.client.zustaende.Lobby;
import applikation.client.zustaende.LobbyStart;
import applikation.client.zustaende.ProgrammStart;
import applikation.client.zustaende.SchwererFehler;
import applikation.client.zustaende.Spiel;
import applikation.client.zustaende.SpielStart;
import applikation.client.zustaende.VerbindungErfassen;
import applikation.client.zustaende.VerbindungWirdAufgebaut;
import dienste.automat.Automat;
import dienste.automat.EventQueue;
import dienste.netzwerk.EndPunkt;

public class BodesuriClient extends Automat {
	public EventQueue queue;
	public EndPunkt endpunkt;

	public VerbindenView verbindenView;
	public LobbyView lobbyView;
	public BodesuriView spielView;

	public pd.Spiel spiel;
	public String spielerName;
	public Spieler spielerIch;

	public BodesuriClient(EventQueue queue) {
		register(new SchwererFehler());
		register(new ProgrammStart());
		register(new VerbindungErfassen());
		register(new VerbindungWirdAufgebaut());
		register(new LobbyStart());
		register(new Lobby());
		register(new SpielStart());
		register(new Spiel());

		setStart(ProgrammStart.class);

		setEventSource(queue);

		this.queue = queue;
	}
}
