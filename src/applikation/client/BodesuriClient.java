package applikation.client;

import javax.swing.JOptionPane;

import pd.spieler.Spieler;
import ui.lobby.LobbyView;
import ui.spiel.BodesuriView;
import ui.verbinden.VerbindenView;
import applikation.client.zugAutomat.ZugAutomat;
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
import dienste.automat.zustaende.Zustand;
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

	public String defaultName;
	
	public ZugAutomat zugAutomat;

	/**
	 * Im Konstruktor werden alle benötigten Zustände erstellt & registriert.
	 * @param defaultName
	 *
	 */
	public BodesuriClient(String defaultName) {
		registriere(new SchwererFehler());
		registriere(new ProgrammStart());
		registriere(new VerbindungErfassen());
		registriere(new VerbindungSteht());
		registriere(new LobbyStart());
		registriere(new Lobby());
		registriere(new SpielStart());
		registriere(new AmZug());
		registriere(new NichtAmZug());
		registriere(new StarteRunde());
		registriere(new KarteTauschenAuswaehlen());
		registriere(new KarteTauschenBekommen());

		setStart(ProgrammStart.class);

		this.queue = new EventQueue();
		this.defaultName = defaultName;

		setEventQuelle(queue);
	}

	public BodesuriClient() {
		this("Spieler");
	}

	/* kommt mal in den controller oder geh ganz anders */
	public void meldeFehler(String fehlermeldung) {
	    // TODO Auto-generated method stub

		JOptionPane.showMessageDialog(verbindenView, fehlermeldung);

    }

	public static void main(String[] args) {
		Automat automat = new BodesuriClient();
		// ClientController controller = new ClientController();

		try {
			automat.run();
		} catch (Exception e) {
			/* Applikation stoppen wenn ein Fehler auftritt */
			e.printStackTrace();
			System.out.println("Client: Exception in run()");
			System.exit(99);
		}
	}

	public boolean isZustand(Class<? extends Zustand> klass) {
	    return getAktuellerZustand() ==getZustand(klass);
    }
}
